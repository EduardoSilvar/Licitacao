/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Enum.TipoCabecalhoRodape;
import Servico.AnexoServico;
import Servico.CabecalhoRodapeServico;
import Servico.UnidadeOrganizacionalServico;
import Servico.UsuarioServico;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Anexo;
import modelo.CabecalhoRodape;
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
public class managerCabecalhoRodape extends managerPrincipal implements Serializable {

    @EJB
    private CabecalhoRodapeServico cabecalhoRodapeServico;
    @EJB
    private AnexoServico anexoServico;
    @EJB
    private UsuarioServico userServico;
    @EJB
    private UnidadeOrganizacionalServico unidadeOrganizacionalServico;

    private Usuario userLogado;
    private CabecalhoRodape cabecalhoRodape;
    private List<CabecalhoRodape> listcabecalhoRodape;
    private Anexo anexo;
    private Boolean telaCabecalho;

    @Override
    public void carregar(String param) {
        this.cabecalhoRodape = cabecalhoRodapeServico.find(Long.parseLong(param));
        userLogado = userServico.getCurrentUser();
        verificarUrl();
    }

    @Override
    public void instanciar() {
        userLogado = userServico.getCurrentUser();
        instanciarAnexo();
        instanciarCabecalhoRodape();
        instanciarListCabecalhoRodape();
        verificarUrl();

    }

    public void salvarCabecalho() {
        if (Utils.isNotEmpty(this.cabecalhoRodape.getImagem())) {
            if (!Utils.isNotEmpty(this.cabecalhoRodape.getImagem().getId())) {
                anexoServico.salvar(this.cabecalhoRodape.getImagem());
            }
        }
        if (telaCabecalho) {
            this.cabecalhoRodape.setTipo(TipoCabecalhoRodape.CABECALHO);
            this.cabecalhoRodapeServico.salvar(this.cabecalhoRodape);
            Msg.messagemInfoRedirect("Operação realizada com sucesso !", "cabecalho.xhtml?visualizar=" + this.cabecalhoRodape.getId());

        } else {
            this.cabecalhoRodape.setTipo(TipoCabecalhoRodape.RODAPE);
            this.cabecalhoRodapeServico.salvar(this.cabecalhoRodape);
            Msg.messagemInfoRedirect("Operação realizada com sucesso !", "rodape.xhtml?visualizar=" + this.cabecalhoRodape.getId());

        }
    }

    public void selecionarLogo(FileUploadEvent event) {
        try {
            Anexo logo = anexoServico.adicionarAnexo(event.getFile());
            logo.setArquivo(event.getFile());
            this.cabecalhoRodape.setImagem(logo);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(managerRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verificarUrl() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String url = request.getRequestURI();
        String sentenca = url;
        int index = sentenca.lastIndexOf("/");
        String restoSentenca = (sentenca.substring(0, index));
        String ultimaPalavra = (sentenca.substring(index + 1));
        telaCabecalho = ultimaPalavra.equals("cabecalho.xhtml");
    }

    public boolean verificarUrlPesquisa() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String url = request.getRequestURI();
        String sentenca = url;
        int index = sentenca.lastIndexOf("/");
        String restoSentenca = (sentenca.substring(0, index));
        String ultimaPalavra = (sentenca.substring(index + 1));
        return ultimaPalavra.equals("pesquisarCabecalho.xhtml");
    }

    public void selecionarAnexo(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.CONTRATO);
        adicionarAnexo();
    }

    public void adicionarAnexo() {

        try {
            this.cabecalhoRodape.setImagem(anexoServico.adicionarAnexo(this.anexo.getArquivo()));
//            this.contrato.getAnexos().get(posicao - 1).setTipo(anexo.getTipo());
            this.cabecalhoRodape.getImagem().setArquivo(anexo.getArquivo());
            this.cabecalhoRodape.getImagem().setCaminho(TipoAnexo.CABECALHO_RODAPE);
            this.cabecalhoRodape.getImagem().setTipoAnexo(this.anexo.getTipoAnexo());
        } catch (SQLException | IOException ex) {
            Logger.getLogger(managerContrato.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        instanciarAnexo();
        Msg.messagemInfo(Msg.SuccessFull);

    }

    public boolean isPDF(Anexo anexo) {
        if (Utils.isNotEmpty(anexo)) {
            return anexo.getNome().contains(".pdf");
        } else {
            return false;
        }
    }

    public String anexoUrl(Anexo anexo) throws IOException {
        String caminhoLogo = "";
        String conteudo_base64 = "";
        if (Utils.isNotEmpty(anexo)) {
            caminhoLogo = anexo.getUrl() + "/" + anexo.getNome();
            if (anexo.getNome().contains(".pdf")) {
                conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File("/opt/Licitacao/logoPDF.png")));
            } else {
                conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File(caminhoLogo)));
            }
            return "data:image/png;base64," + conteudo_base64;
        } else {
            return "";
        }
    }

    public void instanciarAnexo() {
        this.anexo = new Anexo();
    }

    public void deletarAnexo(Anexo a) {
        this.cabecalhoRodape.setImagem(null);
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

    public void limparPesquisa() {
        this.listcabecalhoRodape = new ArrayList<>();
        this.cabecalhoRodape = new CabecalhoRodape();

    }

    public void deletar() {
        this.listcabecalhoRodape.remove(this.cabecalhoRodape);
        this.cabecalhoRodape.setAtivo(false);
        cabecalhoRodapeServico.Update(this.cabecalhoRodape);
        Msg.messagemInfo("Operação realizada com sucesso !");
        instanciarCabecalhoRodape();
    }

    public void removerLogo(Anexo avatar) {
        this.cabecalhoRodape.setImagem(null);
    }

    public void instanciarCabecalhoRodape() {
        this.cabecalhoRodape = new CabecalhoRodape();
        if (Utils.isNotEmpty(userLogado)) {
            if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
                this.cabecalhoRodape.setUnidadeOrganizacional(userLogado.getUnidadeOrganizacional());
            }
        }
        this.cabecalhoRodape.setAtivo(true);
    }

    public void instanciarListCabecalhoRodape() {
        this.listcabecalhoRodape = new ArrayList<>();
    }

    public void pesquisar() {
        if (verificarUrlPesquisa()) {
            this.cabecalhoRodape.setTipo(TipoCabecalhoRodape.CABECALHO);

            this.listcabecalhoRodape = cabecalhoRodapeServico.pesquisar(cabecalhoRodape);
        } else {
            this.cabecalhoRodape.setTipo(TipoCabecalhoRodape.RODAPE);
            this.listcabecalhoRodape = cabecalhoRodapeServico.pesquisar(cabecalhoRodape);
        }
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarCabecalho.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "cabecalho.xhtml?visualizar=" + this.cabecalhoRodape.getId();

    }

    public String getTituloAtual() {
        if (telaCabecalho) {
            return getTitulo() + " Cabeçalho";
        } else {
            return getTitulo() + " Rodapé";

        }
    }

    public CabecalhoRodape getCabecalhoRodape() {
        return cabecalhoRodape;
    }

    public void setCabecalhoRodape(CabecalhoRodape cabecalhoRodape) {
        this.cabecalhoRodape = cabecalhoRodape;
    }

    public List<CabecalhoRodape> getListcabecalhoRodape() {
        return listcabecalhoRodape;
    }

    public void setListcabecalhoRodape(List<CabecalhoRodape> listcabecalhoRodape) {
        this.listcabecalhoRodape = listcabecalhoRodape;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

}
