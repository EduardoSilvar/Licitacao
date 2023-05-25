/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Acrescimo;
import modelo.Contrato;
import util.Utils;

/**
 *
 * @author dylan
 */
@Stateless
public class AcrescimoServico extends ServicoGenerico<Acrescimo> implements Serializable {

    public AcrescimoServico() {
        super(Acrescimo.class);
    }

    public boolean existNumero(Long numero) {
        String sql = "select c from Acrescimo c where c.ativo = true ";
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

    public List<Acrescimo> pesquisarAcrescimoPorContrato(Contrato contrato, Acrescimo acrescimo) {
        String sql = "select c from Acrescimo c where c.contrato = :ct ";
        if (Utils.isNotEmpty(acrescimo.getNumeroTermo())) {
            sql += "and c.numeroTermo = :nt ";
        }
        if (Utils.isNotEmpty(acrescimo.getValor())) {
            sql += "and c.valor = :valor ";
        }
        if (Utils.isNotEmpty(acrescimo.getDataAssinatura())) {
            sql += "and c.dataAssinatura = :dataAssinatura ";
        }
        sql += "and c.ativo = true";
        Query query = entityManager.createQuery(sql);
        query.setParameter("ct", contrato);
        if (Utils.isNotEmpty(acrescimo.getNumeroTermo())) {
            query.setParameter("nt", acrescimo.getNumeroTermo());
        }
        if (Utils.isNotEmpty(acrescimo.getValor())) {
            query.setParameter("valor", acrescimo.getValor());
        }
        if (Utils.isNotEmpty(acrescimo.getDataAssinatura())) {
            query.setParameter("dataAssinatura", acrescimo.getDataAssinatura());
        }
        return query.getResultList();
    }

    public List<Acrescimo> pesquisarAcrescimoPorContrato(Contrato contrato) {
        String sql = "select c from Acrescimo c where c.ativo = true ";
        if (Utils.isNotEmpty(contrato)) {
            if (Utils.isNotEmpty(contrato.getId())) {
                sql += "and c.contrato.id = :id ";
            }
        }
        Query query = entityManager.createQuery(sql);
        if (Utils.isNotEmpty(contrato)) {
            if (Utils.isNotEmpty(contrato.getId())) {
                query.setParameter("id", contrato.getId());
            }
        }
        return query.getResultList();
    }

    public List<Acrescimo> findPesquisa(Acrescimo acrescimo) {
        String sql = "select c from Acrescimo c where ";
        if (Utils.isNotEmpty(acrescimo.getContrato())) {
            sql += "c.contrato = :ct and ";
        }
        if (Utils.isNotEmpty(acrescimo.getNumeroTermo())) {
            sql += "c.numeroTermo = :nt and ";
        }
        if (Utils.isNotEmpty(acrescimo.getValor())) {
            sql += "c.valor = :valor and ";
        }
        if (Utils.isNotEmpty(acrescimo.getDataAssinatura())) {
            sql += "c.dataAssinatura = :da and ";
        }
        if (Utils.isNotEmpty(acrescimo.getFiscal())) {
            sql += "c.fiscal = :fiscal and ";
        }

        sql += "c.ativo = true";
        Query query = entityManager.createQuery(sql);

        if (Utils.isNotEmpty(acrescimo.getContrato())) {
            query.setParameter("ct", acrescimo.getContrato());
        }
        if (Utils.isNotEmpty(acrescimo.getNumeroTermo())) {
            query.setParameter("nt", acrescimo.getNumeroTermo());
        }
        if (Utils.isNotEmpty(acrescimo.getValor())) {
            query.setParameter("valor", acrescimo.getValor());
        }
        if (Utils.isNotEmpty(acrescimo.getDataAssinatura())) {
            query.setParameter("da", acrescimo.getDataAssinatura());
        }
        if (Utils.isNotEmpty(acrescimo.getFiscal())) {
            query.setParameter("fiscal", acrescimo.getFiscal());
        }
        return query.getResultList();
    }
}
