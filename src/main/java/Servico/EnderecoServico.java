/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import javax.ejb.Stateless;
import modelo.Endereco;

/**
 *
 * @author eduardo
 */
@Stateless
public class EnderecoServico extends ServicoGenerico<Endereco>{
    
    public EnderecoServico() {
        super(Endereco.class);
    }
    
}
