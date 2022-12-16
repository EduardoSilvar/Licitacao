/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerEnuns implements Serializable {

    public List<SelectItem> getNatureza() {
        List<SelectItem> items = new ArrayList<>();
        for (NaturezaEnum item : NaturezaEnum.values()) {
            items.add(new SelectItem(item, item.getNome()));
        }
        return items;
    }

    public List<SelectItem> getStatusContrato() {
        List<SelectItem> items = new ArrayList<>();
        for (StatusContrato item : StatusContrato.values()) {
            items.add(new SelectItem(item, item.getStatus()));
        }
        return items;
    }
    
    public List<SelectItem> getEstados() {
        List<SelectItem> items = new ArrayList<>();
        for (Estado item : Estado.values()) {
            items.add(new SelectItem(item, item.getNome()));
        }
        return items;
    }
    
    public List<SelectItem> getTipoContrato(){
        List<SelectItem> items = new ArrayList<>();
        for (TipoContratoEnum item : TipoContratoEnum.values()) {
            items.add(new SelectItem(item, item.getNome()));
        }
        return items;
    }
    
    public List<SelectItem> getTipoLicitacao(){
        List<SelectItem> items = new ArrayList<>();
        for (TipoLicitacaoEnum item : TipoLicitacaoEnum.values()) {
            items.add(new SelectItem(item, item.getNome()));
        }
        return items;
    }
    
    public List<SelectItem> getTipoRecebimento(){
        List<SelectItem> items = new ArrayList<>();
        for (TipoRecebimentoEnum item : TipoRecebimentoEnum.values()) {
            items.add(new SelectItem(item, item.getNome()));
        }
        return items;
    }
    
    public List<SelectItem> getTipoFiscalizacao(){
        List<SelectItem> items = new ArrayList<>();
        for (TipoFiscalizacaoEnum item : TipoFiscalizacaoEnum.values()) {
            items.add(new SelectItem(item, item.getNome()));
        }
        return items;
    }

    public List<SelectItem> getUsers() {
        List<SelectItem> items = new ArrayList<>();
        for (TipoUsuario item : TipoUsuario.values()) {
            items.add(new SelectItem(item, item.getNome()));
        }
        return items;
    }

    public List<SelectItem> getGraficos() {
        List<SelectItem> items = new ArrayList<>();
        for (GraficoEnum item : GraficoEnum.values()) {
            items.add(new SelectItem(item, item.getNome()));
        }
        return items;
    }
}
