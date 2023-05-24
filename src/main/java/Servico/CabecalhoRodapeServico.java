/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import Enum.TipoCabecalhoRodape;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.CabecalhoRodape;
import modelo.UnidadeOrganizacional;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class CabecalhoRodapeServico extends ServicoGenerico<CabecalhoRodape> implements Serializable {

    public CabecalhoRodapeServico() {
        super(CabecalhoRodape.class);
    }

    public void salvar(CabecalhoRodape cabecalhoRodape) {
        if (Utils.isNotEmpty(cabecalhoRodape)) {
            if (Utils.isNotEmpty(cabecalhoRodape.getId())) {
                Update(cabecalhoRodape);
            } else {
                getEntityManager().persist(cabecalhoRodape);
            }
        }

    }

    public List<CabecalhoRodape> autoCompleteRodape(String nome, UnidadeOrganizacional unidade) {
        String sql = "select c from CabecalhoRodape c where c.ativo  = true ";
        if (Utils.isNotEmpty(nome)) {
            sql += "and c.nome = :nome ";
        }
        if (Utils.isNotEmpty(unidade)) {
            sql += "and c.unidadeOrganizacional = :unidade ";
        }
        sql += "and c.tipo = :tipo ";
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(nome)) {
            query.setParameter("nome", nome);
        }
        query.setParameter("tipo", TipoCabecalhoRodape.RODAPE);
        if (Utils.isNotEmpty(unidade)) {
            query.setParameter("unidade", unidade);
        }
        return query.getResultList();
    }

    public List<CabecalhoRodape> autoCompleteCabecalho(String nome, UnidadeOrganizacional unidade) {
        String sql = "select c from CabecalhoRodape c where c.ativo  = true ";
        if (Utils.isNotEmpty(nome)) {
            sql += "and c.nome = :nome ";
        }
        if (Utils.isNotEmpty(unidade)) {
            sql += "and c.unidadeOrganizacional = :unidade ";
        }
        sql += "and c.tipo = :tipo ";
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(nome)) {
            query.setParameter("nome", nome);
        }
        if (Utils.isNotEmpty(unidade)) {
            query.setParameter("unidade", unidade);
        }
        query.setParameter("tipo", TipoCabecalhoRodape.CABECALHO);

        return query.getResultList();
    }

    public List<CabecalhoRodape> pesquisar(CabecalhoRodape cabecalhoRodape) {
        String sql = "select c from CabecalhoRodape c where c.ativo  = true ";
        if (Utils.isNotEmpty(cabecalhoRodape.getNome())) {
            sql += "and c.nome = :nome ";
        }
        if (Utils.isNotEmpty(cabecalhoRodape.getTipo())) {
            sql += "and c.tipo = :tipo ";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(cabecalhoRodape.getNome())) {
            query.setParameter("nome", cabecalhoRodape.getNome());
        }
        if (Utils.isNotEmpty(cabecalhoRodape.getTipo())) {
            query.setParameter("tipo", cabecalhoRodape.getTipo());
        }

        return query.getResultList();
    }

}
