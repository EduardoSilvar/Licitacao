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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Endereco;
import modelo.UnidadeOrganizacional;
import util.Msg;
import util.Utils;

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
    private Long id;

    public void salvar() {
        unidadeServico.Save(this.unidade);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "orgao.xhtml?visualizar=" + this.unidade.getId());
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

    public void atualizar() {
        unidadeServico.Update(this.unidade);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "orgao.xhtml?visualizar=" + this.unidade.getId());
    }

    public void pesquisar() {
        this.unidades = unidadeServico.pesquisar(this.unidade);
        if (this.unidades.size() > 0) {
            Msg.messagemInfo("Operação realizada com sucesso !!");
        } else {
            Msg.messagemError("Nenhum orgão encontrado");
        }
    }

    @Override
    public void carregar(String param) {
        this.id = Long.parseLong(param);
        this.unidade = unidadeServico.find(Long.parseLong(param));
    }

    @Override
    public void instanciar() {
        instanciarUnidade();
        this.unidades = new ArrayList<>();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarUnidadeOrganizacional.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "orgao.xhtml?visualizar=" + this.unidade.getId();
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
