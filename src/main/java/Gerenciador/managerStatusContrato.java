/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.StatusContratoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.StatusContrato;
import util.Msg;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerStatusContrato extends managerPrincipal implements Serializable {

    @EJB
    private StatusContratoServico sttsContratoServico;

    private StatusContrato statusContrato;
    private List<StatusContrato> sttsContratos;

    @Override
    public void carregar(String param) {
        this.statusContrato = sttsContratoServico.find(Long.parseLong(param));
        instanciarListStts();
    }

    @Override
    public void instanciar() {
        instanciarStatus();
        instanciarListStts();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarStatusContrato.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "statusContrato.xhtml?visualizar=" + this.statusContrato.getId();
    }

    public void instanciarStatus() {
        this.statusContrato = new StatusContrato();
    }

    public void instanciarListStts() {
        this.sttsContratos = new ArrayList<>();
    }
    
    public List<StatusContrato> getAll(){
        return sttsContratoServico.FindAll();
    }
    
    public void salvar() {
        System.out.println(this.statusContrato);
        sttsContratoServico.Save(this.statusContrato);
        Msg.messagemInfoRedirect("Processo relizado com sucesso !", "statusContrato.xhtml?visualizar=" + this.statusContrato.getId());
    }

    public void atualizar() {
        sttsContratoServico.Update(this.statusContrato);
        Msg.messagemInfoRedirect("Processo relizado com sucesso !", "statusContrato.xhtml?visualizar=" + this.statusContrato.getId());

    }

    public void deletar() {
        StatusContrato novoStatus = sttsContratoServico.find(this.statusContrato.getId());
        novoStatus.setAtivo(false);
        sttsContratoServico.Update(novoStatus);
        sttsContratos.remove(novoStatus);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "statusContrato.xhtml");
    }

    public void pesquisar() {
        this.sttsContratos = sttsContratoServico.pesquisar(this.statusContrato);
        Msg.messagemInfo("Pesquisa realizada com sucessso !");
    }

    public StatusContratoServico getSttsContratoServico() {
        return sttsContratoServico;
    }

    public void setSttsContratoServico(StatusContratoServico sttsContratoServico) {
        this.sttsContratoServico = sttsContratoServico;
    }

    public StatusContrato getStatusContrato() {
        return statusContrato;
    }

    public void setStatusContrato(StatusContrato statusContrato) {
        this.statusContrato = statusContrato;
    }

    public List<StatusContrato> getSttsContratos() {
        return sttsContratos;
    }

    public void setSttsContratos(List<StatusContrato> sttsContratos) {
        this.sttsContratos = sttsContratos;
    }

}
