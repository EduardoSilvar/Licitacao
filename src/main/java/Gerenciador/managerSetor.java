/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.SetorServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Setor;
import util.Msg;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerSetor extends managerPrincipal implements Serializable {

    @EJB
    private SetorServico setorServico;
    private Setor setor;
    private List<Setor> setores;

    @Override
    public void carregar(String param) {
        this.setor = setorServico.find(Long.parseLong(param));
        this.setores = new ArrayList<>();
    }

    @Override
    public void instanciar() {
        instanciarSetor();
        instanciarSetores();
    }

    public void instanciarSetores() {
        this.setores = new ArrayList<>();
    }

    public void instanciarSetor() {
        this.setor = new Setor();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarSetor.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "setor.xhtml?visualizar=" + this.setor.getId();
    }

    public void salvar() {
        setorServico.Save(this.setor);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "setor.xhtml?visualizar=" + this.setor.getId());
    }

    public void deletar() {
        this.setor.setAtivo(false);
        this.setores.remove(this.setor);
        setorServico.Update(this.setor);
        Msg.messagemInfo("Operação realizada com sucesso !");

    }

    public void atualizar() {
        setorServico.Update(this.setor);
        Msg.messagemInfoRedirect("Operação realizada com sucesso", "setor.xhtml?visualizar=" + this.setor.getId());
    }

    public void pesquisar() {
        this.setores = setorServico.pesquisar(this.setor);
        if (this.setores.size() > 0) {
            Msg.messagemInfo("Pesquisa realizada com sucesso !");
        } else {
            Msg.messagemError("Nennhum setor encontrato !");
        }

    }

    public SetorServico getSetorServico() {
        return setorServico;
    }

    public void setSetorServico(SetorServico setorServico) {
        this.setorServico = setorServico;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public void setSetores(List<Setor> setores) {
        this.setores = setores;
    }

}
