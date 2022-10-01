/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.AnexoServico;
import Servico.ContratoServico;
import Servico.NotaFiscalServico;
import Servico.UsuarioServico;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Anexo;
import modelo.Contratado;
import modelo.Contrato;
import modelo.ModeloDocumento;
import modelo.NotaFiscal;
import modelo.TipoAnexo;
import modelo.Usuario;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import util.Base64j;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerNotaFiscal extends managerPrincipal {

    @EJB
    private NotaFiscalServico notaFiscalServico;
    @EJB
    private AnexoServico anexoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;
    private NotaFiscal notaFiscal;
    private List<Contrato> contratos;
    private List<Usuario> responsaveis;
    private List<NotaFiscal> notasFiscais;
    private Anexo anexo;
    private Usuario userLogado;

    @Override
    public void carregar(String param) {
        userLogado = userServico.getCurrentUser();
        instanciarAnexo();
        this.notaFiscal = notaFiscalServico.find(Long.parseLong(param));
    }

    public void salvar() {
        if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
            this.notaFiscal.setUnidadeOrganizacional(userLogado.getUnidadeOrganizacional());
        }
        Contrato contrato = this.notaFiscal.getContrato();
        contrato.setValorRestante(contrato.getValorRestante().subtract(this.notaFiscal.getValor()));
        contratoServico.Update(contrato);
        notaFiscalServico.Save(this.notaFiscal);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "notaFiscal.xhtml?visualizar=" + this.notaFiscal.getId());
    }

    public void deletar() {
        NotaFiscal nt = notaFiscalServico.find(this.notaFiscal.getId());
        nt.setAtivo(false);
        notaFiscalServico.Update(nt);
        Msg.messagemInfo("Operação realizada com sucesso !");
    }

    public void atualizar() {
        notaFiscalServico.Update(this.notaFiscal);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "notaFiscal.xhtml?visualizar=" + this.notaFiscal.getId());
    }

    public void selecionarAnexo(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.NOTAFISCAL);
//        PrimeFaces.current().executeScript("PF('dlgAnexo').show();");
        adicionarAnexo();
    }

    public void pesquisar() {
        this.notasFiscais = notaFiscalServico.pesquisar(notaFiscal, userLogado);
        if (this.notasFiscais.size() > 0) {
            Msg.messagemInfo("Pesquisa realizada com sucesso !");
        } else {
            Msg.messagemError("Nenhum registro encontrado !");
        }
    }

    public void adicionarAnexo() {

        try {
            this.notaFiscal.getAnexos().add(anexoServico.adicionarAnexo(this.anexo.getArquivo()));
            int posicao = notaFiscal.getAnexos().size();
//            this.contrato.getAnexos().get(posicao - 1).setTipo(anexo.getTipo());
            this.notaFiscal.getAnexos().get(posicao - 1).setArquivo(anexo.getArquivo());
            this.notaFiscal.getAnexos().get(posicao - 1).setCaminho(TipoAnexo.NOTAFISCAL);
            this.notaFiscal.getAnexos().get(posicao - 1).setTipoAnexo(this.anexo.getTipoAnexo());
        } catch (SQLException | IOException ex) {
            Logger.getLogger(managerContrato.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        instanciarAnexo();
        Msg.messagemInfo(Msg.SuccessFull);

    }

    public void deletarAnexo(Anexo a) {
        this.notaFiscal.getAnexos().remove(a);
        Msg.messagemInfo("Anexo removido com sucesso !");
    }

    public String anexoUrl(Anexo anexo) throws IOException {
        String caminhoLogo = "";

        caminhoLogo = anexo.getUrl() + "/" + anexo.getNome();
        String conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File(caminhoLogo)));

        conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File(caminhoLogo)));
        return "data:image/png;base64," + conteudo_base64;
    }

    @Override
    public void instanciar() {
        userLogado = userServico.getCurrentUser();
        instanciarNotaFical();
        instanciarAnexo();
        instanciarSelect();
        instanciarListaNotaFiscal();

    }

    public void gerarDocumentacaoNotaFiscal() throws IOException, DocumentException {
        ModeloDocumento modelo = new ModeloDocumento();
        modelo.setTexto("");
        notaFiscalServico.imprimirModeloNotaFiscal(modelo, this.notaFiscal.getContrato().getContratado(), this.notaFiscal.getContrato(), this.notaFiscal);

    }

    public void instanciarSelect() {
        this.contratos = contratoServico.FindAll();
        this.responsaveis = userServico.FindAll();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarNotaFiscal.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "notaFiscal.xhtml?visualizar=" + this.notaFiscal.getId();
    }

    public List<NotaFiscal> getNotasFiscais() {
        return notasFiscais;
    }

    public void setNotasFiscais(List<NotaFiscal> notasFiscais) {
        this.notasFiscais = notasFiscais;
    }

    public void instanciarAnexo() {
        this.anexo = new Anexo();
    }

    public void instanciarListaNotaFiscal() {
        this.notasFiscais = new ArrayList<>();
    }

    public void instanciarNotaFical() {
        this.notaFiscal = new NotaFiscal();
        this.notaFiscal.setAnexos(new ArrayList<Anexo>());
    }

    public AnexoServico getAnexoServico() {
        return anexoServico;
    }

    public void setAnexoServico(AnexoServico anexoServico) {
        this.anexoServico = anexoServico;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public NotaFiscalServico getNotaFiscalServico() {
        return notaFiscalServico;
    }

    public void setNotaFiscalServico(NotaFiscalServico notaFiscalServico) {
        this.notaFiscalServico = notaFiscalServico;
    }

    public ContratoServico getContratoServico() {
        return contratoServico;
    }

    public void setContratoServico(ContratoServico contratoServico) {
        this.contratoServico = contratoServico;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public UsuarioServico getUserServico() {
        return userServico;
    }

    public void setUserServico(UsuarioServico userServico) {
        this.userServico = userServico;
    }

    public List<Usuario> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(List<Usuario> responsaveis) {
        this.responsaveis = responsaveis;
    }

}
