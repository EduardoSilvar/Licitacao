/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.*;
import Servico.AnexoServico;
import Servico.ContratadoServico;
import Servico.ContratoServico;
import Servico.SetorServico;
import Servico.TipoLicitacaoServico;
import Servico.UsuarioServico;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.Anexo;
import modelo.Contratado;
import modelo.Contrato;
import modelo.FiscalVO;
import modelo.ModeloDocumento;
import modelo.Setor;
import modelo.TipoAnexo;
import modelo.TipoLicitacao;
import modelo.Usuario;
import org.primefaces.event.FileUploadEvent;
import util.Msg;
import org.apache.commons.io.FileUtils;
import util.Base64j;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerContrato extends managerPrincipal implements Serializable {

    @EJB
    private ContratoServico contratoServico;
    @EJB
    private ContratadoServico contratadoServico;
    @EJB
    private UsuarioServico userServico;

    @EJB
    private TipoLicitacaoServico tipoLicitacaoServico;
    @EJB
    private SetorServico setorServico;
    @EJB
    private AnexoServico anexoServico;
    @EJB
    UsuarioServico usuarioServico;

    String senha;
    private String email;
    private String assunto;
    private String textoMensagem;
    private Anexo anexo;
    private TipoAnexo tipoAnexo;

    private Contrato contrato;
    private List<Usuario> responsaveis;
    private List<TipoLicitacao> tiposLicitacao;
    private List<Contrato> contratos;
    private List<Contratado> contratados;
    private List<Setor> setores;
    private Usuario fiscal;
    private Usuario userLogado;
    private boolean renderedFiscalIndividual;
    private boolean renderedFiscalComissao;

    @Override
    public void carregar(String param) {
        userLogado = userServico.getCurrentUser();
        System.err.println(userLogado);
        instanciarSelect();
        InstanciarContrato();
        InstanciarContratos();
        this.anexo = new Anexo();
        this.contrato = contratoServico.find(Long.parseLong(param));
        this.contratos = new ArrayList<>();
        if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
            this.responsaveis = userServico.FindAll(userLogado.getUnidadeOrganizacional());
            this.setores = setorServico.FindAll(userLogado.getUnidadeOrganizacional());
        } else {
            this.responsaveis = userServico.FindAll();
            this.setores = setorServico.FindAll();
        }
        verificarTipoFiscal();
    }

    @Override
    public void instanciar() {
        userLogado = userServico.getCurrentUser();
        System.err.println(userLogado);

        this.anexo = new Anexo();
        instanciarSelect();
        InstanciarContrato();
        InstanciarContratos();
        instanciarVerificacaoRendered();
    }

    public void instanciarSelect() {
        if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
            this.setores = setorServico.FindAll(userLogado.getUnidadeOrganizacional());
            this.contratados = contratadoServico.FindAll(userLogado.getUnidadeOrganizacional());
            this.responsaveis = userServico.FindAll(userLogado.getUnidadeOrganizacional());
            this.tiposLicitacao = tipoLicitacaoServico.FindAll(userLogado.getUnidadeOrganizacional());
        } else {
            this.setores = setorServico.FindAll();
            this.contratados = contratadoServico.FindAll();
            this.responsaveis = userServico.FindAll();
            this.tiposLicitacao = tipoLicitacaoServico.FindAll();
        }
    }

    public void instanciarVerificacaoRendered() {
        this.renderedFiscalIndividual = false;
        this.renderedFiscalComissao = false;
    }

    public void adicionarFiscal(Usuario fiscal) {
        if (this.contrato.getFiscaisContrato().contains(fiscal)) {
            Msg.messagemError("O fiscal já foi adicionado !");
        } else {
            this.contrato.getFiscaisContrato().add(fiscal);
            Msg.messagemInfo("Operação realizada com sucesso !");

        }
    }

    public void removerFiscal() {
        this.contrato.getFiscaisContrato().remove(fiscal);
        Msg.messagemInfo("Operação realizada com sucesso !");
    }

    @Override
    public String getUrlVisualizar() {
        return "contrato.xhtml?visualizar=" + this.contrato.getId();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarContrato.xhtml";
    }

    public void selecionarAnexo(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.CONTRATO);
//        PrimeFaces.current().executeScript("PF('dlgAnexo').show();");
        adicionarAnexo();
    }

    public void verificarTipoFiscal() {
        if (Utils.isNotEmpty(this.contrato)) {
            if (Utils.isNotEmpty(this.contrato.getTipoFiscalizacao())) {
                if (this.contrato.getTipoFiscalizacao().equals(TipoFiscalizacaoEnum.INDIVIDUAL)) {
                    this.renderedFiscalIndividual = true;
                    this.renderedFiscalComissao = false;
                } else {
                    this.renderedFiscalIndividual = false;
                    this.renderedFiscalComissao = true;
                }
            } else {
                this.renderedFiscalComissao = false;
                this.renderedFiscalIndividual = false;
            }
        }
    }

    public void adicionarAnexo() {

        try {
            this.contrato.getAnexos().add(anexoServico.adicionarAnexo(this.anexo.getArquivo()));
            int posicao = contrato.getAnexos().size();
//            this.contrato.getAnexos().get(posicao - 1).setTipo(anexo.getTipo());
            this.contrato.getAnexos().get(posicao - 1).setArquivo(anexo.getArquivo());
            this.contrato.getAnexos().get(posicao - 1).setCaminho(TipoAnexo.CONTRATO);
            this.contrato.getAnexos().get(posicao - 1).setTipoAnexo(this.anexo.getTipoAnexo());
        } catch (SQLException | IOException ex) {
            Logger.getLogger(managerContrato.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        instanciarAnexo();
        Msg.messagemInfo(Msg.SuccessFull);

    }

    public void instanciarAnexo() {
        this.anexo = new Anexo();
    }

    public void salvar() {
        userLogado = userServico.getCurrentUser();

        if (Utils.isNotEmpty(this.contrato.getNumeroContrato())) {
            if (contratoServico.existNumero(this.contrato.getNumeroContrato())) {
                Msg.messagemError("Número de contrato já registrado !");
            } else {
                if (Utils.isNotEmpty(userLogado)) {
                    if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
                        this.contrato.setUnidadeOrganizacional(userLogado.getUnidadeOrganizacional());
                    }
                }
                this.contrato.setCorStatus(cores(this.contrato.getStatus()));
                this.contrato.setValorRestante(this.contrato.getValor());
                contratoServico.Save(this.contrato);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contrato.xhtml?visualizar=" + this.contrato.getId());
            }
        }
    }

    public void pesquisar() {
        userLogado = userServico.getCurrentUser();

        if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
            this.contratos = contratoServico.findPesquisa(this.contrato, userLogado.getUnidadeOrganizacional());
            if (this.contratos.size() > 0) {
                Msg.messagemInfo("Operação realizada com sucesso !!");
            } else {
                Msg.messagemError("Nenhum Contrato encontrato !");
            }
        } else {
            this.contratos = contratoServico.findPesquisa();
            if (this.contratos.size() > 0) {
                Msg.messagemInfo("Operação realizada com sucesso !!");
            } else {
                Msg.messagemError("Nenhum Contrato encontrato !");
            }
        }
    }

    public void deletar() {
        try {
            Contrato novoContrato = contratoServico.find(this.contrato.getId());
            novoContrato.setAtivo(false);
            contratoServico.Update(novoContrato);
            contratos.remove(novoContrato);
            Msg.messagemInfoRedirect("Processo realizado com sucesso !", "pesquisarContrato.xhtml");
            pesquisar();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void atualizar() {
        Contrato contratoBD = contratoServico.find(this.contrato.getId());
        BigDecimal valorBD = contratoBD.getValor();
        BigDecimal valorCampo = this.contrato.getValor();
        BigDecimal valorFinal = BigDecimal.ZERO;
        if (valorBD.compareTo(valorCampo) == 1) {
            BigDecimal valorDiferenca = BigDecimal.ZERO;
            valorDiferenca = valorBD.subtract(valorCampo);
            valorFinal = contratoBD.getValorRestante().subtract(valorDiferenca);
        } else if (valorCampo.compareTo(valorBD) == 1) {
            BigDecimal valorDiferenca = BigDecimal.ZERO;
            valorDiferenca = valorCampo.subtract(valorBD);
            valorFinal = contratoBD.getValorRestante().add(valorDiferenca);
        } else {
            valorFinal = contratoBD.getValorRestante();
        }
        this.contrato.setValorRestante(valorFinal);
        contratoServico.Update(this.contrato);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contrato.xhtml?visualizar=" + this.contrato.getId());
    }

    public String abreviacao(StatusContrato stts) {
        String abreviacao = "teste";
        if (Utils.isNotEmpty(stts)) {
            switch (stts) {
                case APROVADO:
                    abreviacao = "AP";
                    break;
                case CANCELADO:
                    abreviacao = "CA";
                    break;
                case ESPERANDO_INICIO:
                    abreviacao = "EI";
                    break;
                case EXPIRADO:
                    abreviacao = "EP";
                    break;
                case FINALIZADO:
                    abreviacao = "FI";
                    break;
                case INICIADO:
                    abreviacao = "IN";
                    break;
                case PAGO:
                    abreviacao = "PG";
                    break;
                case PROXIMO_EXPIRAR:
                    abreviacao = "PE";
                    break;
                case VIGENCIA:
                    abreviacao = "VI";
                    break;
                default:
                    break;
            }
        }
        return abreviacao;

    }

    public String cores(StatusContrato stts) {
        String cor = "teste";
        if (Utils.isNotEmpty(stts)) {
            switch (stts) {
                case APROVADO:
                    cor = "005CFE";
                    break;
                case CANCELADO:
                    cor = "FF0100";
                    break;
                case ESPERANDO_INICIO:
                    cor = "7736FF";
                    break;
                case EXPIRADO:
                    cor = "FED800";
                    break;
                case FINALIZADO:
                    cor = "7E0EAC";
                    break;
                case INICIADO:
                    cor = "ced9db";
                    break;
                case PAGO:
                    cor = "89F629";
                    break;
                case PROXIMO_EXPIRAR:
                    cor = "3FF900";
                    break;
                case VIGENCIA:
                    cor = "88F629";
                    break;
                default:
                    break;
            }
        }
        return cor;

    }

    public void enviarEmail() {
        if (Utils.isNotEmpty(this.contrato)) {
            if (Utils.isNotEmpty(this.contrato.getContratado())) {
                if (Utils.isNotEmpty(this.contrato.getContratado().getEmail())) {
                    if (verificaEmail(this.contrato.getContratado().getNome(), "cveduardo73@gmail.com", assunto)) {
                        Msg.messagemInfo("Mensagem enviada com sucesso. ");
                    } else {
                        Msg.messagemError("Ocorreu um erro, verifique o email do contratado !");
                    }
                } else {
                    Msg.messagemInfo("O contratado não possui email !");
                }
            }
        }
    }

    public boolean verificaEmail(String nome, String email, String assunto) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.hostinger.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("notificacao@planejacontratos.com.br", "@Adminplaneja1@");
            }
        });

        session.setDebug(false);
        int valor = 0;
        try {
            for (int i = 0; i <= 3; i++) {
                Random aleatorio = new Random();
                valor = valor + aleatorio.nextInt(100) + 1;
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("notificacao@planejacontratos.com.br"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject(assunto);
//            message.setText("A sua nova senha é : \n\n" +senha);
            message.setContent("<html><head></head><body> " + this.textoMensagem + "</b></body></html>", "text/html");
            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean renderedTempoDeterminado() {
        return contrato.isPossuiTempoDeterminado();
    }

    public Contrato Pegar(Long id) {
        return contratoServico.find(id);
    }

    public List<Contrato> PegarTodos() {
        if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
            contratos = contratoServico.FindAll(userLogado.getUnidadeOrganizacional());
            return contratos;
        } else {
            contratos = contratoServico.FindAll();
            return contratos;
        }
    }

    public void InstanciarContratos() {
        contratos = new ArrayList<>();
    }

    public void InstanciarContrato() {
        contrato = new Contrato();
        contrato.setFiscalContrato(new ArrayList<Usuario>());
        contrato.setAnexos(new ArrayList<Anexo>());
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void deletarAnexo(Anexo a) {
        this.contrato.getAnexos().remove(a);
        Msg.messagemInfo("Anexo removido com sucesso !");
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

    public String anexoUrl(Anexo anexo) throws IOException {
        String caminhoLogo = "";

        caminhoLogo = anexo.getUrl() + "/" + anexo.getNome();
        String conteudo_base64 = "";
        if (anexo.getNome().contains(".pdf")) {
            conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File("/opt/Licitacao/logoPDF.png")));
        } else {
            conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File(caminhoLogo)));
        }
        return "data:image/png;base64," + conteudo_base64;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<Contratado> getContratados() {
        return contratados;
    }

    public void setContratados(List<Contratado> contratados) {
        this.contratados = contratados;
    }

    public List<Usuario> getResponsaveis() {
        return responsaveis;
    }

    public String getTextoMensagem() {
        return textoMensagem;
    }

    public void setTextoMensagem(String textoMensagem) {
        this.textoMensagem = textoMensagem;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public void setResponsaveis(List<Usuario> responsaveis) {
        this.responsaveis = responsaveis;
    }

    public ContratoServico getContratoServico() {
        return contratoServico;
    }

    public void setContratoServico(ContratoServico contratoServico) {
        this.contratoServico = contratoServico;
    }

    public ContratadoServico getContratadoServico() {
        return contratadoServico;
    }

    public void setContratadoServico(ContratadoServico contratadoServico) {
        this.contratadoServico = contratadoServico;
    }

    public UsuarioServico getUserServico() {
        return userServico;
    }

    public void setUserServico(UsuarioServico userServico) {
        this.userServico = userServico;
    }

    public TipoLicitacaoServico getTipoLicitacaoServico() {
        return tipoLicitacaoServico;
    }

    public void setTipoLicitacaoServico(TipoLicitacaoServico tipoLicitacaoServico) {
        this.tipoLicitacaoServico = tipoLicitacaoServico;
    }

    public List<TipoLicitacao> getTiposLicitacao() {
        return tiposLicitacao;
    }

    public void setTiposLicitacao(List<TipoLicitacao> tiposLicitacao) {
        this.tiposLicitacao = tiposLicitacao;
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public void setSetores(List<Setor> setores) {
        this.setores = setores;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public TipoAnexo getTipoAnexo() {
        return tipoAnexo;
    }

    public void setTipoAnexo(TipoAnexo tipoAnexo) {
        this.tipoAnexo = tipoAnexo;
    }

    public Usuario getUserLogado() {
        return userLogado;
    }

    public void setUserLogado(Usuario userLogado) {
        this.userLogado = userLogado;
    }

    public boolean isRenderedFiscalIndividual() {
        return renderedFiscalIndividual;
    }

    public void setRenderedFiscalIndividual(boolean renderedFiscalIndividual) {
        this.renderedFiscalIndividual = renderedFiscalIndividual;
    }

    public boolean isRenderedFiscalComissao() {
        return renderedFiscalComissao;
    }

    public void setRenderedFiscalComissao(boolean renderedFiscalComissao) {
        this.renderedFiscalComissao = renderedFiscalComissao;
    }

    public Usuario getFiscal() {
        return fiscal;
    }

    public void setFiscal(Usuario fiscal) {
        this.fiscal = fiscal;
    }

}
