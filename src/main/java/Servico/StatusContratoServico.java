/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.StatusContrato;

/**
 *
 * @author eduardo
 */
@Stateless
public class StatusContratoServico extends ServicoGenerico<StatusContrato> implements Serializable{

    public StatusContratoServico() {
        super(StatusContrato.class);
    }

    public List<StatusContrato> pesquisar(StatusContrato stts) {
        String sql = "select * from statusContrato s where s.ativo = true";
        if (util.Utils.isNotEmpty(stts.getStatus())) {
            sql += " and s.status = :status";
        }
        if (util.Utils.isNotEmpty(stts.getDescricao())) {
            sql += " and s.descricao = :descricao";
        }
        Query query = getEntityManager().createQuery(sql);
        if (util.Utils.isNotEmpty(stts.getStatus())) {
            query.setParameter("status", stts.getStatus());
        }
        if (util.Utils.isNotEmpty(stts.getDescricao())) {
            query.setParameter("descricao", stts.getDescricao());
        }
        return query.getResultList();
    }

}
