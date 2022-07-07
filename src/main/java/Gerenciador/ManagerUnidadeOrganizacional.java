/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import static Gerenciador.managerLogin.VerificarLogin;
import Servico.UnidadeOrganizacionalServico;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Endereco;
import modelo.UnidadeOrganizacional;
import util.Msg;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class ManagerUnidadeOrganizacional extends managerPrincipal implements Serializable {

    @EJB
    UnidadeOrganizacionalServico unidadeServico;

    private UnidadeOrganizacional unidade;
    private List<UnidadeOrganizacional> unidades;

    public void salvar() {
        unidadeServico.Save(this.unidade);
        Msg.messagemInfo("Operação realizada com sucesso !");
    }

    public void deletar() {
        UnidadeOrganizacional Novaunidade = unidadeServico.find(this.unidade.getId());
        Novaunidade.setAtivo(false);
        unidadeServico.Update(Novaunidade);
        this.unidades.remove(Novaunidade);
        Msg.messagemInfo("Operação realizada com sucesso !");
    }

    public void atualizar() {
        unidadeServico.Update(this.unidade);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "unidadeOrganizacional.xhtml?visualizar=" + this.unidade.getId());
    }

    public void pesquisar() {
        this.unidades = unidadeServico.pesquisar(this.unidade);
    }

    @Override
    public void carregar(String param) {
        try {
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.unidade = unidadeServico.find(Long.parseLong(param));
    }

    @Override
    public void instanciar() {
        try {
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        instanciarUnidade();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarUnidadeOrganizacional.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "unidadeOrganizacional.xhtml?visualizar=" + this.unidade.getId();
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

}
