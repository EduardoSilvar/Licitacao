/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import modelo.Contratado;

/**
 *
 * @author eduardo
 */
@Stateless
public class ContratadoServico extends ServicoGenerico<Contratado> {
    
    
    public ContratadoServico() {
        super(Contratado.class);
    }
    
}
