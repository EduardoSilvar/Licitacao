/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import javax.ejb.Stateless;
import modelo.Contrato;

/**
 *
 * @author eduardo
 */
@Stateless
public class ContratoServico extends ServicoGenerico<Contrato>{
    
    public ContratoServico() {
        super(Contrato.class);
    }
    
}
