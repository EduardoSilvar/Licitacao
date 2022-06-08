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

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerContrato implements Serializable {

    @EJB
    private ContratoServico contratoServico;

    private Contrato contrato;
    private boolean valorMudou;
    private boolean tempoDeterminado;
    private List<Contrato> contratos;

    @PostConstruct
    public void iniciar() {
        InstanciarContrato();
        InstanciarContratos();
        this.valorMudou = false;
        this.tempoDeterminado = false;
    }

    public void Salvar() {
        contrato.setPossuiTempoDeterminado(tempoDeterminado);
        contratoServico.Save(contrato);
    }
    
    public List<Contratado> getContratados(){
        return contratoServico.findAllContratados();
    }
    
    public void Deletar(Long id) {
        Contrato novoContrato = contratoServico.find(id);
        novoContrato.setAtivo(false);
        contratoServico.Update(novoContrato);
    }

    public void Atualizar(Contrato contrato) {
        contratoServico.Update(contrato);
    }

    public boolean renderedNovoValor() {
        return valorMudou;
    }
    
    public boolean renderedTempoDeterminado(){
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
