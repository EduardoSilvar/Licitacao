/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import Enum.StatusContrato;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Contrato;
import modelo.PedidoRenovacao;

/**
 *
 * @author eduardo
 */
@Stateless
public class MensagemServico implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public List<Contrato> aprovado() {
        StatusContrato status = StatusContrato.APROVADO;
        String sql = "select c from Contrato c where c.status = :status and c.ativo = true";
        Query query = em.createQuery(sql);
        query.setParameter("status", status);
        return query.getResultList();

    }

    public List<Contrato> expirar() {
        StatusContrato status = StatusContrato.PROXIMO_EXPIRAR;
        String sql = "select c from Contrato c where c.status = :status and c.ativo = true ";
        Query query = em.createQuery(sql);
        query.setParameter("status", status);
        System.err.println(query.getResultList().size() + "é o total");
        return query.getResultList();

    }

    public List<PedidoRenovacao> pedidoRenovacao() {
        String sql = "select p from Pedidorenovacao p where p.ativo = true";
        Query query = em.createQuery(sql);
        return query.getResultList();
    }

}
