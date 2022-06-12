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

    public List<Setor> pesquisar(Setor setor) {
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
        return query.getResultList();
    }

}