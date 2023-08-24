/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.ChatServico;
import Servico.UsuarioServico;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import modelo.Usuario;
import org.primefaces.PrimeFaces;
import util.Base64j;
import util.DateUtils;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerLogin implements Serializable {

    @EJB
    UsuarioServico usuarioServico;
    @EJB
    ChatServico chatServico;
    private Usuario usuarioLogado;
    private String user;

//    private ConfiguracaoSistema configuracaoSistema;
    @PostConstruct
    public void init() {
        usuarioLogado = new Usuario();
//        configuracaoSistema = configuracaoSistemaServico.obterConfiguracaoSistema();
//        this.credenciada = credenciadaServico.findCredenciadaAssinante();
        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        usuarioLogado = usuarioServico.getCurrentUser();
        if (userPrincipal != null) {
            //this.user = usuarioLogado.getNome();
            this.user = userPrincipal.getName();
            String[] nome = this.user.trim().split(" ");

            if (nome.length > 1) {
                this.user = nome[0];
            }
            iniciarPagina();
        }
    }

    public int totalChats() {
        if (Utils.isNotEmpty(usuarioLogado)) {
            return chatServico.totalChatNaoLido(usuarioLogado);
        }
        return 0;
    }

    public void testeBackup() throws IOException {
        String[] command = {
            "bash",
            "-c",
            "export PGPASSWORD=@@licitacao@@#7@2023 && pg_dump -h localhost -p 5432 -U postgres -F c -b -v -f /opt/backup/licitacao.backup licitacao"
        };
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Backup do banco de dados concluído com sucesso.");
            } else {
                System.err.println("Falha ao fazer backup do banco de dados.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        compactarDossie("/opt/backup", "/opt/backup.zip");
        downloadBackup("/opt/backup.zip");

    }

    public void downloadBackup(String caminho) throws IOException {
        File originalFile = new File("/opt/backup.zip");
        String encodedBase64 = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
            byte[] bytes = new byte[(int) originalFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64j.encodeBytes(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return encodedBase64;

        PrimeFaces.current().executeScript(
                String.format("saveFile('%s', '%s', '%s')", encodedBase64, "application/xzip", "backup" + DateUtils.format(DateUtils.YYYY_MM_DD, new Date()) + ".zip")
        );
        Path diretoriozip = Paths.get("/opt/backup.zip");
        File file = new File("/opt/backup");

        Files.deleteIfExists(diretoriozip);
    }

    public void compactarDossie(String pastaParaCompactar, String arquivoCompactado) throws IOException {

        try {
            // Cria o comando para compactar a pasta usando o comando "zip"
            ProcessBuilder processBuilder = new ProcessBuilder("zip", "-r", arquivoCompactado, pastaParaCompactar);

            // Executa o comando
            Process process = processBuilder.start();

            // Aguarda o término do comando
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Pasta compactada com sucesso!");
            } else {
                System.out.println("Falha ao compactar a pasta.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void iniciarPagina() {

        HttpServletRequest uri = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        if (this.configuracaoSistema.getTipoPlano() == null && !uri.getRequestURI().contains("configuracao.xhtml")) {
//            try {
////                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
////                context.redirect("configuracao.xhtml");
//            } catch (IOException ex) {
//                Logger.getLogger(ManagerIndex.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

//        if (isRequerente(usuarioLogado)) {
//            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.getExternalContext().getFlash().setKeepMessages(true);
//            try {
//                context.redirect("inicio.xhtml");
//            } catch (IOException ex) {
//                Logger.getLogger("").log(Level.SEVERE, null, ex);
//            }
//        }
    }

//    public boolean isRequerente(Usuario usuario) {
//        if (usuario != null) {
//            for (Grupo grupo : usuario.getGrupos()) {
//                if (grupo.getNome().toLowerCase().contains("requerente")) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

//    public String getNomeCredenciada() {
//        String nomeCredenciada = "";
//        if (Utils.isNotEmpty(this.credenciada) && Utils.isNotEmpty(this.credenciada.getNomeProprietario())) {
//            nomeCredenciada = this.credenciada.getNomeProprietario();
//        }
//        return nomeCredenciada;
//    }
}
