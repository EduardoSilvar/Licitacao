/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Enum.TipoFiscalizacaoEnum;
import Servico.AcrescimoServico;
import Servico.AnexoServico;
import Servico.ContratoServico;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.Acrescimo;
import modelo.Anexo;
import modelo.Contrato;
import modelo.TipoAnexo;
import modelo.Usuario;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import util.Base64j;
import util.Msg;
import util.Utils;

/**
 *
 * @author dylan
 */
@ManagedBean
@ViewScoped
public class managerAcrescimo extends managerPrincipal implements Serializable {

    @EJB
    private AcrescimoServico acrescimoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;
    @EJB
    private AnexoServico anexoServico;

    private Acrescimo acrescimo;
    private String verificadorRendered;
    private List<Acrescimo> acrescimos;
    private List<Usuario> fiscais;
    private Anexo anexo;
    private List<Contrato> contratos;
    private Usuario user;
    private Long id;

    @Override
    public void carregar(String param) {
        this.anexo = new Anexo();
        this.user = userServico.getCurrentUser();
        this.id = Long.parseLong(param);
        this.acrescimo = acrescimoServico.find(Long.parseLong(param));
        this.acrescimos = new ArrayList<>();
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            this.fiscais = userServico.FindAll(user.getUnidadeOrganizacional());
            this.contratos = contratoServico.FindAll(user.getUnidadeOrganizacional());
        } else {
            this.contratos = contratoServico.FindAll();
            this.fiscais = userServico.FindAll();
        }

    }

    @Override
    public void instanciar() {
        this.anexo = new Anexo();
        this.user = userServico.getCurrentUser();
        instanciarSelect();
        instanciarAcrescimo();
        instanciarAcrescimos();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarAcrescimo.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "cadastrarAlteracoes.xhtml?visualizar=" + this.acrescimo.getId();
    }

    private void instanciarAcrescimo() {
        acrescimo = new Acrescimo();
    }

    private void instanciarAcrescimos() {
        this.acrescimos = new ArrayList<>();
    }

    private void instanciarSelect() {
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            this.fiscais = userServico.FindAll(user.getUnidadeOrganizacional());
            this.contratos = contratoServico.FindAll(user.getUnidadeOrganizacional());
        } else {
            this.fiscais = userServico.FindAll();
            this.contratos = contratoServico.FindAll();
        }

    }

    public void selecionarAnexo(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.ACRESCIMO);
        adicionarAnexo();
    }

    public void selecionarContrato() {
        if (this.acrescimo.getContrato().getTipoFiscalizacao().equals(TipoFiscalizacaoEnum.INDIVIDUAL)) {
            this.acrescimo.setFiscal(new Usuario());
            this.acrescimo.setFiscal(this.acrescimo.getContrato().getFiscal());
        }
    }

    public void adicionarAnexo() {

        try {
            if (Utils.isEmpty(this.acrescimo.getAnexos())) {
                this.acrescimo.setAnexos(new ArrayList<Anexo>());
            }
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                this.acrescimo.getAnexos().add(anexoServico.adicionarAnexo(this.anexo.getArquivo(), TipoAnexo.ACRESCIMO, user.getUnidadeOrganizacional().getId()));
            } else {
                this.acrescimo.getAnexos().add(anexoServico.adicionarAnexo(this.anexo.getArquivo(), TipoAnexo.ACRESCIMO, 000l));

            }
            int posicao = acrescimo.getAnexos().size();
            this.acrescimo.getAnexos().get(posicao - 1).setArquivo(anexo.getArquivo());
            this.acrescimo.getAnexos().get(posicao - 1).setCaminho(TipoAnexo.ACRESCIMO);
            this.acrescimo.getAnexos().get(posicao - 1).setTipoAnexo(this.anexo.getTipoAnexo());
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
        this.acrescimo.getAnexos().remove(a);
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
        return "data:image/png;base64," + conteudo_base64;
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

            response.setContentType("application/pdf");
            response.setContentLength(arquivo.length);
            ouputStream.write(arquivo, 0, arquivo.length);
            ouputStream.flush();
            ouputStream.close();

        } catch (DocumentException ex) {
            Logger.getLogger(managerNotaFiscal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {
        if (Utils.isNotEmpty(this.acrescimo.getNumeroTermo())) {
//            if (acrescimoServico.existNumero(this.acrescimo.getNumeroTermo())) {
//                Msg.messagemError("Número de termo aditivo já registrado !");
//            } else {
            Contrato contrato = this.acrescimo.getContrato();
            contrato.setValor(this.acrescimo.getValor().add(contrato.getValor()));
            acrescimoServico.Save(this.acrescimo);
            contrato.setValorRestante(contrato.getValorRestante().add(this.acrescimo.getValor()));
            contratoServico.Update(contrato);
            Msg.messagemInfoRedirect("Operação realizada com sucesso !", "acrescimo.xhtml?visualizar=" + this.acrescimo.getId() + "&acrescimo=TRUE");
//            }
        }
    }

    public void atualizar() {
        Contrato contrato = this.acrescimo.getContrato();
        Acrescimo acrescimoBD = acrescimoServico.find(this.acrescimo.getId());
        Contrato contratoBD = acrescimoBD.getContrato();
        BigDecimal valorBD = acrescimoBD.getValor();
        BigDecimal valorCampo = this.acrescimo.getValor();
        BigDecimal valorFinal = BigDecimal.ZERO;
        if (contratoBD.equals(this.acrescimo.getContrato())) {
            if (valorBD.compareTo(valorCampo) == 1) {
                BigDecimal valorDiferenca = BigDecimal.ZERO;
                valorDiferenca = valorBD.subtract(valorCampo);
                valorFinal = acrescimoBD.getContrato().getValorRestante().subtract(valorDiferenca);
            } else if (valorCampo.compareTo(valorBD) == 1) {
                BigDecimal valorDiferenca = BigDecimal.ZERO;
                valorDiferenca = valorCampo.subtract(valorBD);
                valorFinal = acrescimoBD.getContrato().getValorRestante().add(valorDiferenca);
            } else {
                valorFinal = acrescimoBD.getContrato().getValorRestante();
            }
        } else {
            valorFinal = this.acrescimo.getContrato().getValorRestante().add(this.acrescimo.getValor());
        }
        contrato.setValor(this.acrescimo.getValor().add(contrato.getValor()));
        contrato.setValorRestante(valorFinal);
        contratoServico.Update(contrato);
        acrescimoServico.Update(this.acrescimo);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "acrescimo.xhtml?visualizar=" + this.acrescimo.getId() + "&acrescimo=TRUE");
    }

    public void pesquisar(Contrato contrato) {
        this.acrescimos = acrescimoServico.pesquisarAcrescimoPorContrato(contrato, this.acrescimo);
        if (Utils.isNotEmpty(acrescimos)) {
            Msg.messagemInfo("pesquisa realizada com sucesso !");
        } else {
            Msg.messagemError("Nenhum acréscimo encontrado !");
        }
    }

    public void deletar() {
        try {
            Acrescimo NovoAcrescimo = acrescimoServico.find(this.acrescimo.getId());
            NovoAcrescimo.setAtivo(false);
            acrescimoServico.Update(NovoAcrescimo);
            acrescimos.remove(NovoAcrescimo);
            if (Utils.isNotEmpty(acrescimo)) {
                this.acrescimos = acrescimoServico.findPesquisa(this.acrescimo);
            }
            if (Utils.isNotEmpty(this.id)) {
                Msg.messagemInfoRedirect("operação realizada com sucesso !", "acrescimo.xhtml");
            } else {
                Msg.messagemInfo("Processo realizado com sucesso !");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

//    public String urlVisualizar(long id) {
//        return "cadastrarAlteracoes.xhtml?visualizar=" + id + "&acrescimo=TRUE";
//    }
//
//    public String urlEditar(long id) {
//        return "cadastrarAlteracoes.xhtml?editar=" + id + "&acrescimo=TRUE";
//    }
//    public boolean verificarAcrescimo() {
//        boolean verificarMetodo = false;
//        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        this.verificadorRendered = params.get("acrescimo");
//        if (isVisualizar() || isEditar()) {
//            if (this.verificadorRendered != null && !this.verificadorRendered.isEmpty()) {
//                verificarMetodo = true;
//            }
//        }
//        if (isCadastrar()) {
//            verificarMetodo = true;
//        }
//        return verificarMetodo;
//    }
    public AcrescimoServico getAcrescimoServico() {
        return acrescimoServico;
    }

    public void setAcrescimoServico(AcrescimoServico acrescimoServico) {
        this.acrescimoServico = acrescimoServico;
    }

    public ContratoServico getContratoServico() {
        return contratoServico;
    }

    public void setContratoServico(ContratoServico contratoServico) {
        this.contratoServico = contratoServico;
    }

    public UsuarioServico getUserServico() {
        return userServico;
    }

    public void setUserServico(UsuarioServico userServico) {
        this.userServico = userServico;
    }

    public Acrescimo getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(Acrescimo acrescimo) {
        this.acrescimo = acrescimo;
    }

    public List<Acrescimo> getAcrescimos() {
        return acrescimos;
    }

    public void setAcrescimos(List<Acrescimo> acrescimos) {
        this.acrescimos = acrescimos;
    }

    public List<Usuario> getFiscais() {
        return fiscais;
    }

    public void setFiscais(List<Usuario> fiscais) {
        this.fiscais = fiscais;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerificadorRendered() {
        return verificadorRendered;
    }

    public void setVerificadorRendered(String verificadorRendered) {
        this.verificadorRendered = verificadorRendered;
    }

}
