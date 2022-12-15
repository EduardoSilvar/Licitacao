/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.GrupoServico;
import Servico.SetorServico;
import Servico.UnidadeOrganizacionalServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Grupo;
import modelo.Setor;
import modelo.Usuario;
import modelo.UnidadeOrganizacional;
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
    @EJB
    private UnidadeOrganizacionalServico unidadeServico;
    @EJB
    private SetorServico setorServico;
    @EJB
    private GrupoServico grupoServico;

    private Usuario user;
    private Usuario userlogado;
    private Grupo grupo;
    private List<Usuario> users;
    private String repetirSenha;
    private List<Setor> setores;
    private List<UnidadeOrganizacional> unidades;

    @Override
    public void carregar(String param) {
        userlogado = userServico.getCurrentUser();
        this.grupo = new Grupo();
        if (Utils.isNotEmpty(userlogado.getUnidadeOrganizacional())) {
            this.unidades = unidadeServico.FindUnidades(userlogado.getUnidadeOrganizacional());
        } else {
            this.unidades = unidadeServico.FindAll();

        }

        this.user = userServico.find(Long.parseLong(param));
    }

    @Override
    public void instanciar() {
        userlogado = userServico.getCurrentUser();
        if (Utils.isNotEmpty(userlogado.getUnidadeOrganizacional())) {
            this.unidades = unidadeServico.FindUnidades(userlogado.getUnidadeOrganizacional());
        } else {
            this.unidades = unidadeServico.FindAll();

        }
        this.grupo = new Grupo();
        instanciarUsuario();
        instanciarListUser();
    }

    public List<Setor> getSetores() {
        if (Utils.isNotEmpty(userlogado.getUnidadeOrganizacional())) {
            return setorServico.FindAll(userlogado.getUnidadeOrganizacional());
        } else {
            return setorServico.FindAll();
        }
    }

    public void salvar() {
        if (Utils.isNotEmpty(grupo)) {
            user.getGrupos().add(grupo);
        }
        if (userServico.existEmail(user)) {
            Msg.messagemError("O e-mail ja foi registrado !");
        } else {
            if (userServico.existLogin(user)) {
                Msg.messagemError("O Login ja esta sendo usado !");
            } else {
                if (userServico.existCpf(user)) {
                    Msg.messagemError("CPF ja registrado !!");
                } else {
                    if (userServico.valida(user.getCpf())) {
                        if (Utils.isNotEmpty(this.repetirSenha)) {
                            if (user.getSenha().equals(repetirSenha)) {
                                if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                                    this.user.setSenha(Usuario.encryptPassword(repetirSenha));
                                    userServico.Save(this.user);
                                    Msg.messagemInfoRedirect("Operação realizada com sucesso !", "usuario.xhtml?visualizar=" + this.user.getId());
                                } else {
                                    this.user.setUnidadeOrganizacional(userlogado.getUnidadeOrganizacional());
                                    this.user.setSenha(Usuario.encryptPassword(repetirSenha));
                                    userServico.Save(this.user);
                                    Msg.messagemInfoRedirect("Operação realizada com sucesso !", "usuario.xhtml?visualizar=" + this.user.getId());
                                }

                            } else {
                                Msg.messagemError("As senhas não conferem !");
                            }
                        } else {
                            Msg.messagemError("Você precisa repetir a senha !");
                        }
                    } else {
                        Msg.messagemError("CPF invalido !!");
                    }
                }
            }

        }

    }

    public List<Usuario> getAll() {
        if (Utils.isNotEmpty(userlogado.getUnidadeOrganizacional())) {
            return userServico.FindAll(userlogado.getUnidadeOrganizacional());
        } else {
            return userServico.FindAll();
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
        if (Utils.isNotEmpty(user)) {
            this.users = userServico.pesquisar(this.user, userlogado.getUnidadeOrganizacional());
        }
        Msg.messagemInfo("Operação realizada com sucesso !");
    }

    public void pesquisar() {
        this.users = userServico.pesquisar(this.user, userlogado.getUnidadeOrganizacional());
        if (users.size() > 0) {
            Msg.messagemInfo("Operação realizada com sucesso !");
        } else {
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
        this.user.setGrupos(new ArrayList<Grupo>());
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

    public List<UnidadeOrganizacional> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadeOrganizacional> unidades) {
        this.unidades = unidades;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

}
