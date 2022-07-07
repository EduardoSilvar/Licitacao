/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import java.io.Serializable;
import javax.ejb.Stateless;
import modelo.Configuracao;
/**
 *
 * @author eduardo
 */
@Stateless
public class ConfiguracaoServico extends ServicoGenerico<Configuracao> implements Serializable {

    public ConfiguracaoServico() {
        super(Configuracao.class);
    }

}
