/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.tipoContratoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.TipoContrato;
import util.Msg;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerTipoContrato extends ManagerPrincipal implements Serializable {

    @EJB
    private tipoContratoServico tipoContratoServico;

    private TipoContrato tipoContrato;
    private List<TipoContrato> TipoContratos;

    @Override
    public void carregar(String param) {
        this.tipoContrato = tipoContratoServico.find(Long.parseLong(param));
    }

    @Override
    public void instanciar() {
        InstanciarTipoContrato();
        instanciarLista();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarTipoContrato.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "tipoContrato.xhtml?visualizar=" + this.tipoContrato.getId();
    }

    public void salvar() {
        tipoContratoServico.Save(tipoContrato);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "tipoContrato.xhtml?visualizar=" + this.tipoContrato.getId());
    }

    public void atualizar() {
        tipoContratoServico.Update(this.tipoContrato);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "tipoContrato.xhtml?visualizar=" + this.tipoContrato.getId());
    }

    public void deletar(Long id) {
        TipoContrato novoTipoContrato = tipoContratoServico.find(id);
        novoTipoContrato.setAtivo(false);
        tipoContratoServico.Update(novoTipoContrato);
        TipoContratos.remove(novoTipoContrato);

    }

    public void pesquisar() {
        TipoContratos = tipoContratoServico.pesquisar(this.tipoContrato);
        if(TipoContratos.size() > 0){
        Msg.messagemInfo("Pesquisa feita com sucesso !");
        }else{
        Msg.messagemError("Nenhum tipo de contrato foi encontrato !");
        }
    }

    public void trazerTodos() {
        TipoContratos = tipoContratoServico.FindAll();

    }

    public void instanciarLista() {
        TipoContratos = new ArrayList<>();
    }

    public void InstanciarTipoContrato() {
        this.tipoContrato = new TipoContrato();
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public List<TipoContrato> getTipoContratos() {
        return TipoContratos;
    }

    public void setTipoContratos(List<TipoContrato> TipoContratos) {
        this.TipoContratos = TipoContratos;
    }

}
