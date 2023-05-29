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
import modelo.Contratado;
import modelo.ContratoVo;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class ContratadoServico extends ServicoGenerico<Contratado> implements Serializable {

    public ContratadoServico() {
        super(Contratado.class);
    }

    public List<Contratado> findContratado(String nome) {
        String jpql = "select c from Contratado c where ";

        if (nome != null && !nome.isEmpty()) {
            jpql += "UPPER(c.nome) like UPPER(:nome) and ";
        }

        jpql += "c.ativo = true";

        Query query = entityManager.createQuery(jpql);

        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", "%" + nome.trim() + "%");
        }

        return query.getResultList();
    }

    public List<Contratado> findContratado(String nome, UnidadeOrganizacional unidade) {
        String jpql = "select c from Contratado c where ";

        if (nome != null && !nome.isEmpty()) {
            jpql += "UPPER(c.nome) like UPPER(:nome) and ";
        }
        if (Utils.isNotEmpty(unidade)) {
            if (Utils.isNotEmpty(unidade.getId())) {
                jpql += "c.unidadeOrganizacional.id = :id and ";
            }
        }

        jpql += "c.ativo = true";

        Query query = entityManager.createQuery(jpql);

        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", "%" + nome.trim() + "%");
        }
        if (Utils.isNotEmpty(unidade)) {
            if (Utils.isNotEmpty(unidade.getId())) {
                query.setParameter("id", unidade.getId());
            }
        }
        return query.getResultList();
    }

    public List<Contratado> findPesquisa(Contratado contratado, Usuario user) {
        String sql = "select c from Contratado c where ";
        if (Utils.isNotEmpty(contratado.getNatureza())) {
            sql += "c.natureza = :nt and ";
        }
        if (Utils.isNotEmpty(contratado.getCnpj())) {
            sql += "c.cnpj = :cnpj and ";
        }
        if (Utils.isNotEmpty(contratado.getCpf())) {
            sql += "c.cpf = :cpf and ";
        }
        if (Utils.isNotEmpty(contratado.getEspecialidade())) {
            sql += "c.especialidade = :especialidade and ";
        }
        if (Utils.isNotEmpty(contratado.getNome())) {
            sql += "UPPER(c.nome) like  UPPER(:nome) and ";
        }
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                sql += " c.unidadeOrganizacional = :unidade and ";
            }
        }

        sql += "c.ativo = true";
        Query query = entityManager.createQuery(sql);

        if (Utils.isNotEmpty(contratado.getCnpj())) {
            query.setParameter("cnpj", contratado.getCnpj());
        }
        if (Utils.isNotEmpty(contratado.getCpf())) {
            query.setParameter("cpf", contratado.getCpf());
        }
        if (Utils.isNotEmpty(contratado.getEspecialidade())) {
            query.setParameter("especialidade", contratado.getEspecialidade());
        }
        if (Utils.isNotEmpty(contratado.getNome())) {
            query.setParameter("nome", "%" + contratado.getNome().trim() + "%");
        }
        if (Utils.isNotEmpty(contratado.getNatureza())) {
            query.setParameter("nt", contratado.getNatureza());
        }
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                query.setParameter("unidade", user.getUnidadeOrganizacional());
            }
        }
        return query.getResultList();
    }

    public boolean existCpf(String cpf, Usuario user) {
        String sql = "select c from Contratado c where c.ativo = true ";
        if (Utils.isNotEmpty(cpf)) {
            sql += "and c.cpf = :cpf ";
        }
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                sql += "and c.unidadeOrganizacional = :unidade ";
            }
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(cpf)) {
            query.setParameter("cpf", cpf);
        }
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                query.setParameter("unidade", user.getUnidadeOrganizacional());
            }
        }
        if (query.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean existCnpj(String cnpj, Usuario user) {
        String sql = "select c from Contratado c where c.ativo = true ";
        if (Utils.isNotEmpty(cnpj)) {
            sql += "and c.cnpj = :cnpj ";
        }
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                sql += "and c.unidadeOrganizacional = :unidade ";
            }
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(cnpj)) {
            query.setParameter("cnpj", cnpj);
        }
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }}
        if (query.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
