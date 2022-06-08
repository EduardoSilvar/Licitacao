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
import modelo.TipoLicitacao;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class TipoLicitacaoServico extends ServicoGenerico<TipoLicitacao> implements Serializable{
    
    public TipoLicitacaoServico() {
        super(TipoLicitacao.class);
    }
    
    public List<TipoLicitacao> findAllTiposLicitacao(){
        String sql = "select a from TipoLicitacao a";
        Query query = getEntityManager().createQuery(sql);
        return query.getResultList();
    }
    
    public List<TipoLicitacao> pesquisar(TipoLicitacao tipo) {
        String sql = "select a from TipoLicitacao a where ";
        if (Utils.isNotEmpty(tipo.getDescricao())) {
            sql += "a.descricao = :desc and ";
        }
        if (Utils.isNotEmpty(tipo.getTipo())) {
            sql += "a.tipo = :tipo and ";
        }
        sql += "a.ativo = true";
        
        Query query = getEntityManager().createQuery(sql);
        
        if (Utils.isNotEmpty(tipo.getDescricao())) {
            query.setParameter("desc", tipo.getDescricao().trim());
        }
        if (Utils.isNotEmpty(tipo.getTipo())) {
            query.setParameter("tipo", tipo.getTipo().trim());
        }
        return query.getResultList();
    }
    
}
