/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Contratado;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class ContratadoServico extends ServicoGenerico<Contratado> {

    public ContratadoServico() {
        super(Contratado.class);
    }

    public List<Contratado> findPesquisa(Contratado contratado) {
        String sql = "select c from Contratado c where ";
        if (Utils.isNotEmpty(contratado.getCnpj())) {
            sql += "c.cnpj = :cnpj and ";
        }
        if (Utils.isNotEmpty(contratado.getCpf())) {
            sql += "c.cpf = :cpf and ";
        }
        if (Utils.isNotEmpty(contratado.getEspecialidade())) {
            sql += "c.especialidade = :especialidade and ";
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
        return query.getResultList();
    }

}
