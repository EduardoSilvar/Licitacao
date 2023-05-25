/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.AnexoServico;
import Servico.RelatorioServico;
import java.io.File;
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
import modelo.Anexo;
import modelo.Relatorio;
import modelo.TipoAnexo;
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
public class managerRelatorio extends managerPrincipal implements Serializable {

    @EJB
    RelatorioServico relatorioServico;

    @EJB
    AnexoServico anexoServico;

    private Relatorio relatorio;

    private List<Relatorio> relatoriosPesquisados;

    private Anexo anexo;

    @Override
    public void instanciar() {
        instanciarRelatorio();
        instanciarRelatoriosPesquisados();
        instanciarAnexo();

    }

    public void instanciarRelatoriosPesquisados() {
        relatoriosPesquisados = new ArrayList<>();
    }

    public void instanciarRelatorio() {
        this.relatorio = new Relatorio();
    }

    @Override
    public void carregar(String param) {
        relatorio = relatorioServico.find(Long.parseLong(param));
        instanciarAnexo();

    }

    public void salvarRelatorioCabecalho() throws SQLException, IOException {
        if (Utils.isNotEmpty(relatorio)) {
            if (Utils.isNotEmpty(relatorio.getLogo())) {
                if (Utils.isNotEmpty(relatorio.getLogo().getArquivo())) {
                    Anexo logo = anexoServico.adicionarArquivoPermanente(relatorio.getLogo().getArquivo(), TipoAnexo.CABECALHO_RODAPE.getUrl());
                    relatorio.setLogo(logo);
                }
            }
        }
        relatorioServico.salvar(relatorio);
        Msg.messagemInfoRedirect(Msg.SuccessFull, "cabecalho.xhtml?visualizar=" + relatorio.getId());
    }

    public void salvarRelatorioRodape() throws SQLException, IOException {
        if (Utils.isNotEmpty(relatorio)) {
            if (Utils.isNotEmpty(relatorio.getLogo())) {
                if (Utils.isNotEmpty(relatorio.getLogo().getArquivo())) {
                    Anexo logo = anexoServico.adicionarArquivoPermanente(relatorio.getLogo().getArquivo(), TipoAnexo.CABECALHO_RODAPE.getUrl());
                    relatorio.setLogo(logo);
                }
            }
        }
        relatorioServico.salvar(relatorio);
        Msg.messagemInfoRedirect(Msg.SuccessFull, "rodape.xhtml?visualizar=" + relatorio.getId());
    }

    public void pesquisarRodape() {
        try {
            relatoriosPesquisados = relatorioServico.find(this.relatorio);
        } catch (Exception ex) {
            instanciarRelatoriosPesquisados();
            Msg.messagemError(ex.getMessage());
        }
    }

    public void pesquisarCabecelho() {
        try {
            relatoriosPesquisados = relatorioServico.find(this.relatorio);
        } catch (Exception ex) {
            instanciarRelatoriosPesquisados();
            Msg.messagemError(ex.getMessage());
        }
    }

    public void limparPesquisa() {
        instanciarRelatorio();
        instanciarRelatoriosPesquisados();
    }

    public void remover() {
        relatorioServico.remover(this.relatorio);
        Msg.messagemInfoRedirect(Msg.SuccessFull, "pesquisarRelatorio.xhtml");
    }

//    public void selecionarLogo(FileUploadEvent event) {
//        try {
//            Anexo logo = anexoServico.adicionarAnexo(event.getFile(),);
//            logo.setArquivo(event.getFile());
//            this.relatorio.setLogo(logo);
//        } catch (IOException | SQLException ex) {
//            Logger.getLogger(managerRelatorio.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

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

//    public void selecionarAnexo(FileUploadEvent event) {
//        if (Utils.isNotEmpty(event)) {
//            if (Utils.isNotEmpty(event.getFile())) {
//                this.anexo.setArquivo(event.getFile());
//            }
//        }
//        this.anexo.setTipoAnexo(TipoAnexo.CONTRATO);
//        adicionarAnexo();
//    }

//    public void adicionarAnexo() {
//
//        try {
//            this.relatorio.setLogo(anexoServico.adicionarAnexo(this.anexo.getArquivo()));
////            this.contrato.getAnexos().get(posicao - 1).setTipo(anexo.getTipo());
//            this.relatorio.getLogo().setArquivo(anexo.getArquivo());
//            this.relatorio.getLogo().setCaminho(TipoAnexo.CABECALHO_RODAPE);
//            this.relatorio.getLogo().setTipoAnexo(this.anexo.getTipoAnexo());
//        } catch (SQLException | IOException ex) {
//            Logger.getLogger(managerContrato.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        instanciarAnexo();
//        Msg.messagemInfo(Msg.SuccessFull);
//
//    }

    public void instanciarAnexo() {
        this.anexo = new Anexo();
    }

    public void deletarAnexo(Anexo a) {
        this.relatorio.getAnexos().remove(a);
        Msg.messagemInfo("Anexo removido com sucesso !");
    }

    public List<Anexo> getLogo() {
        List<Anexo> avatar = new ArrayList<>();
        avatar.add(this.relatorio.getLogo());
        return avatar;
    }

    public void removerLogo(Anexo avatar) {
        this.relatorio.setLogo(null);
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarRelatorio.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "relatorio.xhtml?visualizar=" + relatorio.getId();
    }

    @Override
    public String getTitulo() {
        return super.getTitulo();
    }

    public String cabecalho() {

        return getTitulo() + " Cabeçalho";
    }

    public String rodape() {

        return getTitulo() + " Rodapé";
    }

    public String getTituloRelatoriosDataTable() {
        return (this.relatoriosPesquisados != null && !this.relatoriosPesquisados.isEmpty())
                ? "Relatórios (" + this.relatoriosPesquisados.size() + ")"
                : "Relatórios";
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    public List<Relatorio> getRelatoriosPesquisados() {
        return relatoriosPesquisados;
    }

    public void setRelatoriosPesquisados(List<Relatorio> relatoriosPesquisados) {
        this.relatoriosPesquisados = relatoriosPesquisados;
    }

}
