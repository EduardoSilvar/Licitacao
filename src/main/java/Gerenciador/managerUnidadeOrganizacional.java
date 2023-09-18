/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.TipoCabecalhoRodape;
import Servico.AnexoServico;
import Servico.CabecalhoRodapeServico;
import Servico.ConfiguracaoServico;
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
import modelo.Configuracao;
import modelo.Endereco;
import modelo.TipoAnexo;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import util.Base64j;
import util.Caracteres;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerUnidadeOrganizacional extends managerPrincipal implements Serializable {

    @EJB
    UnidadeOrganizacionalServico unidadeServico;
    @EJB
    private CabecalhoRodapeServico cabecalhoRodapeServico;
    @EJB
    private AnexoServico anexoServico;
    @EJB
    private UsuarioServico userServico;
    @EJB
    private ConfiguracaoServico configService;

    private CabecalhoRodape cabecalho;

    private CabecalhoRodape rodape;

    private Usuario userLogado;
    private UnidadeOrganizacional unidade;
    private List<UnidadeOrganizacional> unidades;
    private Anexo anexo;
    private Anexo timbrado;
    private Anexo timbradoAnexo;

    private Long id;

    public void salvar() {

        if (Utils.validarCNPJ(Caracteres.removecaracter(this.unidade.getCnpj()))) {
            unidadeServico.Save(this.unidade);
            if (Utils.isNotEmpty(cabecalho)) {
                salvarCabecalho();
                salvarRodape();
                if (Utils.isNotEmpty(unidade.getConfiguracao())) {
                    Configuracao config = unidade.getConfiguracao();
                    config.setCabecalhoNotaFiscal(cabecalho);
                    config.setRodapeNotaFiscal(rodape);
                    config.setTimbrado(timbrado);
                    configService.Update(config);
                } else {
                    Configuracao config = new Configuracao();
                    config.setCabecalhoNotaFiscal(cabecalho);
                    config.setRodapeNotaFiscal(rodape);
                    config.setTimbrado(timbrado);
                    configService.Save(config);
                    unidade.setConfiguracao(config);
                    unidadeServico.Update(unidade);
                }
            }
            Msg.messagemInfoRedirect("Operação realizada com sucesso !", "orgao.xhtml?visualizar=" + this.unidade.getId());
        } else {
            Msg.messagemError("CNPJ invalido !");
        }
    }

    public void deletar() {
        UnidadeOrganizacional Novaunidade = unidadeServico.find(this.unidade.getId());
        Novaunidade.setAtivo(false);
        unidadeServico.Update(Novaunidade);
        if (Utils.isNotEmpty(id)) {
            Msg.messagemInfoRedirect("Operação realizada com sucesso !", "orgao.xhtml");
        } else {
            this.unidades.remove(Novaunidade);
            this.unidades = unidadeServico.pesquisar(this.unidade);
            Msg.messagemInfo("Operação realizada com sucesso !");
        }
    }

    public void verificarCnpj() {

        if (!Utils.validarCNPJ(Caracteres.removecaracter(this.unidade.getCnpj()))) {
            Msg.messagemError("CNPJ invalido !");
        }
    }

    public void atualizar() {
        unidadeServico.Update(this.unidade);
        if (Utils.isNotEmpty(cabecalho)) {
            salvarCabecalho();
            salvarRodape();
            if (Utils.isNotEmpty(unidade.getConfiguracao())) {
                Configuracao config = unidade.getConfiguracao();
                config.setCabecalhoNotaFiscal(cabecalho);
                config.setRodapeNotaFiscal(rodape);
                config.setTimbrado(timbrado);
                if (Utils.isEmpty(timbrado.getId())) {
                    anexoServico.salvar(timbrado);
                }
                configService.Update(config);
            } else {

                Configuracao config = new Configuracao();
                config.setCabecalhoNotaFiscal(cabecalho);
                config.setRodapeNotaFiscal(rodape);
                config.setTimbrado(timbrado);
                if (Utils.isEmpty(timbrado.getId())) {
                    anexoServico.salvar(timbrado);
                }
                configService.Save(config);
                unidade.setConfiguracao(config);
                unidadeServico.Update(unidade);
            }
        } 
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "orgao.xhtml?visualizar=" + this.unidade.getId());
    }

    public void pesquisar() {
        this.unidades = unidadeServico.pesquisar(this.unidade);
        if (this.unidades.size() > 0) {
            Msg.messagemInfo("Operação realizada com sucesso !!");
        } else {
            Msg.messagemError("Nenhuma entidade encontrado");
        }
    }

    @Override
    public void carregar(String param) {
        this.id = Long.parseLong(param);
        this.unidade = unidadeServico.find(Long.parseLong(param));
        userLogado = userServico.getCurrentUser();
        instanciarAnexo();
        if (Utils.isNotEmpty(this.unidade.getConfiguracao())) {
            if (!Utils.isNotEmpty(this.unidade.getConfiguracao().getCabecalhoNotaFiscal())) {
                instanciarCabecalho();
            } else {
                cabecalho = this.unidade.getConfiguracao().getCabecalhoNotaFiscal();
            }
            if (!Utils.isNotEmpty(this.unidade.getConfiguracao().getRodapeNotaFiscal())) {
                instanciarRodape();
            } else {
                rodape = this.unidade.getConfiguracao().getRodapeNotaFiscal();

            }
            if (!Utils.isNotEmpty(this.unidade.getConfiguracao().getTimbrado())) {
                this.timbrado = new Anexo();
                this.timbradoAnexo = new Anexo();

            } else {
                this.timbrado = this.unidade.getConfiguracao().getTimbrado();
                this.timbradoAnexo = new Anexo();

            }
        } else {
            instanciarCabecalho();
            instanciarRodape();
            this.timbrado = new Anexo();
            this.timbradoAnexo = new Anexo();

        }

    }

    @Override
    public void instanciar() {
        instanciarUnidade();
        this.unidades = new ArrayList<>();
        userLogado = userServico.getCurrentUser();
        instanciarAnexo();
        instanciarCabecalho();
        instanciarRodape();
        this.timbrado = new Anexo();

    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarOrgao.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "orgao.xhtml?visualizar=" + this.unidade.getId();
    }

    public void instanciarCabecalho() {
        this.cabecalho = new CabecalhoRodape();
        if (Utils.isNotEmpty(userLogado)) {
            if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
                this.cabecalho.setUnidadeOrganizacional(userLogado.getUnidadeOrganizacional());
            }
        }
        this.cabecalho.setAtivo(true);
    }

    public void instanciarRodape() {
        this.rodape = new CabecalhoRodape();
        if (Utils.isNotEmpty(userLogado)) {
            if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
                this.rodape.setUnidadeOrganizacional(userLogado.getUnidadeOrganizacional());
            }
        }
        this.rodape.setAtivo(true);
    }

    public List<Anexo> getTimbrados() {
        List<Anexo> anexos = new ArrayList<>();
        if (Utils.isNotEmpty(timbrado)) {
            if (Utils.isNotEmpty(timbrado.getCaminho())) {
                anexos.add(timbrado);
            }
        }
        return anexos;
    }

    public void selecionarLogoCabecalho(FileUploadEvent event) {
        try {
            Anexo logo = anexoServico.adicionarAnexo(event.getFile(), TipoAnexo.CABECALHO_RODAPE, userLogado.getUnidadeOrganizacional().getId());
            logo.setArquivo(event.getFile());
            this.cabecalho.setImagem(logo);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(managerRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selecionarLogoRodape(FileUploadEvent event) {
        try {
            Anexo logo = anexoServico.adicionarAnexo(event.getFile(), TipoAnexo.CABECALHO_RODAPE, userLogado.getUnidadeOrganizacional().getId());
            logo.setArquivo(event.getFile());
            this.rodape.setImagem(logo);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(managerRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selecionarAnexoCabecalho(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.CABECALHO_RODAPE);
        adicionarAnexoCabecalho();
    }

    public void selecionarAnexoTimbrado(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.timbradoAnexo.setArquivo(event.getFile());
            }
        }
        this.timbradoAnexo.setTipoAnexo(TipoAnexo.CABECALHO_RODAPE);
        adicionarAnexoTimbrado();
    }

    public void selecionarAnexoRodape(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.CABECALHO_RODAPE);
        adicionarAnexoRodape();
    }

    public void salvarCabecalho() {
        if (Utils.isNotEmpty(this.cabecalho.getImagem())) {
            if (!Utils.isNotEmpty(this.cabecalho.getImagem().getId())) {
                anexoServico.salvar(this.cabecalho.getImagem());
            }
        }
        this.cabecalho.setTipo(TipoCabecalhoRodape.CABECALHO);
        this.cabecalhoRodapeServico.salvar(this.cabecalho);
    }

    public void salvarRodape() {
        if (Utils.isNotEmpty(this.rodape.getImagem())) {
            if (!Utils.isNotEmpty(this.rodape.getImagem().getId())) {
                anexoServico.salvar(this.rodape.getImagem());
            }
        }
        this.rodape.setTipo(TipoCabecalhoRodape.CABECALHO);
        this.cabecalhoRodapeServico.salvar(this.rodape);
    }

    public void adicionarAnexoCabecalho() {

        try {
            if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
                this.cabecalho.setImagem(anexoServico.adicionarAnexo(this.anexo.getArquivo(), TipoAnexo.CABECALHO_RODAPE, userLogado.getUnidadeOrganizacional().getId()));
            } else {
                this.cabecalho.setImagem(anexoServico.adicionarAnexo(this.anexo.getArquivo(), TipoAnexo.CABECALHO_RODAPE, 000l));
            }
//            this.contrato.getAnexos().get(posicao - 1).setTipo(anexo.getTipo());
            this.cabecalho.getImagem().setArquivo(anexo.getArquivo());
            this.cabecalho.getImagem().setCaminho(TipoAnexo.CABECALHO_RODAPE);
            this.cabecalho.getImagem().setTipoAnexo(this.anexo.getTipoAnexo());
        } catch (SQLException | IOException ex) {
            Logger.getLogger(managerContrato.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        instanciarAnexo();
        Msg.messagemInfo(Msg.SuccessFull);

    }

    public void adicionarAnexoTimbrado() {

        try {
            if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
                this.timbrado = anexoServico.adicionarAnexo(this.timbradoAnexo.getArquivo(), TipoAnexo.CABECALHO_RODAPE, userLogado.getUnidadeOrganizacional().getId());
            } else {
                this.timbrado = anexoServico.adicionarAnexo(this.timbradoAnexo.getArquivo(), TipoAnexo.CABECALHO_RODAPE, 000l);
            }
//            this.contrato.getAnexos().get(posicao - 1).setTipo(anexo.getTipo());
            this.timbrado.setArquivo(timbradoAnexo.getArquivo());
            this.timbrado.setCaminho(TipoAnexo.CABECALHO_RODAPE);
            this.timbrado.setTipoAnexo(this.timbradoAnexo.getTipoAnexo());
        } catch (SQLException | IOException ex) {
            Logger.getLogger(managerContrato.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        instanciarAnexo();
        Msg.messagemInfo(Msg.SuccessFull);

    }

    public void adicionarAnexoRodape() {

        try {
            if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
                this.rodape.setImagem(anexoServico.adicionarAnexo(this.anexo.getArquivo(), TipoAnexo.CABECALHO_RODAPE, userLogado.getUnidadeOrganizacional().getId()));
            } else {
                this.rodape.setImagem(anexoServico.adicionarAnexo(this.anexo.getArquivo(), TipoAnexo.CABECALHO_RODAPE, 000l));
            }
//            this.contrato.getAnexos().get(posicao - 1).setTipo(anexo.getTipo());
            this.rodape.getImagem().setArquivo(anexo.getArquivo());
            this.rodape.getImagem().setCaminho(TipoAnexo.CABECALHO_RODAPE);
            this.rodape.getImagem().setTipoAnexo(this.anexo.getTipoAnexo());
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

    public void deletarAnexoCabecalho(Anexo a) {
        this.cabecalho.setImagem(null);
        Msg.messagemInfo("Anexo removido com sucesso !");
    }

    public void deletarAnexoTimbrado(Anexo a) {
        this.timbrado = null;
        Msg.messagemInfo("Anexo removido com sucesso !");
    }

    public void deletarAnexoRodape(Anexo a) {
        this.rodape.setImagem(null);
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

    public void instanciarUnidade() {
        this.unidade = new UnidadeOrganizacional();
        this.unidade.setEndereco(new Endereco());
    }

    public UnidadeOrganizacional getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeOrganizacional unidade) {
        this.unidade = unidade;
    }

    public List<UnidadeOrganizacional> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadeOrganizacional> unidades) {
        this.unidades = unidades;
    }

    public CabecalhoRodape getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(CabecalhoRodape cabecalho) {
        this.cabecalho = cabecalho;
    }

    public CabecalhoRodape getRodape() {
        return rodape;
    }

    public void setRodape(CabecalhoRodape rodape) {
        this.rodape = rodape;
    }

    public Usuario getUserLogado() {
        return userLogado;
    }

    public void setUserLogado(Usuario userLogado) {
        this.userLogado = userLogado;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

}
