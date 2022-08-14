/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import java.io.Serializable;
import javax.ejb.Stateless;
import modelo.Mensagem;

/**
 *
 * @author eduardo
 */
@Stateless
public class MensagemServico extends ServicoGenerico<Mensagem> implements Serializable {

    public MensagemServico() {
        super(Mensagem.class);
    }

}
