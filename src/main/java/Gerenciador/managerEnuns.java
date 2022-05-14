/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.NaturezaEnum;
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
}
