/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.AcrescimoServico;
import Servico.AnexoServico;
import Servico.ContratoServico;
import Servico.NotaFiscalServico;
import Servico.RepactuacaoServico;
import Servico.UsuarioServico;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.Acrescimo;
import modelo.Anexo;
import modelo.Contratado;
import modelo.Contrato;
import modelo.ModeloDocumento;
import modelo.NotaFiscal;
import modelo.Repactuacao;
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
    @EJB
    private AcrescimoServico acrescimoServico;
    @EJB
    private RepactuacaoServico repactuacaoServico;

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
        contrato.setValorRestante(contrato.getValorRestante().subtract(new BigDecimal(this.notaFiscal.getValor().toString())));
        contratoServico.Update(contrato);
        notaFiscalServico.Save(this.notaFiscal);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "notaFiscal.xhtml?visualizar=" + this.notaFiscal.getId());
    }

    public void verificarData() {
        if (Utils.isNotEmpty(notaFiscal.getInicioFiscalizado())) {
            if (this.notaFiscal.getInicioFiscalizado().before(this.notaFiscal.getFinalFiscalizado())) {
                Msg.messagemError("A data final deve ser posterior à data inicial.");
            }
        } else {
            Msg.messagemError("A data final deve ser posterior à data inicial.");
        }
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

//    public void validateSecondDate(FacesContext context, UIComponent component, Object value) {
//        Date secondDate = (Date) value;
//        if (secondDate != null && secondDate.before(notaFiscal.getInicioFiscalizado())) {
//            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "A data inicial deve ser preenchida primeiro", null));
//        }
//    }
    public List<Contrato> autocompletaContrato(String nome) {
        return contratoServico.findContrato(nome, userLogado.getUnidadeOrganizacional());
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

    public List<Acrescimo> listAcrescimo(Contrato contrato) {
        return acrescimoServico.pesquisarAcrescimoPorContrato(contrato);
    }

    public List<Repactuacao> listRepactuacao(Contrato contrato) {
        return repactuacaoServico.pesquisarRepactuacaoPorContrato(contrato);
    }

    public List<NotaFiscal> listNotasFiscais(Contrato contrato) {
        return notaFiscalServico.listNotaFiscalContrato(contrato);
    }

    public void visualizarAnexo(Anexo anexo) throws IOException, IOException, Exception {
        try {
            String caminhoLogo = "";
            String conteudo_base64 = "";
            caminhoLogo = anexo.getUrl() + "/" + anexo.getNome();
            byte[] arquivo = null;

            File file = new File(caminhoLogo);
            arquivo = fileToByte(file);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            ServletOutputStream ouputStream = response.getOutputStream();
            Integer totalPaginas = 0;

//            if (modeloDocumento.getTexto() != null) {
//                export(modeloDocumento.getTexto(), ouputStream, totalPaginas);
//            } else if (modeloDocumento.getAnexos() != null) {
//                export(modeloDocumento.getAnexos(), ouputStream, config, totalPaginas, startPage, false);
//            }
            response.setContentType("application/pdf");
            response.setContentLength(arquivo.length);
            ouputStream.write(arquivo, 0, arquivo.length);
            ouputStream.flush();
            ouputStream.close();

        } catch (DocumentException ex) {
            Logger.getLogger(managerNotaFiscal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isPDF(Anexo anexo) {
        return anexo.getNome().contains(".pdf");

    }

    public byte[] fileToByte(File imagem) throws Exception {
        FileInputStream fis = new FileInputStream(imagem);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead = 0;
        while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    public void deletarAnexo(Anexo a) {
        this.notaFiscal.getAnexos().remove(a);
        Msg.messagemInfo("Anexo removido com sucesso !");
    }

    public String anexoUrl(Anexo anexo) throws IOException {
        String caminhoLogo = "";
        String conteudo_base64 = "";
        caminhoLogo = anexo.getUrl() + "/" + anexo.getNome();
        if (anexo.getNome().contains(".pdf")) {
            conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File("/opt/Licitacao/logoPDF.png")));
        } else {
            conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File(caminhoLogo)));
        }
//        conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File(caminhoLogo)));
        return "data:image/png;base64," + conteudo_base64;
    }

    @Override
    public void instanciar() {
        userLogado = userServico.getCurrentUser();
        instanciarNotaFical();
        instanciarAnexo();
        instanciarSelect();
        instanciarListaNotaFiscal();
        instanciarSelectBooleans();
    }

    public void gerarDocumentacaoNotaFiscal() throws IOException, DocumentException {
        ModeloDocumento modelo = new ModeloDocumento();
        modelo.setTexto("");
        if (Utils.isNotEmpty(userLogado)) {
            if (Utils.isNotEmpty(userLogado.getGrupos())) {
                if (userLogado.getGrupos().get(0).getNome().equals("gestor")) {
                    notaFiscalServico.imprimirModeloNotaFiscal(modelo, this.notaFiscal.getContrato().getContratado(), this.notaFiscal.getContrato(), this.notaFiscal, this.userLogado.getUnidadeOrganizacional(), listAcrescimo(this.notaFiscal.getContrato()), listRepactuacao(this.notaFiscal.getContrato()), userLogado, listNotasFiscais(this.notaFiscal.getContrato()));
                } else {
                    notaFiscalServico.imprimirModeloNotaFiscal(modelo, this.notaFiscal.getContrato().getContratado(), this.notaFiscal.getContrato(), this.notaFiscal, this.userLogado.getUnidadeOrganizacional());
                }
                System.err.println(userLogado.getGrupos().get(0).getNome());

            }
        }
    }

    public void instanciarSelectBooleans() {
        this.notaFiscal.setObrigacaoMensalSeAplica(true);
        this.notaFiscal.setPrazoEstabelecidoSeAplica(true);
        this.notaFiscal.setDocumentoObrigatorioSeAplica(true);
        this.notaFiscal.setRelatorioSeAplica(true);
        this.notaFiscal.setQualidadeEsperadaSeAplica(true);
        this.notaFiscal.setInformouSituacaoSeAplica(true);
        this.notaFiscal.setDiligenciaNecessariasSeAplica(true);
    }

    public void instanciarSelect() {
        this.contratos = contratoServico.FindAll(userLogado.getUnidadeOrganizacional());
        this.responsaveis = userServico.FindAll(userLogado.getUnidadeOrganizacional());
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
