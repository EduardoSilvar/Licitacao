/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Enum.TipoFiscalizacaoEnum;
import Servico.AnexoServico;
import Servico.ContratoServico;
import Servico.RenovacaoServico;
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
import modelo.Anexo;
import modelo.Contrato;
import modelo.Renovacao;
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
public class managerRenovacao extends managerPrincipal implements Serializable {

    @EJB
    private RenovacaoServico renovacaoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;
    @EJB
    private AnexoServico anexoServico;

    private Anexo anexo;
    private Renovacao renovacao;
    private String verificadorRendered;
    private List<Renovacao> renovacoes;
    private List<Usuario> fiscais;
    private List<Contrato> contratos;
    private Usuario user;
    private Long id;

    @Override
    public void carregar(String param) {
        this.anexo = new Anexo();
        this.user = userServico.getCurrentUser();
        this.id = Long.parseLong(param);
        this.renovacao = renovacaoServico.find(Long.parseLong(param));
        this.renovacoes = new ArrayList<>();
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
        this.user = userServico.getCurrentUser();
        this.anexo = new Anexo();
        instanciarSelect();
        instanciarRenovacao();
        intanciarRenovacoes();
    }

    @Override
    public String getUrlPesquisar() {
        return "contratoForm.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "renovacao.xhtml?visualizar=" + this.renovacao.getId();
    }

    private void instanciarRenovacao() {
        this.renovacao = new Renovacao();
    }

    private void intanciarRenovacoes() {
        this.renovacoes = new ArrayList<>();
    }

    public boolean renderedValorMudou() {
        return renovacao.getValorMudou();
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
        if (Utils.isNotEmpty(this.renovacao.getNumeroTermo())) {
            if (renovacaoServico.existNumero(this.renovacao.getNumeroTermo())) {
                Msg.messagemError("Número de termo aditivo já registrado !");
            } else {
                Contrato contrato = this.renovacao.getContrato();
                BigDecimal valorFinal = BigDecimal.ZERO;
                if (this.renovacao.getValorMudou()) {
                    contrato.setValor(this.renovacao.getValor());
                    if (this.renovacao.getContrato().getValor().compareTo(this.renovacao.getValor()) == 1) {
                        BigDecimal valorDiferenca = BigDecimal.ZERO;
                        valorDiferenca = this.renovacao.getContrato().getValor().subtract(this.renovacao.getValor());
                        valorFinal = contrato.getValorRestante().subtract(valorDiferenca);
                    } else if (this.renovacao.getValor().compareTo(this.renovacao.getContrato().getValor()) == 1) {
                        BigDecimal valorDiferenca = BigDecimal.ZERO;
                        valorDiferenca = this.renovacao.getValor().subtract(this.renovacao.getContrato().getValor());
                        valorFinal = contrato.getValorRestante().add(valorDiferenca);
                    } else {
                        valorFinal = contrato.getValorRestante();
                    }
                } else {
                    valorFinal = contrato.getValorRestante();
                }
                contrato.setValorRestante(valorFinal);
                contratoServico.Update(this.renovacao.getContrato());
                renovacaoServico.Save(this.renovacao);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "renovacao.xhtml?visualizar=" + this.renovacao.getId() + "&renovacao=TRUE");
            }
        }
    }

    public void atualizar() {
        Contrato contrato = this.renovacao.getContrato();
        Renovacao renovacaoBD = renovacaoServico.find(this.renovacao.getId());
        Contrato contratoBD = renovacaoBD.getContrato();
        BigDecimal valorBD = renovacaoBD.getValor();
        BigDecimal valorCampo = this.renovacao.getValor();
        BigDecimal valorFinal = BigDecimal.ZERO;
        if (contratoBD.equals(this.renovacao.getContrato())) {
            if (this.renovacao.getValorMudou()) {
                contrato.setValor(this.renovacao.getValor());
                if (valorBD.compareTo(valorCampo) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = valorBD.subtract(valorCampo);
                    valorFinal = renovacaoBD.getContrato().getValorRestante().subtract(valorDiferenca);
                } else if (valorCampo.compareTo(valorBD) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = valorCampo.subtract(valorBD);
                    valorFinal = renovacaoBD.getContrato().getValorRestante().add(valorDiferenca);
                } else {
                    valorFinal = renovacaoBD.getContrato().getValorRestante();
                }
            } else {
                valorFinal = renovacaoBD.getContrato().getValorRestante();
            }

        } else {
            if (this.renovacao.getValorMudou()) {
                contrato.setValor(this.renovacao.getValor());
                if (this.renovacao.getContrato().getValor().compareTo(this.renovacao.getValor()) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = this.renovacao.getContrato().getValor().subtract(this.renovacao.getValor());
                    valorFinal = contrato.getValorRestante().subtract(valorDiferenca);
                } else if (this.renovacao.getValor().compareTo(this.renovacao.getContrato().getValor()) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = this.renovacao.getValor().subtract(this.renovacao.getContrato().getValor());
                    valorFinal = contrato.getValorRestante().add(valorDiferenca);
                } else {
                    valorFinal = contrato.getValorRestante();
                }
            } else {
                valorFinal = contrato.getValorRestante();
            }
        }
        contrato.setValorRestante(valorFinal);
        contratoServico.Update(contrato);
        renovacaoServico.Update(this.renovacao);
        System.out.println("qualquer coisa");
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "renovacao.xhtml?visualizar=" + this.renovacao.getId() + "&renovacao=TRUE");
    }

    public void pesquisar(Contrato contrato) {
        this.renovacoes = renovacaoServico.pesquisarRenovacaoPorContrato(contrato, this.renovacao);
    }

    public void selecionarAnexo(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.RENOVACAO);
        adicionarAnexo();
    }
    
    public void selecionarContrato(){
        if(this.renovacao.getContrato().getTipoFiscalizacao().equals(TipoFiscalizacaoEnum.INDIVIDUAL)){
            this.renovacao.setFiscal(new Usuario());
            this.renovacao.setFiscal(this.renovacao.getContrato().getFiscal());
        }
    }

    public void adicionarAnexo() {

        try {
            if (Utils.isEmpty(this.renovacao.getAnexos())) {
                this.renovacao.setAnexos(new ArrayList<Anexo>());
            }
            this.renovacao.getAnexos().add(anexoServico.adicionarAnexo(this.anexo.getArquivo()));
            int posicao = renovacao.getAnexos().size();
            this.renovacao.getAnexos().get(posicao - 1).setArquivo(anexo.getArquivo());
            this.renovacao.getAnexos().get(posicao - 1).setCaminho(TipoAnexo.RENOVACAO);
            this.renovacao.getAnexos().get(posicao - 1).setTipoAnexo(this.anexo.getTipoAnexo());
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
        this.renovacao.getAnexos().remove(a);
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
            Renovacao NovaRenovacao = renovacaoServico.find(this.renovacao.getId());
            NovaRenovacao.setAtivo(false);
            renovacaoServico.Update(NovaRenovacao);
            renovacoes.remove(NovaRenovacao);
            if (Utils.isNotEmpty(renovacao)) {
                this.renovacoes = renovacaoServico.findPesquisa(this.renovacao);
            }
            if (Utils.isNotEmpty(this.id)) {
                Msg.messagemInfoRedirect("operação realizada com sucesso !", "renovacao.xhtml");
            } else {
                Msg.messagemInfo("Processo realizado com sucesso !");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public RenovacaoServico getRenovacaoServico() {
        return renovacaoServico;
    }

    public void setRenovacaoServico(RenovacaoServico renovacaoServico) {
        this.renovacaoServico = renovacaoServico;
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

    public Renovacao getRenovacao() {
        return renovacao;
    }

    public void setRenovacao(Renovacao renovacao) {
        this.renovacao = renovacao;
    }

    public List<Renovacao> getRenovacoes() {
        return renovacoes;
    }

    public void setRenovacoes(List<Renovacao> renovacoes) {
        this.renovacoes = renovacoes;
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
