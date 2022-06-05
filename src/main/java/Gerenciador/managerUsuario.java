/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.UsuarioServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Usuario;
import modelo.Usuario_;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ViewScoped
@ManagedBean
public class managerUsuario extends managerPrincipal implements Serializable {
    
    @EJB
    private UsuarioServico userServico;
    
    private Usuario user;
    private List<Usuario> users;
    private String repetirSenha;
    
    @Override
    public void carregar(String param) {
        this.user = userServico.find(Long.parseLong(param));
    }
    
    @Override
    public void instanciar() {
        instanciarUsuario();
        instanciarListUser();
    }
    
    public void salvar() {
        if (Utils.isNotEmpty(user.getSenha())) {
            if (Utils.isNotEmpty(this.repetirSenha)) {
                if (user.getSenha().equals(repetirSenha)) {
                    userServico.Save(this.user);
                    Msg.messagemInfoRedirect("Operação realizada com sucesso !", "usuario.xhtml?visualizar=" + this.user.getId());
                } else {
                    Msg.messagemError("As senhas não conferem !");
                }
            } else {
                Msg.messagemError("Você precisa precisa repetir a senha !");
            }
        } else {
            Msg.messagemError("Você precisa inserir as senhas !");
        }
    }
    
    public void atualizar() {
        userServico.Update(this.user);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "usuario.xhtml?visualizar=" + this.user.getId());
    }
    
    public void deletar() {
        Usuario novoUser = userServico.find(this.user.getId());
        novoUser.setAtivo(false);
        userServico.Update(novoUser);
        pesquisar();
        Msg.messagemInfo("Operação realizada com sucesso !");
    }
    
    public void pesquisar() {
        this.users = userServico.pesquisar(this.user);
        if (users.size() > 0) {
            Msg.messagemInfo("Operação realizada com sucesso !");
        }else{
        Msg.messagemError("Não existe registros");
        }
    }
    
    @Override
    public String getUrlPesquisar() {
        return "pesquisarUsuario.xhtml";
    }
    
    @Override
    public String getUrlVisualizar() {
        return "usuario.xhtml?visualizar=" + this.user.getId();
    }
    
    public void instanciarUsuario() {
        this.user = new Usuario();
    }
    
    public void instanciarListUser() {
        this.users = new ArrayList<>();
    }
    
    public UsuarioServico getUserServico() {
        return userServico;
    }
    
    public void setUserServico(UsuarioServico userServico) {
        this.userServico = userServico;
    }
    
    public Usuario getUser() {
        return user;
    }
    
    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public List<Usuario> getUsers() {
        return users;
    }
    
    public void setUsers(List<Usuario> users) {
        this.users = users;
    }
    
    public String getRepetirSenha() {
        return repetirSenha;
    }
    
    public void setRepetirSenha(String repetirSenha) {
        this.repetirSenha = repetirSenha;
    }
    
}
