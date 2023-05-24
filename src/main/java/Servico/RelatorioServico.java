/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.Relatorio;
import org.apache.commons.io.IOUtils;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class RelatorioServico extends ServicoGenerico<Relatorio> {

    public RelatorioServico() {
        super(Relatorio.class);
    }

    public void salvar(Relatorio relatorio) {

        if (relatorio.getId() == null) {
            Save(relatorio);
        } else {
            Update(relatorio);
        }

    }

    public void remover(Relatorio relatorio) {
        relatorio.setAtivo(false);
        Update(relatorio);
    }

    @Override
    public Relatorio find(Long id) {
        return super.find(id);
    }

    public List<Relatorio> findCabecalhoRequerimento(String nome) {

        String sql = "select e from Relatorio e where ";

        if (nome != null && !nome.isEmpty()) {
            sql += "lower(e.nome) like lower(:nome) and ";
        }
        sql += "e.ativo = true ";

        Query query = getEntityManager().createQuery(sql);

        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", "%" + nome.trim() + "%");
        }
        return query.getResultList();
    }

    public List<Relatorio> findRodapeRequerimento(String nome) {

        String sql = "select e from Relatorio e where ";

        if (nome != null && !nome.isEmpty()) {
            sql += "lower(e.nome) like lower(:nome) and ";
        }
        sql += "e.ativo = true ";

        Query query = getEntityManager().createQuery(sql);

        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", "%" + nome.trim() + "%");
        }

        return query.getResultList();

    }

    public List<Relatorio> find(Relatorio relatorio) throws Exception {

        String sql = "select r from Relatorio r where ";

        if (!Utils.isEmpty(relatorio.getNome())) {
            sql += "lower(r.nome) like lower(:nome) and ";
        }

        sql += "r.ativo = true and r.tipo = :tipo";

        Query query = getEntityManager().createQuery(sql);

        if (!Utils.isEmpty(relatorio.getNome())) {
            query.setParameter("nome", "%" + relatorio.getNome().trim() + "%");
        }

        List<Relatorio> result = query.getResultList();

        if (result == null || result.isEmpty()) {
            throw new Exception("Nenhum registro encontrado");
        }

        return result;

    }

}
