/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.CabecalhoRodape;

/**
 *
 * @author eduardo
 */
@FacesConverter(value = "cabecalhorodapeconverter", forClass = CabecalhoRodape.class)
public class CabecalhoRodapeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (CabecalhoRodape) uiComponent.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {

        if (value instanceof CabecalhoRodape) {

            CabecalhoRodape entity = (CabecalhoRodape) value;

            if (entity != null && entity instanceof CabecalhoRodape && entity.getId() != null) {
                uiComponent.getAttributes().put(entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
}
