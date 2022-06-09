/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.UsuarioServico;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerLogin implements Serializable {

    @EJB
    private UsuarioServico userServico;
    private Usuario usuarioLogado;
    private String user;
    private String senha;

    @PostConstruct
    public void init() {
        this.usuarioLogado = new Usuario();
//        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
//        usuarioLogado = userServico.getCurrentUser();
//        if (userPrincipal != null) {
//            this.user = userPrincipal.getName();
//            String[] nome = this.user.trim().split(" ");
//            if (nome.length > 1) {
//                this.user = nome[0];
//            }
//        }
    }

    public boolean logado() {
        boolean uL = false;
        uL = !Utils.isEmpty(usuarioLogado);
        return uL;
    }

    public void logout() throws IOException, NullPointerException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        fc.getExternalContext().redirect("index.xhtml");
    }

    public void verificarLogin() {
        this.usuarioLogado.setSenha(Usuario.encryptPassword(senha));
        if (Utils.isNotEmpty(usuarioLogado)) {
            if (userServico.login(usuarioLogado)) {
                this.usuarioLogado = userServico.find(usuarioLogado);
                Msg.messagemInfoRedirect("Bem vindo ", "index.xhtml");
            } else {
                Msg.messagemError("Informações erradas, tente novamente !");
            }
        }
    }

    public UsuarioServico getUserServico() {
        return userServico;
    }

    public void setUserServico(UsuarioServico userServico) {
        this.userServico = userServico;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
