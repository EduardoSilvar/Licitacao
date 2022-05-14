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

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class TipoContratoManager implements Serializable {

    @EJB
    private tipoContratoServico tipoContratoServico;

    private TipoContrato tipoContrato;
    private List<TipoContrato> TipoContratos;

    @PostConstruct
    public void init() {
        instanciarLista();
        InstanciarTipoContrato();
    }

    public void Salvar() {
        tipoContratoServico.Save(tipoContrato);
    }

    public void Atualizar(TipoContrato tipocontrato) {
        tipoContratoServico.Update(tipocontrato);
    }

    public void Deletar(Long id) {
        TipoContrato novoTipoContrato = tipoContratoServico.Find(id);
        novoTipoContrato.setAtivo(false);

    }

    public void TrazerTodos() {
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
