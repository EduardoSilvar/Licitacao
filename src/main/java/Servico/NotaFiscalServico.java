/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import Gerenciador.relatorioConfig;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import modelo.Acrescimo;
import modelo.Contratado;
import modelo.Contrato;
import modelo.ModeloDocumento;
import modelo.NotaFiscal;
import modelo.Repactuacao;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class NotaFiscalServico extends ServicoGenerico<NotaFiscal> implements Serializable {

    public NotaFiscalServico() {
        super(NotaFiscal.class);
    }

    public List<NotaFiscal> pesquisar(NotaFiscal nota, Usuario user) {
        String sql = "select n from NotaFiscal n where n.ativo = true ";
        if (Utils.isNotEmpty(nota.getContrato())) {
            sql += "and n.contrato = :contrato ";
        }
        if (Utils.isNotEmpty(nota.getContratado())) {
            sql += "and n.contratado = :contratado ";
        }
        if (Utils.isNotEmpty(nota.getValor())) {
            sql += "and n.valor = :valor";
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += "and n.unidadeOrganizacional = :unidade";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(nota.getContrato())) {
            query.setParameter("contrato", nota.getContrato());
        }
        if (Utils.isNotEmpty(nota.getContratado())) {
            query.setParameter("contratado", nota.getContratado());
        }
        if (Utils.isNotEmpty(nota.getValor())) {
            query.setParameter("valor", nota.getValor());
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();
    }

    public List<NotaFiscal> listNotaFiscalContrato(Contrato contrato) {
        String sql = "select n from NotaFiscal n where n.ativo = true ";
        if (Utils.isNotEmpty(contrato)) {
            if (Utils.isNotEmpty(contrato.getId())) {
                sql += "and n.contrato.id = :id ";
            }
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(contrato)) {
            if (Utils.isNotEmpty(contrato.getId())) {
                query.setParameter("id", contrato.getId());
            }
        }

        return query.getResultList();
    }

    public void imprimirModeloNotaFiscal(ModeloDocumento modelo, Contratado contratado, Contrato contrato, NotaFiscal nota, UnidadeOrganizacional orgao, List<Acrescimo> acrescimos, List<Repactuacao> apostilamento, Usuario user, List<NotaFiscal> notasFiscais) throws IOException, IOException, DocumentException {
        float esquerda = 10;

        float direita = 10;

        float cima = 10;

        float baixo = 10;

        Document document = new Document(PageSize.A4, esquerda, direita, cima, baixo);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        ServletOutputStream ouputStream = response.getOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, ouputStream);
        pdfWriter.getPageEvent();
        document.open();

        try {
            relatorioConfig pdf = new relatorioConfig(modelo.getTexto());
            pdf.converts(ouputStream, document, contratado, contrato, nota, orgao, acrescimos, apostilamento, user, notasFiscais);
        } catch (TransformerException ex) {
            Logger.getLogger(null).log(Level.SEVERE, null, ex);
        }
        response.setHeader(
                "Content-Disposition", "inline; filename=\"relatorio.pdf\"");
        response.setContentType(
                "application/pdf");
        facesContext.responseComplete();

        ouputStream.flush();

        ouputStream.close();

    }

    public void imprimirModeloNotaFiscal(ModeloDocumento modelo, Contratado contratado, Contrato contrato, NotaFiscal nota, UnidadeOrganizacional orgao) throws IOException, IOException, DocumentException {
        float esquerda = 10;

        float direita = 10;

        float cima = 10;

        float baixo = 30;

        Document document = new Document(PageSize.A4, esquerda, direita, cima, baixo);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        ServletOutputStream ouputStream = response.getOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, ouputStream);
        pdfWriter.getPageEvent();
        document.open();

        try {
            relatorioConfig pdf = new relatorioConfig(modelo.getTexto());
            pdf.converts(ouputStream, document, contratado, contrato, nota, orgao);
        } catch (TransformerException ex) {
            Logger.getLogger(null).log(Level.SEVERE, null, ex);
        }
        response.setHeader(
                "Content-Disposition", "inline; filename=\"relatorio.pdf\"");
        response.setContentType(
                "application/pdf");
        facesContext.responseComplete();

        ouputStream.flush();

        ouputStream.close();

    }

}
