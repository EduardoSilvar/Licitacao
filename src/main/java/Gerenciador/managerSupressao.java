/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Enum.TipoFiscalizacaoEnum;
import Servico.AnexoServico;
import Servico.ContratoServico;
import Servico.SupressaoServico;
import Servico.UsuarioServico;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.Anexo;
import modelo.Supressao;
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
public class managerSupressao extends managerPrincipal implements Serializable {

    @EJB
    private SupressaoServico supressaoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;

    @EJB
    private AnexoServico anexoServico;

    private Anexo anexo;
    private Supressao supressao;
    private String verificadorRendered;
    private List<Supressao> supressoes;
    private List<Usuario> fiscais;
    private List<Contrato> contratos;
    private Usuario user;
    private Long id;

    @Override
    public void carregar(String param) {
        this.user = userServico.getCurrentUser();
        this.id = Long.parseLong(param);
        this.anexo = new Anexo();
        this.supressao = supressaoServico.find(Long.parseLong(param));
        this.supressoes = new ArrayList<>();
        if (Utils.isNotEmpty(this.user.getUnidadeOrganizacional())) {
            this.fiscais = userServico.FindAll(this.user.getUnidadeOrganizacional());
            this.contratos = contratoServico.FindAll(this.user.getUnidadeOrganizacional());
        } else {
            this.fiscais = userServico.FindAll();
            this.contratos = contratoServico.FindAll();
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
        return "pesquisarSupressao.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "supressao.xhtml?visualizar=" + this.supressao.getId();
    }

    private void instanciarAcrescimo() {
        supressao = new Supressao();
    }

    private void instanciarAcrescimos() {
        this.supressoes = new ArrayList<>();
    }

    private void instanciarSelect() {
        if (Utils.isNotEmpty(this.user.getUnidadeOrganizacional())) {
            this.fiscais = userServico.FindAll(this.user.getUnidadeOrganizacional());
            this.contratos = contratoServico.FindAll(this.user.getUnidadeOrganizacional());
        } else {
            this.fiscais = userServico.FindAll();
            this.contratos = contratoServico.FindAll();
        }

    }

    public void salvar() {
        if (Utils.isNotEmpty(this.supressao.getNumeroTermo())) {
            if (supressaoServico.existNumero(this.supressao.getNumeroTermo())) {
                Msg.messagemError("Número de termo aditivo já registrado !");
            } else {
                Contrato contrato = this.supressao.getContrato();
                contrato.setValor(contrato.getValor().subtract(this.supressao.getValor()));
                contrato.setValorRestante(contrato.getValorRestante().subtract(this.supressao.getValor()));
                contratoServico.Update(contrato);
                supressaoServico.Save(this.supressao);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "supressao.xhtml?visualizar=" + this.supressao.getId() + "&supressao=TRUE");
            }
        }
    }

    public void selecionarContrato() {
        if (this.supressao.getContrato().getTipoFiscalizacao().equals(TipoFiscalizacaoEnum.INDIVIDUAL)) {
            this.supressao.setFiscal(new Usuario());
            this.supressao.setFiscal(this.supressao.getContrato().getFiscal());
        }
    }

    public void atualizar() {
        Contrato contrato = this.supressao.getContrato();
        Supressao supressaoBD = supressaoServico.find(this.supressao.getId());
        Contrato contratoBD = supressaoBD.getContrato();
        BigDecimal valorBD = supressaoBD.getValor();
        BigDecimal valorCampo = this.supressao.getValor();
        BigDecimal valorFinal = BigDecimal.ZERO;
        if (contratoBD.equals(this.supressao.getContrato())) {
            if (valorBD.compareTo(valorCampo) == 1) {
                BigDecimal valorDiferenca = BigDecimal.ZERO;
                valorDiferenca = valorBD.subtract(valorCampo);
                valorFinal = supressaoBD.getContrato().getValorRestante().subtract(valorDiferenca);
            } else if (valorCampo.compareTo(valorBD) == 1) {
                BigDecimal valorDiferenca = BigDecimal.ZERO;
                valorDiferenca = valorCampo.subtract(valorBD);
                valorFinal = supressaoBD.getContrato().getValorRestante().add(valorDiferenca);
            } else {
                valorFinal = supressaoBD.getContrato().getValorRestante();
            }
        } else {
            valorFinal = this.supressao.getContrato().getValorRestante().subtract(this.supressao.getValor());
        }
        contrato.setValor(this.supressao.getContrato().getValor().subtract(this.supressao.getValor()));
        contrato.setValorRestante(valorFinal);
        contratoServico.Update(contrato);
        supressaoServico.Update(this.supressao);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "supressao.xhtml?visualizar=" + this.supressao.getId() + "&supressao=TRUE");
    }

    public void pesquisar(Contrato contrato) {
        this.supressoes = supressaoServico.pesquisarSupressaoPorContrato(contrato, this.supressao);
    }

    public void selecionarAnexo(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.SUPRESSAO);
        adicionarAnexo();
    }

    public void adicionarAnexo() {

        try {
            if (Utils.isEmpty(this.supressao.getAnexos())) {
                this.supressao.setAnexos(new ArrayList<Anexo>());
            }
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                this.supressao.getAnexos().add(anexoServico.adicionarAnexo(this.anexo.getArquivo(), TipoAnexo.SUPRESSAO, user.getUnidadeOrganizacional().getId()));
            } else {
                this.supressao.getAnexos().add(anexoServico.adicionarAnexo(this.anexo.getArquivo(), TipoAnexo.SUPRESSAO, 000l));
            }
            int posicao = supressao.getAnexos().size();
            this.supressao.getAnexos().get(posicao - 1).setArquivo(anexo.getArquivo());
            this.supressao.getAnexos().get(posicao - 1).setCaminho(TipoAnexo.SUPRESSAO);
            this.supressao.getAnexos().get(posicao - 1).setTipoAnexo(this.anexo.getTipoAnexo());
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
        this.supressao.getAnexos().remove(a);
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

    public void deletar() {
        try {
            Supressao NovaSupressao = supressaoServico.find(this.supressao.getId());
            NovaSupressao.setAtivo(false);
            supressaoServico.Update(NovaSupressao);
            supressoes.remove(NovaSupressao);
            if (Utils.isNotEmpty(supressao)) {
                this.supressoes = supressaoServico.findPesquisa(this.supressao);
            }
            if (Utils.isNotEmpty(this.id)) {
                Msg.messagemInfoRedirect("operação realizada com sucesso !", "supressao.xhtml");
            } else {
                Msg.messagemInfo("Processo realizado com sucesso !");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

//    public String urlVisualizar(long id){
//        return "cadastrarAlteracoes.xhtml?visualizar="+id+"&supressao=TRUE";
//    }
//    
//    public String urlEditar(long id){
//        return "cadastrarAlteracoes.xhtml?editar="+id+"&supressao=TRUE";
//    }
//    
//    public boolean verificarSupressao(){
//        boolean verificarMetodo = false;
//        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        this.verificadorRendered = params.get("supressao");
//        if(isVisualizar() || isEditar()){
//            if(this.verificadorRendered != null && !this.verificadorRendered.isEmpty()){
//                verificarMetodo = true;
//            }
//        }
//        if(isCadastrar()) {
//            verificarMetodo = true;
//        } 
//        return verificarMetodo;
//    }
    public SupressaoServico getSupressaoServico() {
        return supressaoServico;
    }

    public void setSupressaoServico(SupressaoServico supressaoServico) {
        this.supressaoServico = supressaoServico;
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

    public Supressao getSupressao() {
        return supressao;
    }

    public void setSupressao(Supressao supressao) {
        this.supressao = supressao;
    }

    public List<Supressao> getSupressoes() {
        return supressoes;
    }

    public void setSupressoes(List<Supressao> supressoes) {
        this.supressoes = supressoes;
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
