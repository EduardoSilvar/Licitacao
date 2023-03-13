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
import modelo.Setor;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class SetorServico extends ServicoGenerico<Setor> implements Serializable {

    public SetorServico() {
        super(Setor.class);
    }

    public List<Setor> findSetor(String nome) {
        String jpql = "select c from Setor c where ";

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

    public List<Setor> findSetor(String nome, UnidadeOrganizacional unidade) {
        String jpql = "select c from Setor c where ";

        if (nome != null && !nome.isEmpty()) {
            jpql += "lower(c.nome) like lower(:nome) and ";
        }
        if (Utils.isNotEmpty(unidade)) {
            if (Utils.isNotEmpty(unidade.getId())) {
                jpql += "c.unidadeOrganizacional.id = :id and ";
            }
        }
        jpql += "c.ativo = true";

        Query query = entityManager.createQuery(jpql);
        if (Utils.isNotEmpty(unidade)) {
            if (Utils.isNotEmpty(unidade.getId())) {
                query.setParameter("id", unidade.getId());
            }
        }
        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", "%" + nome + "%");
        }

        return query.getResultList();
    }

    public List<Setor> pesquisar(Setor setor, Usuario user) {
        String sql = "select s from Setor s where s.ativo = true";
        if (Utils.isNotEmpty(setor.getNome())) {
            sql += " and s.nome = :nome";
        }
        if (Utils.isNotEmpty(setor.getNomeResponsavel())) {
            sql += " and s.nomeResponsavel = :nomeres";
        }
        if (Utils.isNotEmpty(setor.getCpfResponsavel())) {
            sql += " and s.cpfResponsavel = :cpf";
        }
        if (Utils.isNotEmpty(setor.getTelefone())) {
            sql += " and s.telefone = :telefone";
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += " and s.unidadeOrganizacional = :unidade";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(setor.getNome())) {
            query.setParameter("nome", setor.getNome());
        }
        if (Utils.isNotEmpty(setor.getNomeResponsavel())) {
            query.setParameter("nomeres", setor.getNomeResponsavel());
        }
        if (Utils.isNotEmpty(setor.getCpfResponsavel())) {
            query.setParameter("cpf", setor.getCpfResponsavel());
        }
        if (Utils.isNotEmpty(setor.getTelefone())) {
            query.setParameter("telefone", setor.getTelefone());
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();
    }

}
