/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.TipoLicitacao;

/**
 *
 * @author eduardo
 */
@Stateless
public class TipoLicitacaoServico extends ServicoGenerico<TipoLicitacao> {

    public TipoLicitacaoServico() {
        super(TipoLicitacao.class);
    }

    public List<TipoLicitacao> pesquisar(TipoLicitacao tipo) {
        String sql = "select a from TipoLicitacao a where ";
        if (tipo.getDescricao() != null && tipo != null) {
            sql += "a.descricao = :desc and ";
        }
        if (tipo.getTipo() != null && tipo != null) {
            sql += "a.tipo = :tipo and ";
        }
        sql += "a.ativo = true";

        Query query = getEntityManager().createQuery(sql);

        if (tipo.getDescricao() != null && tipo != null) {
            query.setParameter("desc", "%" + tipo.getDescricao().trim() + "%");
        }
        if (tipo.getTipo() != null && tipo != null) {
            query.setParameter("tipo", "%" + tipo.getTipo().trim() + "%");
        }
        System.err.println(query.getResultList());
        return query.getResultList();
    }

}
