/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.ChatServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import modelo.Usuario;

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
        return chatServico.totalChatNaoLido(usuarioLogado);
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
