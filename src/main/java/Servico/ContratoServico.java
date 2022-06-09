/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Contrato;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class ContratoServico extends ServicoGenerico<Contrato> implements Serializable{
    
    public ContratoServico() {
        super(Contrato.class);
    }
    
    public List<Contrato> findPesquisa(Contrato contrato) {
        String sql = "select c from Contrato c where ";
        if (Utils.isNotEmpty(contrato.getNumeroContrato())) {
            sql += "c.NumeroContrato = :nc and ";
        }
        if (Utils.isNotEmpty(contrato.getContratado())) {
            sql += "c.contratado = :contratado and ";
        }
        if (Utils.isNotEmpty(contrato.getNumeroProcesso())) {
            sql += "c.NumeroProcesso = :np and ";
        }
        if (Utils.isNotEmpty(contrato.getNumeroLicitacao())) {
            sql += "c.NumeroLicitacao = :nl and ";
        }
        if (Utils.isNotEmpty(contrato.getTipoContrato())) {
            sql += "c.tipoContrato = :tc and ";
        }
        if (Utils.isNotEmpty(contrato.getTipoLicitacao())) {
            sql += "c.tipoLicitacao = :tl and ";
        }
        if (Utils.isNotEmpty(contrato.getValor())) {
            sql += "c.valor = :valor and ";
        }
        if (Utils.isNotEmpty(contrato.isPossuiTempoDeterminado())) {
            sql += "c.possuiTempoDeterminado = :tempoDeterminado and ";
        }
        if (Utils.isNotEmpty(contrato.getDataInicio())) {
            sql += "c.dataInicio = :di and ";
        }
        if (Utils.isNotEmpty(contrato.getDataFinal())) {
            sql += "c.dataFinal = :df and ";
        }
        if (Utils.isNotEmpty(contrato.getDataAssinatura())) {
            sql += "c.dataAssinatura = :da and ";
        }
        if (Utils.isNotEmpty(contrato.getDataRenovacao())) {
            sql += "c.dataRenovacao = :dr and ";
        }
        if (Utils.isNotEmpty(contrato.getObjetoContrato())) {
            sql += "c.objetoContrato = :oc and ";
        }
        if (Utils.isNotEmpty(contrato.getFiscalContrato())) {
            sql += "c.fiscalContrato = :fc and ";
        }
        if (Utils.isNotEmpty(contrato.getStatus())) {
            sql += "c.status = :status and ";
        }
        if (Utils.isNotEmpty(contrato.getBanco())) {
            sql += "c.banco = :banco and ";
        }
        if (Utils.isNotEmpty(contrato.getNumeroConta())) {
            sql += "c.numeroConta = :numeroConta and ";
        }
        if (Utils.isNotEmpty(contrato.getDigito())) {
            sql += "c.digito = :digito and ";
        }
        if (Utils.isNotEmpty(contrato.getAgencia())) {
            sql += "c.agencia = :agencia and ";
        }
        if (Utils.isNotEmpty(contrato.getOperacao())) {
            sql += "c.operacao = :operacao and ";
        }
        if (Utils.isNotEmpty(contrato.getSaldoInicial())) {
            sql += "c.saldoInicial = :saldo and ";
        }
        

        sql += "c.ativo = true";
        Query query = entityManager.createQuery(sql);

        if (Utils.isNotEmpty(contrato.getNumeroContrato())) {
            query.setParameter("nc", contrato.getNumeroContrato());
        }
        if (Utils.isNotEmpty(contrato.getContratado())) {
            query.setParameter("contratado", contrato.getContratado());
        }
        if (Utils.isNotEmpty(contrato.getNumeroProcesso())) {
            query.setParameter("np", contrato.getNumeroProcesso());
        }
        if (Utils.isNotEmpty(contrato.getNumeroLicitacao())) {
            query.setParameter("nl", contrato.getNumeroLicitacao());
        }
        if (Utils.isNotEmpty(contrato.getTipoContrato())) {
            query.setParameter("tc", contrato.getTipoContrato());
        }
        if (Utils.isNotEmpty(contrato.getTipoLicitacao())) {
            query.setParameter("tl", contrato.getTipoLicitacao());
        }
        if (Utils.isNotEmpty(contrato.getValor())) {
            query.setParameter("valor", contrato.getValor());
        }
        if (Utils.isNotEmpty(contrato.isPossuiTempoDeterminado())) {
            query.setParameter("tempoDeterminado", contrato.isPossuiTempoDeterminado());
        }
        if (Utils.isNotEmpty(contrato.getDataInicio())) {
            query.setParameter("di", contrato.getDataInicio());
        }
        if (Utils.isNotEmpty(contrato.getDataFinal())) {
            query.setParameter("df", contrato.getDataFinal());
        }
        if (Utils.isNotEmpty(contrato.getDataAssinatura())) {
            query.setParameter("da", contrato.getDataAssinatura());
        }
        if (Utils.isNotEmpty(contrato.getDataRenovacao())) {
            query.setParameter("dr", contrato.getDataRenovacao());
        }
        if (Utils.isNotEmpty(contrato.getObjetoContrato())) {
            query.setParameter("oc", contrato.getObjetoContrato());
        }
        if (Utils.isNotEmpty(contrato.getFiscalContrato())) {
            query.setParameter("fc", contrato.getFiscalContrato());
        }
        if (Utils.isNotEmpty(contrato.getStatus())) {
            query.setParameter("status", contrato.getStatus());
        }
        if (Utils.isNotEmpty(contrato.getBanco())) {
            query.setParameter("banco", contrato.getBanco());
        }
        if (Utils.isNotEmpty(contrato.getNumeroConta())) {
            query.setParameter("numeroConta", contrato.getNumeroConta());
        }
        if (Utils.isNotEmpty(contrato.getDigito())) {
            query.setParameter("digito", contrato.getDigito());
        }
        if (Utils.isNotEmpty(contrato.getAgencia())) {
            query.setParameter("agencia", contrato.getAgencia());
        }
        if (Utils.isNotEmpty(contrato.getOperacao())) {
            query.setParameter("operacao", contrato.getOperacao());
        }
        if (Utils.isNotEmpty(contrato.getSaldoInicial())) {
            query.setParameter("saldo", contrato.getSaldoInicial());
        }
        return query.getResultList();
    }
    
    public boolean existNumero(BigInteger numero) {
        String sql = "select c from Contrato c where c.ativo = true ";
        if (Utils.isNotEmpty(numero)) {
            sql += "and c.NumeroContrato = :numero ";
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
    
}
