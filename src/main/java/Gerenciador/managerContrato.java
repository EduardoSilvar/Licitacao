/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.ContratadoServico;
import Servico.ContratoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contratado;
import modelo.Contrato;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerContrato extends managerPrincipal implements Serializable {

    @EJB
    private ContratoServico contratoServico;

    private Contrato contrato;
    private boolean valorMudou;
    private boolean tempoDeterminado;
    private List<Contrato> contratos;

    @Override
    public void carregar(String param) {
        this.contrato = contratoServico.find(Long.parseLong(param));
        this.contratos = new ArrayList<>();
    }

    @Override
    public void instanciar() {
        InstanciarContrato();
        InstanciarContratos();
    }

    @Override
    public String getUrlVisualizar() {
        return "contrato.xhtml?visualizar=" + this.contrato.getId();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarContrato.xhtml";
    }

    @PostConstruct
    public void iniciar() {
        this.valorMudou = false;
        this.tempoDeterminado = false;
    }

    public void Salvar() {
        contrato.setPossuiTempoDeterminado(tempoDeterminado);
        if (Utils.isNotEmpty(contrato.getNumeroContrato())) {
            if (contratoServico.existNumero(contrato.getNumeroContrato())) {
                Msg.messagemError("Número de contrato já registrado !");
            } else {
                contratoServico.Save(contrato);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contrato.xhtml?visualizar=" + this.contrato.getId());
            }
        } else {
            contratoServico.Save(contrato);
            Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contrato.xhtml?visualizar=" + this.contrato.getId());

        }
    }

    public void pesquisar() {
        System.err.println("rendeu meu parceiro");
        this.contratos = contratoServico.findPesquisa(this.contrato);
    }

    public void Deletar() {
        try {
            Contrato novoContrato = contratoServico.find(this.contrato.getId());
            novoContrato.setAtivo(false);
            contratoServico.Update(novoContrato);
            contratos.remove(novoContrato);
            Msg.messagemInfoRedirect("Processo realizado com sucesso !", "contrato.xhtml");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void Atualizar() {
        contratoServico.Update(contrato);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contrato.xhtml?visualizar=" + this.contrato.getId());
    }

    public boolean renderedNovoValor() {
        return valorMudou;
    }

    public boolean renderedTempoDeterminado() {
        return tempoDeterminado;
    }

    public Contrato Pegar(Long id) {
        return contratoServico.find(id);
    }

    public void print() {
        System.out.println(valorMudou);
    }

    public void PegarTodos() {
        contratos = contratoServico.FindAll();
    }

    public void InstanciarContratos() {
        contratos = new ArrayList<>();
    }

    public void InstanciarContrato() {
        contrato = new Contrato();
    }

    public boolean getValorMudou() {
        return this.valorMudou;
    }

    public void setValorMudou(boolean valorMudou) {
        this.valorMudou = valorMudou;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public boolean isTempoDeterminado() {
        return tempoDeterminado;
    }

    public void setTempoDeterminado(boolean tempoDeterminado) {
        this.tempoDeterminado = tempoDeterminado;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

}
