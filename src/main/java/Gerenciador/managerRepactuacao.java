/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Enum.TipoFiscalizacaoEnum;
import Servico.AnexoServico;
import Servico.ContratoServico;
import Servico.RepactuacaoServico;
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
 * @author dylan
 */
@ManagedBean
@ViewScoped
public class managerRepactuacao extends managerPrincipal implements Serializable {

    @EJB
    private RepactuacaoServico repactuacaoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;
    @EJB
    private AnexoServico anexoServico;

    private Repactuacao repactuacao;
    private String verificadorRendered;
    private List<Repactuacao> repactuacoes;
    private List<Usuario> fiscais;
    private List<Contrato> contratos;
    private Anexo anexo;
    private Usuario user;
    private Long id;

    @Override
    public void carregar(String param) {
        this.anexo = new Anexo();
        this.user = userServico.getCurrentUser();
        this.id = Long.parseLong(param);
        this.repactuacao = repactuacaoServico.find(Long.parseLong(param));
        this.repactuacoes = new ArrayList<>();
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
        instanciarRepactuacao();
        intanciarRepactuacoes();
    }

    @Override
    public String getUrlPesquisar() {
        return "contratoForm.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "repactuacao.xhtml?visualizar=" + this.repactuacao.getId();
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

    private void instanciarRepactuacao() {
        this.repactuacao = new Repactuacao();
    }

    private void intanciarRepactuacoes() {
        this.repactuacoes = new ArrayList<>();
    }

    public boolean renderedValorMudou() {
        return repactuacao.getValorMudou();
    }

    public void selecionarContrato(){
        if(this.repactuacao.getContrato().getTipoFiscalizacao().equals(TipoFiscalizacaoEnum.INDIVIDUAL)){
            this.repactuacao.setFiscal(new Usuario());
            this.repactuacao.setFiscal(this.repactuacao.getContrato().getFiscal());
        }
    }
    
    public void salvar() {
        if (Utils.isNotEmpty(this.repactuacao.getNumeroTermo())) {
            if (repactuacaoServico.existNumero(this.repactuacao.getNumeroTermo())) {
                Msg.messagemError("Número de termo aditivo já registrado !");
            } else {
                Contrato contrato = this.repactuacao.getContrato();
                BigDecimal valorFinal = BigDecimal.ZERO;
                if (this.repactuacao.getValorMudou()) {
                    contrato.setValor(this.repactuacao.getValor());
                    if (this.repactuacao.getContrato().getValor().compareTo(this.repactuacao.getValor()) == 1) {
                        BigDecimal valorDiferenca = BigDecimal.ZERO;
                        valorDiferenca = this.repactuacao.getContrato().getValor().subtract(this.repactuacao.getValor());
                        valorFinal = contrato.getValorRestante().subtract(valorDiferenca);
                    } else if (this.repactuacao.getValor().compareTo(this.repactuacao.getContrato().getValor()) == 1) {
                        BigDecimal valorDiferenca = BigDecimal.ZERO;
                        valorDiferenca = this.repactuacao.getValor().subtract(this.repactuacao.getContrato().getValor());
                        valorFinal = contrato.getValorRestante().add(valorDiferenca);
                    } else {
                        valorFinal = contrato.getValorRestante();
                    }
                } else {
                    valorFinal = contrato.getValorRestante();
                }
                contrato.setValorRestante(valorFinal);
                contratoServico.Update(this.repactuacao.getContrato());
                repactuacaoServico.Save(this.repactuacao);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "repactuacao.xhtml?visualizar=" + this.repactuacao.getId() + "&repactuacao=TRUE");
            }
        }
    }

    public void atualizar() {
        Contrato contrato = this.repactuacao.getContrato();
        Repactuacao repactuacaoBD = repactuacaoServico.find(this.repactuacao.getId());
        Contrato contratoBD = repactuacaoBD.getContrato();
        BigDecimal valorBD = repactuacaoBD.getValor();
        BigDecimal valorCampo = this.repactuacao.getValor();
        BigDecimal valorFinal = BigDecimal.ZERO;
        if (contratoBD.equals(this.repactuacao.getContrato())) {
            if (this.repactuacao.getValorMudou()) {
                contrato.setValor(this.repactuacao.getValor());
                if (valorBD.compareTo(valorCampo) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = valorBD.subtract(valorCampo);
                    valorFinal = repactuacaoBD.getContrato().getValorRestante().subtract(valorDiferenca);
                } else if (valorCampo.compareTo(valorBD) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = valorCampo.subtract(valorBD);
                    valorFinal = repactuacaoBD.getContrato().getValorRestante().add(valorDiferenca);
                } else {
                    valorFinal = repactuacaoBD.getContrato().getValorRestante();
                }
            } else {
                valorFinal = repactuacaoBD.getContrato().getValorRestante();
            }

        } else {
            if (this.repactuacao.getValorMudou()) {
                contrato.setValor(this.repactuacao.getValor());
                if (this.repactuacao.getContrato().getValor().compareTo(this.repactuacao.getValor()) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = this.repactuacao.getContrato().getValor().subtract(this.repactuacao.getValor());
                    valorFinal = contrato.getValorRestante().subtract(valorDiferenca);
                } else if (this.repactuacao.getValor().compareTo(this.repactuacao.getContrato().getValor()) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = this.repactuacao.getValor().subtract(this.repactuacao.getContrato().getValor());
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
        repactuacaoServico.Update(repactuacao);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "repactuacao.xhtml?visualizar=" + this.repactuacao.getId() + "&repactuacao=TRUE");
    }

    public void pesquisar(Contrato contrato) {
        this.repactuacoes = repactuacaoServico.pesquisarAcrescimoPorContrato(contrato, this.repactuacao);
    }

    public void deletar() {
        try {
            Repactuacao NovaRepactuacao = repactuacaoServico.find(this.repactuacao.getId());
            NovaRepactuacao.setAtivo(false);
            repactuacaoServico.Update(NovaRepactuacao);
            repactuacoes.remove(NovaRepactuacao);
            if (Utils.isNotEmpty(repactuacao)) {
                this.repactuacoes = repactuacaoServico.findPesquisa(this.repactuacao);
            }
            if (Utils.isNotEmpty(this.id)) {
                Msg.messagemInfoRedirect("operação realizada com sucesso !", "repactuacao.xhtml");
            } else {
                Msg.messagemInfo("Processo realizado com sucesso !");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void selecionarAnexo(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.REPACTUACAO);
        adicionarAnexo();
    }

    public void adicionarAnexo() {

        try {
            if (Utils.isEmpty(this.repactuacao.getAnexos())) {
                this.repactuacao.setAnexos(new ArrayList<Anexo>());
            }
            this.repactuacao.getAnexos().add(anexoServico.adicionarAnexo(this.anexo.getArquivo()));
            int posicao = repactuacao.getAnexos().size();
            this.repactuacao.getAnexos().get(posicao - 1).setArquivo(anexo.getArquivo());
            this.repactuacao.getAnexos().get(posicao - 1).setCaminho(TipoAnexo.REPACTUACAO);
            this.repactuacao.getAnexos().get(posicao - 1).setTipoAnexo(this.anexo.getTipoAnexo());
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
        this.repactuacao.getAnexos().remove(a);
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

    public RepactuacaoServico getRepactuacaoServico() {
        return repactuacaoServico;
    }

    public void setRepactuacaoServico(RepactuacaoServico repactuacaoServico) {
        this.repactuacaoServico = repactuacaoServico;
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

    public Repactuacao getRepactuacao() {
        return repactuacao;
    }

    public void setRepactuacao(Repactuacao repactuacao) {
        this.repactuacao = repactuacao;
    }

    public List<Repactuacao> getRepactuacoes() {
        return repactuacoes;
    }

    public void setRepactuacoes(List<Repactuacao> repactuacoes) {
        this.repactuacoes = repactuacoes;
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
