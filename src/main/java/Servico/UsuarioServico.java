/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import br.com.caelum.stella.validation.CPFValidator;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Usuario;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class UsuarioServico extends ServicoGenerico<Usuario> implements Serializable {

    public UsuarioServico() {
        super(Usuario.class);
    }

    public List<Usuario> pesquisar(Usuario user) {
        String sql = "select u from Usuario u where u.ativo = true";
        if (Utils.isNotEmpty(user.getEmail())) {
            sql += " and u.email = :email";
        }
        if (Utils.isNotEmpty(user.getNome())) {
            sql += " and u.nome = :nome";
        }
        Query query = getEntityManager().createQuery(sql);

        if (Utils.isNotEmpty(user.getEmail())) {
            query.setParameter("email", user.getEmail());
        }
        if (Utils.isNotEmpty(user.getNome())) {
            query.setParameter("nome", user.getNome());
        }
        return query.getResultList();
    }

    public boolean existEmail(Usuario user) {
        String sql = "select u from Usuario u where u.ativo = true ";
        if (Utils.isNotEmpty(user.getEmail())) {
            sql += "and u.email = :email";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(user.getEmail())) {
            query.setParameter("email", user.getEmail());
        }
        if (query.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean existLogin(Usuario user) {
        String sql = "select u from Usuario u where u.ativo = true ";
        if (Utils.isNotEmpty(user.getLogin())) {
            sql += "and u.login = :login";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(user.getLogin())) {
            query.setParameter("login", user.getLogin());
        }
        if (query.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean existCpf(Usuario user) {
        String sql = "select u from Usuario u where u.ativo = true ";
        if (Utils.isNotEmpty(user.getCpf())) {
            sql += "and u.cpf = :cpf";
        }
        Query query = getEntityManager().createQuery(sql);

        if (Utils.isNotEmpty(user.getCpf())) {
            query.setParameter("cpf", user.getCpf());
        }
        if (query.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean valida(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
