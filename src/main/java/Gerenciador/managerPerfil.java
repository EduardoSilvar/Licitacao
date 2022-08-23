/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.UsuarioServico;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ViewScoped
@ManagedBean
public class managerPerfil implements Serializable {

    @EJB
    UsuarioServico usuarioServico;

    private Usuario usuario;

    private String senhaAtual;

    private String novaSenha;

    private String repetirSenha;

    private boolean alteracaoSenha = false;

    @PostConstruct
    public void init() {
        carregarUsuarioLogado();
    }

    public void carregarUsuarioLogado() {
        this.usuario = usuarioServico.getCurrentUser();
        if (Utils.isEmpty(this.usuario)) {
            this.usuario = new Usuario();
        }
    }

    public void alterarSenha() {
        this.alteracaoSenha = !this.alteracaoSenha;
        System.err.println(this.alteracaoSenha);
    }

    public void salvar() {
        try {
            usuarioServico.alterarSenha(this.usuario, this.senhaAtual, this.novaSenha, this.repetirSenha);
            Msg.messagemInfoRedirect(Msg.SuccessFull, "perfil.xhtml");
        } catch (Exception ex) {
            Msg.messagemError(ex.getMessage());
        }
    }

    public void logout() throws IOException, NullPointerException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        fc.getExternalContext().redirect("login.xhtml");
    }

    public String getUrlAposCancelar() {
        return "index.xhtml";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public boolean isAlteracaoSenha() {
        return alteracaoSenha;
    }

    public String getRepetirSenha() {
        return repetirSenha;
    }

    public void setRepetirSenha(String repetirSenha) {
        this.repetirSenha = repetirSenha;
    }

}
