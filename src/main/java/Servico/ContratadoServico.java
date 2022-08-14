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
            jpql += "lower(c.nome) like lower(:nome) and ";
        }

        jpql += "c.ativo = true";

        Query query = entityManager.createQuery(jpql);

        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", "%" + nome + "%");
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
            sql += "c.nome = :nome and ";
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += " c.unidadeOrganizacional = :unidade and ";
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
            query.setParameter("nome", contratado.getNome());
        }
        if (Utils.isNotEmpty(contratado.getNatureza())) {
            query.setParameter("nt", contratado.getNatureza());
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();
    }

    public boolean existCpf(String cpf) {
        String sql = "select c from Contratado c where c.ativo = true ";
        if (Utils.isNotEmpty(cpf)) {
            sql += "and c.cpf = :cpf ";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(cpf)) {
            query.setParameter("cpf", cpf);
        }
        if (query.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
