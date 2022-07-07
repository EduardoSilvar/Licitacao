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
    private Usuario userll;
    private String user;
    private String senha;
    private String novaSenha;
    private String repetirSenha;

    @PostConstruct
    public void init() {
        this.usuarioLogado = new Usuario();
        try {
            instanciarLogado();
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void instanciarLogado() throws IOException {
        if (Utils.isNotEmpty(getObjectSession("usuarioLogado"))) {
            this.userll = (Usuario) getObjectSession("usuarioLogado");
        }
    }

    public static void VerificarLogin() throws IOException {
        if (Utils.isEmpty(getObjectSession("usuarioLogado"))) {
            redirectLogin();
        }
    }

    public static void redirectLogin() {
        HttpServletRequest uri = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (!uri.getRequestURI().contains("login.xhtml")) {
            try {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(managerLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean logado() {
        boolean uL = false;
        uL = !Utils.isEmpty(usuarioLogado);
        return uL;
    }

    public static void logout() throws IOException, NullPointerException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        fc.getExternalContext().redirect("index.xhtml");
    }

    public static void logout(String user) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute(user);

    }

    public static void setObjectSession(String user, Object object) throws IOException, NullPointerException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(user, object);
    }

    public static Object getObjectSession(String user) throws IOException, NullPointerException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return session.getAttribute(user);
    }

    public void verificarLogin() throws IOException {
        this.usuarioLogado.setSenha(Usuario.encryptPassword(senha));
        if (Utils.isNotEmpty(usuarioLogado)) {
            if (userServico.login(usuarioLogado)) {
                this.usuarioLogado = userServico.find(usuarioLogado);
                setObjectSession("usuarioLogado", this.usuarioLogado);
                Msg.messagemInfoRedirect("Bem vindo ", "index.xhtml");
            } else {
                Msg.messagemError("Informações erradas, tente novamente !");
            }
        }
    }

    public Usuario retornanome() throws IOException {
        return (Usuario) getObjectSession("usuarioLogado");
    }

    public Usuario getUserll() {
        return userll;
    }

    public void setUserll(Usuario userll) {
        this.userll = userll;
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

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getRepetirSenha() {
        return repetirSenha;
    }

    public void setRepetirSenha(String repetirSenha) {
        this.repetirSenha = repetirSenha;
    }

}
