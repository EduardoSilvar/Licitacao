/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import javax.ejb.Stateless;
import modelo.Pessoa;

/**
 *
 * @author eduardo
 */
@Stateless
public class PessoaServico extends ServicoGenerico<Pessoa> {

    public PessoaServico() {
        super(Pessoa.class);
    }

}
