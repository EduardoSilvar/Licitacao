/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Repactuacao;
import util.Utils;

/**
 *
 * @author dylan
 */
@Stateless
public class RepactuacaoServico extends ServicoGenerico<Repactuacao> implements Serializable{

    public RepactuacaoServico() {
        super(Repactuacao.class);
    }
    
    public boolean existNumero(Long numero) {
        String sql = "select c from Repactuacao c where c.ativo = true ";
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
    
    public List<Repactuacao> findPesquisa(Repactuacao repactuacao) {
        String sql = "select c from Repactuacao c where ";
        if (Utils.isNotEmpty(repactuacao.getContrato())) {
            sql += "c.contrato = :ct and ";
        }
        if (Utils.isNotEmpty(repactuacao.getNumeroTermo())) {
            sql += "c.numeroTermo = :nt and ";
        }
        if (Utils.isNotEmpty(repactuacao.getDataAssinatura())) {
            sql += "c.dataAssinatura = :da and ";
        }
        if (Utils.isNotEmpty(repactuacao.getFiscal())) {
            sql += "c.fiscal = :fiscal and ";
        }

        sql += "c.ativo = true";
        Query query = entityManager.createQuery(sql);

        if (Utils.isNotEmpty(repactuacao.getContrato())) {
            query.setParameter("ct", repactuacao.getContrato());
        }
        if (Utils.isNotEmpty(repactuacao.getNumeroTermo())) {
            query.setParameter("nt", repactuacao.getNumeroTermo());
        }
        if (Utils.isNotEmpty(repactuacao.getDataAssinatura())) {
            query.setParameter("da", repactuacao.getDataAssinatura());
        }
        if (Utils.isNotEmpty(repactuacao.getFiscal())) {
            query.setParameter("fiscal", repactuacao.getFiscal());
        }
        return query.getResultList();
    }
}