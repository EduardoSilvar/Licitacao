/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.UnidadeOrganizacionalServico;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.UnidadeOrganizacional;

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

    @Override
    public void carregar(String param) {
        this.unidade = unidadeServico.find(Long.parseLong(param));
    }

    @Override
    public void instanciar() {
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
    }

    public UnidadeOrganizacional getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeOrganizacional unidade) {
        this.unidade = unidade;
    }

}
