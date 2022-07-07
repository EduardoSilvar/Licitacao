/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import Enum.StatusContrato;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author eduardo
 */
@Stateless
public class GraficoServico {

    @PersistenceContext
    private EntityManager em;

    public int Contratos(StatusContrato status) {
        String sql = "select c Contrato c where c.ativo = true and c.status = :status";
        Query query = em.createQuery(sql);
        query.setParameter("status", status);

        return query.getResultList().size();
    }

}
