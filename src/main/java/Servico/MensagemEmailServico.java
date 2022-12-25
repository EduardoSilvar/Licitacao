/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Contratado;
import modelo.MensagemEmail;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class MensagemEmailServico extends ServicoGenerico<MensagemEmail> {

    public MensagemEmailServico() {
        super(MensagemEmail.class);
    }

    public List<MensagemEmail> findMensagens(Contratado contratado) {
        String sql = "select e from MensagemEmail e where e.ativo = true ";

        if (Utils.isNotEmpty(contratado)) {
            sql += "and e.destinatario = :contratado ";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(contratado)) {
            query.setParameter("contratado", contratado);
        }
        if (Utils.isNotEmpty(query.getResultList())) {
            return query.getResultList();
        } else {
            return new ArrayList<>();
        }
    }
}
