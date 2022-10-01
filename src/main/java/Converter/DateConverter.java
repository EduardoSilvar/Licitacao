/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converter;

import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import util.DateUtils;

/**
 *
 * @author eduardo
 */
@FacesConverter(value = "dataConverter")
public class DateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value != null && !value.isEmpty()) {
            Date data = DateUtils.parse(DateUtils.DD_MM_YYYY, value);
            String dataFormatada = DateUtils.format(DateUtils.YYYY_MM_DD_T_HH_MM_SS_SSS_SS_SS, data);
            return dataFormatada;
        }
        return null;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {
            String dataString = (String) value;
            if (!dataString.isEmpty()) {
                Date data = DateUtils.parse(DateUtils.YYYY_MM_DD_T_HH_MM_SS_SSS_SS_SS, dataString);
                String dataFormatada = DateUtils.format(DateUtils.DD_MM_YYYY, data);
                return dataFormatada;
            }
        }
        return null;

    }

}
