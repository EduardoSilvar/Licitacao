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
import modelo.Supressao;
import util.Utils;

/**
 *
 * @author dylan
 */
@Stateless
public class SupressaoServico extends ServicoGenerico<Supressao> implements Serializable{

    public SupressaoServico() {
        super(Supressao.class);
    }
    
    public boolean existNumero(Long numero) {
        String sql = "select c from Supressao c where c.ativo = true ";
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
    
    public List<Supressao> pesquisarSupressaoPorContrato(Contrato contrato,Supressao supressao){
        String sql = "select c from Supressao c where c.contrato = :ct ";
        if (Utils.isNotEmpty(supressao.getNumeroTermo())) {
            sql += "and c.numeroTermo = :nt ";
        }
        if (Utils.isNotEmpty(supressao.getValor())) {
            sql += "and c.valor = :valor ";
        }
        sql += "and c.ativo = true";
        Query query = entityManager.createQuery(sql);
        query.setParameter("ct", contrato);
        if (Utils.isNotEmpty(supressao.getNumeroTermo())) {
            query.setParameter("nt", supressao.getNumeroTermo());
        }
        if (Utils.isNotEmpty(supressao.getValor())) {
            query.setParameter("valor", supressao.getValor());
        }
        return query.getResultList();
    }
    
    public List<Supressao> findPesquisa(Supressao supressao) {
        String sql = "select c from Supressao c where ";
        if (Utils.isNotEmpty(supressao.getContrato())) {
            sql += "c.contrato = :ct and ";
        }
        if (Utils.isNotEmpty(supressao.getNumeroTermo())) {
            sql += "c.numeroTermo = :nt and ";
        }
        if (Utils.isNotEmpty(supressao.getValor())) {
            sql += "c.valor = :valor and ";
        }
        if (Utils.isNotEmpty(supressao.getDataAssinatura())) {
            sql += "c.dataAssinatura = :da and ";
        }
        if (Utils.isNotEmpty(supressao.getFiscal())) {
            sql += "c.fiscal = :fiscal and ";
        }

        sql += "c.ativo = true";
        Query query = entityManager.createQuery(sql);

        if (Utils.isNotEmpty(supressao.getContrato())) {
            query.setParameter("ct", supressao.getContrato());
        }
        if (Utils.isNotEmpty(supressao.getNumeroTermo())) {
            query.setParameter("nt", supressao.getNumeroTermo());
        }
        if (Utils.isNotEmpty(supressao.getValor())) {
            query.setParameter("valor", supressao.getValor());
        }
        if (Utils.isNotEmpty(supressao.getDataAssinatura())) {
            query.setParameter("da", supressao.getDataAssinatura());
        }
        if (Utils.isNotEmpty(supressao.getFiscal())) {
            query.setParameter("fiscal", supressao.getFiscal());
        }
        return query.getResultList();
    }
    
}
