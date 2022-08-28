/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import Enum.StatusContrato;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Contratado;
import modelo.Contrato;
import modelo.ContratoVo;
import modelo.Setor;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class ContratoServico extends ServicoGenerico<Contrato> implements Serializable {

    public ContratoServico() {
        super(Contrato.class);
    }

    public List<ContratoVo> buscarContratos(Usuario user) {

        String sql = "select new modelo.ContratoVo(c.status, count(c)) "
                + "from Contrato c ";
        sql += "where c.ativo = true ";
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += "and c.unidadeOrganizacional = :unidade ";
        }
        sql += "group by c.status";

        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();
    }

    public List<ContratoVo> buscarTipoContrato(Usuario user) {

        String sql = "select new modelo.ContratoVo(c.tipoContrato, count(c)) "
                + "from Contrato c ";
        sql += "where c.ativo = true ";
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += "and c.unidadeOrganizacional = :unidade ";
        }
        sql += "group by c.tipoContrato.id";

        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();
    }

    public List<ContratoVo> buscarContratoSetor(Setor setor, Contratado contratado, Usuario user) {

        String sql = "select new modelo.ContratoVo(c.status, count(c)) "
                + "from Contrato c ";
        sql += "where c.ativo = true ";
        if (Utils.isNotEmpty(setor)) {
            sql += "and c.setor = :setor ";
        }
        if (Utils.isNotEmpty(contratado)) {
            sql += "and c.contratado = :contratado ";
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += "and c.unidadeOrganizacional = :unidade ";
        }
        sql += "group by c.status";

        Query query = getEntityManager().createQuery(sql);

        if (Utils.isNotEmpty(setor)) {
            query.setParameter("setor", setor);
        }
        if (Utils.isNotEmpty(contratado)) {
            query.setParameter("contratado", contratado);
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();
    }

    public List<Contrato> buscarValorContrato(Setor setor, Usuario user) {
        String sql = "select c from Contrato c where c.ativo = true ";
        if (Utils.isNotEmpty(setor)) {
            sql += "and c.setor = :setor ";
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += "and c.unidadeOrganizacional = :unidade ";
        }
        Query query = getEntityManager().createQuery(sql);

        if (Utils.isNotEmpty(setor)) {
            query.setParameter("setor", setor);
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();
    }

    public List<Contrato> findContrato(String nome) {
        String jpql = "select c from Contrato c where ";

        if (nome != null && !nome.isEmpty()) {
            jpql += "lower(c.nome) like lower(:nome) and ";
        }

        jpql += "c.ativo = true";

        Query query = entityManager.createQuery(jpql);

        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", "%" + nome + "%");
        }

        return query.getResultList();
    }

    public List<Contrato> findPesquisa(Contrato contrato, UnidadeOrganizacional unidade) {
        String sql = "select c from Contrato c where ";
        if (Utils.isNotEmpty(contrato.getContratado())) {
            sql += "c.contratado = :contratado and ";
        }
        if (Utils.isNotEmpty(unidade)) {
            sql += "c.unidadeOrganizacional.id = :unidade and ";
        }
        if (Utils.isNotEmpty(contrato.getFiscalContrato())) {
            sql += "c.fiscalContrato = :fiscal and ";
        }
        if (Utils.isNotEmpty(contrato.getNumeroContrato())) {
            sql += "c.numeroContrato = :numero and ";
        }
        if (Utils.isNotEmpty(contrato.getStatus())) {
            sql += "c.status = :status and ";
        }
        if (Utils.isNotEmpty(contrato.getDataFinal())) {
            sql += "c.dataFinal = :dataf and ";
        }
        if (Utils.isNotEmpty(contrato.getDataInicio())) {
            sql += "c.dataInicio = :datai and ";
        }
        if (Utils.isNotEmpty(contrato.getSetor())) {
            sql += "c.setor = :setor and ";
        }
        sql += "c.ativo = true";

        Query query = entityManager.createQuery(sql);
        if (Utils.isNotEmpty(contrato.getContratado())) {
            query.setParameter("contratado", contrato.getContratado());
        }
        if (Utils.isNotEmpty(contrato.getFiscalContrato())) {
            query.setParameter("fiscal", contrato.getFiscalContrato());
        }
        if (Utils.isNotEmpty(contrato.getStatus())) {
            query.setParameter("status", contrato.getStatus());
        }
        if (Utils.isNotEmpty(contrato.getDataFinal())) {
            query.setParameter("dataf", contrato.getDataFinal());
        }
        if (Utils.isNotEmpty(unidade)) {
            query.setParameter("unidade", unidade.getId());
        }
        if (Utils.isNotEmpty(contrato.getDataInicio())) {
            query.setParameter("datai", contrato.getDataInicio());
        }
        if (Utils.isNotEmpty(contrato.getNumeroContrato())) {
            query.setParameter("numero", contrato.getNumeroContrato());
        }
        if (Utils.isNotEmpty(contrato.getSetor())) {
            query.setParameter("setor", contrato.getSetor());
        }
        return query.getResultList();
    }

    public boolean existNumero(Long numero) {
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

    public List<Contratado> contratados(Usuario user) {
        String sql = "select c Contratado c where c.ativo = true";
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += " and c.unidadeOrganizacional = :unidade ";
        }
        Query query = entityManager.createQuery(sql);
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();

    }

}
