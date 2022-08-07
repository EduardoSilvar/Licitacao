/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import static Gerenciador.managerLogin.VerificarLogin;
import static Gerenciador.managerLogin.getObjectSession;
import Servico.AnexoServico;
import Servico.NotaFiscalServico;
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
    private NotaFiscal notaFiscal;
    private List<NotaFiscal> notasFiscais;
    private Anexo anexo;
    private Usuario user;

    @Override
    public void carregar(String param) {
        try {
            user = (Usuario) getObjectSession("usuarioLogado");
//            VerificarLogin();
            instanciarAnexo();
            this.notaFiscal = notaFiscalServico.find(Long.parseLong(param));
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {
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
        this.notasFiscais = notaFiscalServico.pesquisar(notaFiscal);
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
        try {
            user = (Usuario) getObjectSession("usuarioLogado");
//            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        instanciarNotaFical();
        instanciarAnexo();
        instanciarListaNotaFiscal();

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

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

}
