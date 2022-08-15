/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Contrato;
import modelo.Renovacao;
import util.Utils;

/**
 *
 * @author dylan
 */
@Stateless
public class RenovacaoServico extends ServicoGenerico<Renovacao> implements Serializable{
    public RenovacaoServico(){
        super(Renovacao.class);
    }
    
    public boolean existNumero(Long numero) {
        String sql = "select c from Renovacao c where c.ativo = true ";
        if (Utils.isNotEmpty(numero)) {
            sql += "and c.numeroTermo = :numero ";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(numero)) {
            query.setParameter("numero", numero);
        }
        if (query.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public List<Renovacao> pesquisarRenovacaoPorContrato(Contrato contrato,Renovacao renovacao){
        String sql = "select c from Renovacao c where c.contrato = :ct ";
        if (Utils.isNotEmpty(renovacao.getNumeroTermo())) {
            sql += "and c.numeroTermo = :nt ";
        }
        if (Utils.isNotEmpty(renovacao.getValor())) {
            sql += "and c.valor = :valor ";
        }
        sql += "and c.ativo = true";
        Query query = entityManager.createQuery(sql);
        query.setParameter("ct", contrato);
        if (Utils.isNotEmpty(renovacao.getNumeroTermo())) {
            query.setParameter("nt", renovacao.getNumeroTermo());
        }
        if (Utils.isNotEmpty(renovacao.getValor())) {
            query.setParameter("valor", renovacao.getValor());
        }
        return query.getResultList();
    }
    
    public List<Renovacao> findPesquisa(Renovacao renovacao) {
        String sql = "select c from Renovacao c where ";
        if (Utils.isNotEmpty(renovacao.getContrato())) {
            sql += "c.contrato = :ct and ";
        }
        if (Utils.isNotEmpty(renovacao.getNumeroTermo())) {
            sql += "c.numeroTermo = :nt and ";
        }
        if (Utils.isNotEmpty(renovacao.getDataInicial())) {
            sql += "c.dataInicial = :di and ";
        }
        if (Utils.isNotEmpty(renovacao.getDataFinal())) {
            sql += "c.dataFinal = :df and ";
        }
        if (Utils.isNotEmpty(renovacao.getDataAssinatura())) {
            sql += "c.dataAssinatura = :da and ";
        }
        if (Utils.isNotEmpty(renovacao.getFiscal())) {
            sql += "c.fiscal = :fiscal and ";
        }

        sql += "c.ativo = true";
        Query query = entityManager.createQuery(sql);

        if (Utils.isNotEmpty(renovacao.getContrato())) {
            query.setParameter("ct", renovacao.getContrato());
        }
        if (Utils.isNotEmpty(renovacao.getNumeroTermo())) {
            query.setParameter("nt", renovacao.getNumeroTermo());
        }
        if (Utils.isNotEmpty(renovacao.getDataInicial())) {
            query.setParameter("di", renovacao.getDataInicial());
        }
        if (Utils.isNotEmpty(renovacao.getDataFinal())) {
            query.setParameter("df", renovacao.getDataFinal());
        }
        if (Utils.isNotEmpty(renovacao.getDataAssinatura())) {
            query.setParameter("da", renovacao.getDataAssinatura());
        }
        if (Utils.isNotEmpty(renovacao.getFiscal())) {
            query.setParameter("fiscal", renovacao.getFiscal());
        }
        return query.getResultList();
    }
}
