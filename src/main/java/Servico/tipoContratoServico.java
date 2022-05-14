/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import javax.ejb.Stateless;
import modelo.TipoContrato;

/**
 *
 * @author eduardo
 */
@Stateless
public class tipoContratoServico extends ServicoGenerico<TipoContrato> {

    public tipoContratoServico() {
        super(TipoContrato.class);
    }

}
