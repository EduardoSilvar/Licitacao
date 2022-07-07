/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.UnidadeOrganizacional;

/**
 *
 * @author eduardo
 */
@FacesConverter(value = "unidadeconverter", forClass = UnidadeOrganizacional.class)
public class UnidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (UnidadeOrganizacional) uiComponent.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {

        if (value instanceof UnidadeOrganizacional) {

            UnidadeOrganizacional entity = (UnidadeOrganizacional) value;

            if (entity != null && entity instanceof UnidadeOrganizacional && entity.getId() != null) {
                uiComponent.getAttributes().put(entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
}
