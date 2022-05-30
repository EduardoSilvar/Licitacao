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
import modelo.TipoContrato;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class tipoContratoServico extends ServicoGenerico<TipoContrato> implements Serializable{

    public tipoContratoServico() {
        super(TipoContrato.class);
    }

    public List<TipoContrato> pesquisar(TipoContrato tipo) {
        String sql = "select t from TipoContrato t where ";
        if (Utils.isNotEmpty(tipo.getNome())) {
            sql += "t.nome = :nome and ";
        }
        if (Utils.isNotEmpty(tipo.getDescricao())) {
            sql += "t.descricao = :desc and ";
        }
        sql += "t.ativo = true";

        Query query = entityManager.createQuery(sql);
        if (Utils.isNotEmpty(tipo.getNome())) {
            query.setParameter("nome", tipo.getNome());
        }
        if (Utils.isNotEmpty(tipo.getDescricao())) {
            query.setParameter("desc", tipo.getDescricao());
        }

        return query.getResultList();

    }

}
