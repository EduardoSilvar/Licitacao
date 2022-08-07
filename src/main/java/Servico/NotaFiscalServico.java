/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.NotaFiscal;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class NotaFiscalServico extends ServicoGenerico<NotaFiscal> implements Serializable {

    public NotaFiscalServico() {
        super(NotaFiscal.class);
    }

    public List<NotaFiscal> pesquisar(NotaFiscal nota) {
        String sql = "select n from NotaFiscal n where n.ativo = true ";
        if (Utils.isNotEmpty(nota.getContrato())) {
            sql += "and n.contrato = :contrato ";
        }
        if (Utils.isNotEmpty(nota.getContratado())) {
            sql += "and n.contratado = :contratado ";
        }
        if (Utils.isNotEmpty(nota.getValor())) {
            sql += "and n.valor = :valor";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(nota.getContrato())) {
            query.setParameter("contrato", nota.getContrato());
        }
        if (Utils.isNotEmpty(nota.getContratado())) {
            query.setParameter("contratado", nota.getContratado());
        }
        if (Utils.isNotEmpty(nota.getValor())) {
            query.setParameter("valor", nota.getValor());
        }
        return query.getResultList();
    }

}
