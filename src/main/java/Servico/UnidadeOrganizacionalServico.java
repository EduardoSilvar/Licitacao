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
import modelo.UnidadeOrganizacional;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class UnidadeOrganizacionalServico extends ServicoGenerico<UnidadeOrganizacional> implements Serializable {

    public UnidadeOrganizacionalServico() {
        super(UnidadeOrganizacional.class);
    }

    public List<UnidadeOrganizacional> pesquisar(UnidadeOrganizacional unidade) {
        String sql = "select u from UnidadeOrganizacional u where u.ativo = true";
        if (Utils.isNotEmpty(unidade.getNome())) {
            sql += " and UPPER(u.nome) LIKE UPPER(:nome)";
        }
        if (Utils.isNotEmpty(unidade.getNomeRepresentante())) {
            sql += " and UPPER(u.NomeRepresentante) LIKE UPPER(:nomerepresentante)";
        }
        if (Utils.isNotEmpty(unidade.getCnpj())) {
            sql += " and u.cnpj = :cnpj";
        }
        if (Utils.isNotEmpty(unidade.getEndereco().getBairro())) {
            sql += " and u.endereco.bairro = :bairro";
        }
        if (Utils.isNotEmpty(unidade.getEndereco().getCidade())) {
            sql += " and u.endereco.cidade = :cidade";
        }

        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(unidade.getNome())) {
            query.setParameter("nome","%"+ unidade.getNome().trim()+"%");
        }
        if (Utils.isNotEmpty(unidade.getNomeRepresentante())) {
            query.setParameter("nomerepresentante", "%"+unidade.getNomeRepresentante().trim()+"%");
        }
        if (Utils.isNotEmpty(unidade.getCnpj())) {
            query.setParameter("cnpj", unidade.getCnpj());
        }
        if (Utils.isNotEmpty(unidade.getEndereco().getBairro())) {
            query.setParameter("bairro", unidade.getEndereco().getBairro());
        }
        if (Utils.isNotEmpty(unidade.getEndereco().getCidade())) {
            query.setParameter("cidade", unidade.getEndereco().getCidade());
        }

        return query.getResultList();
    }

    public List<UnidadeOrganizacional> FindUnidades(UnidadeOrganizacional unidade) {
        String sql = "select e from UnidadeOrganizacional e where e.ativo = true ";
        if (Utils.isNotEmpty(unidade)) {
            sql += "and e = :unidade";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(unidade)) {
            query.setParameter("unidade", unidade);
        }
        return query.getResultList();

    }
}
