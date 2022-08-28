/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Endereco;
import modelo.Grupo;

/**
 *
 * @author eduardo
 */
@Stateless
public class GrupoServico extends ServicoGenerico<Grupo> implements Serializable {

    public GrupoServico() {
        super(Grupo.class);
    }

    public List<Grupo> findGrupos(String nome) {
        String jpql = "select c from Grupo c ";

        if (nome != null && !nome.isEmpty()) {
            jpql += "where lower(c.nome) like lower(:nome) ";
        }

        Query query = entityManager.createQuery(jpql);

        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", "%" + nome + "%");
        }

        return query.getResultList();
    }

}
