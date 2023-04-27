/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import Enum.StatusContrato;
import Gerenciador.RelatorioConfig;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import modelo.Anexo;
import modelo.Contratado;
import modelo.Contrato;
import modelo.ContratoVo;
import modelo.ModeloDocumento;
import modelo.Setor;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class ContratoServico extends ServicoGenerico<Contrato> implements Serializable {

    public ContratoServico() {
        super(Contrato.class);
    }

    public List<Contrato> buscarContratosPagos(Usuario user) {

        String sql = "select c from Contrato c where c.ativo = true and c.status = :status ";
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += "and c.unidadeOrganizacional = :unidade ";
        }
        Query query = getEntityManager().createQuery(sql);
        query.setParameter("status", StatusContrato.PAGO);
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();
    }

    public List<ContratoVo> buscarContratos(Usuario user) {

        String sql = "select new modelo.ContratoVo(c.status, count(c)) "
                + "from Contrato c ";
        sql += "where c.ativo = true ";
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                sql += "and c.unidadeOrganizacional = :unidade ";
            }
        }
        sql += "group by c.status";

        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(user)) {

            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                query.setParameter("unidade", user.getUnidadeOrganizacional());
            }
        }
        return query.getResultList();
    }

    public List<ContratoVo> buscarTipoContrato(Usuario user) {

        String sql = "select new modelo.ContratoVo(c.tipoContrato, count(c)) "
                + "from Contrato c ";
        sql += "where c.ativo = true ";
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                sql += "and c.unidadeOrganizacional = :unidade ";
            }
        }
        sql += "group by c.tipoContrato";

        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                query.setParameter("unidade", user.getUnidadeOrganizacional());
            }
        }
        return query.getResultList();
    }

    public void abrirPDF() throws FileNotFoundException, DocumentException, IOException {

//        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
//        event = configuracaoDocumento(modelo);
        ModeloDocumento modelo = new ModeloDocumento();
        modelo.setTexto("");
        Document document = new Document();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        ServletOutputStream ouputStream = response.getOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(new File("/opt/Licitacao/uploads/2509202201163507033_mapamemorial.png")));
//        pdfWriter.setPageEvent(event);
//        pdfWriter.getPageEvent();
//        document.open();

        try {
            RelatorioConfig pdf = new RelatorioConfig(modelo.getTexto());
            pdf.convert(new File("/opt/Licitacao/uploads/LICITACAO.PDF"));
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

    public List<ContratoVo> buscarContratoSetor(Setor setor, Contratado contratado, Usuario user) {

        String sql = "select new modelo.ContratoVo(c.status, count(c)) "
                + "from Contrato c ";
        sql += "where c.ativo = true ";
        if (Utils.isNotEmpty(setor)) {
            sql += "and c.setor = :setor ";
        }
        if (Utils.isNotEmpty(contratado)) {
            sql += "and c.contratado = :contratado ";
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += "and c.unidadeOrganizacional = :unidade ";
        }
        sql += "group by c.status";

        Query query = getEntityManager().createQuery(sql);

        if (Utils.isNotEmpty(setor)) {
            query.setParameter("setor", setor);
        }
        if (Utils.isNotEmpty(contratado)) {
            query.setParameter("contratado", contratado);
        }
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();
    }

    public List<Contrato> buscarValorContrato(Setor setor, Usuario user) {
        String sql = "select c from Contrato c where c.ativo = true ";
        if (Utils.isNotEmpty(setor)) {
            sql += "and c.setor = :setor ";
        }
        if (Utils.isNotEmpty(user)) {
            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                sql += "and c.unidadeOrganizacional = :unidade ";
            }
        }
        Query query = getEntityManager().createQuery(sql);

        if (Utils.isNotEmpty(setor)) {
            query.setParameter("setor", setor);
        }
        if (Utils.isNotEmpty(user)) {

            if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
                query.setParameter("unidade", user.getUnidadeOrganizacional());
            }
        }
        return query.getResultList();
    }

    public List<Contrato> findContrato(String nome) {
        String jpql = "select c from Contrato c where ";

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

    public List<Contrato> findContrato(String nome, UnidadeOrganizacional unidade) {
        String jpql = "select c from Contrato c where ";

        if (nome != null && !nome.isEmpty()) {
            jpql += "UPPER(c.nome) like UPPER(:nome) and ";
        }
        if (Utils.isNotEmpty(unidade)) {
            jpql += "c.unidadeOrganizacional.id = :unidade and ";
        }
        jpql += "c.ativo = true";

        Query query = entityManager.createQuery(jpql);

        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", "%" + nome.trim() + "%");
        }
        if (Utils.isNotEmpty(unidade)) {
            query.setParameter("unidade", unidade.getId());
        }
        return query.getResultList();
    }

    public List<Contrato> findPesquisa() {
        String sql = "select c from Contrato c where c.ativo = true";
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }

    public List<Contrato> findAllUnidade(UnidadeOrganizacional unidade) {
        String sql = "select c from Contrato c where ";

        if (Utils.isNotEmpty(unidade)) {
            sql += "c.unidadeOrganizacional.id = :unidade and ";
        }
        sql += "c.ativo = true";

        Query query = entityManager.createQuery(sql);

        if (Utils.isNotEmpty(unidade)) {
            query.setParameter("unidade", unidade.getId());
        }
        return query.getResultList();
    }

    public List<Contrato> findPesquisa(Contrato contrato, UnidadeOrganizacional unidade) {
        String sql = "select c from Contrato c where ";
        if (Utils.isNotEmpty(contrato.getContratado())) {
            sql += "c.contratado = :contratado and ";
        }
        if (Utils.isNotEmpty(unidade)) {
            sql += "c.unidadeOrganizacional = :unidade and ";
        }
        if (Utils.isNotEmpty(contrato.getNumeroProcesso())) {
            sql += "c.NumeroProcesso = :numero and ";
        }
        if (Utils.isNotEmpty(contrato.getStatus())) {
            sql += "c.status = :status and ";
        }
        if (Utils.isNotEmpty(contrato.getDataFinal())) {
            sql += "c.dataFinal = :dataf and ";
        }
        if (Utils.isNotEmpty(contrato.getDataInicio())) {
            sql += "c.dataInicio = :datai and ";
        }
        if (Utils.isNotEmpty(contrato.getSetor())) {
            sql += "c.setor = :setor and ";
        }
        sql += "c.ativo = true";

        Query query = entityManager.createQuery(sql);
        if (Utils.isNotEmpty(contrato.getContratado())) {
            query.setParameter("contratado", contrato.getContratado());
        }
        if (Utils.isNotEmpty(contrato.getStatus())) {
            query.setParameter("status", contrato.getStatus());
        }
        if (Utils.isNotEmpty(contrato.getDataFinal())) {
            query.setParameter("dataf", contrato.getDataFinal());
        }
        if (Utils.isNotEmpty(unidade)) {
            query.setParameter("unidade", unidade);
        }
        if (Utils.isNotEmpty(contrato.getDataInicio())) {
            query.setParameter("datai", contrato.getDataInicio());
        }
        if (Utils.isNotEmpty(contrato.getNumeroProcesso())) {
            query.setParameter("numero", contrato.getNumeroProcesso());
        }
        if (Utils.isNotEmpty(contrato.getSetor())) {
            query.setParameter("setor", contrato.getSetor());
        }
        return query.getResultList();
    }

    public boolean existNumero(String numero) {
        String sql = "select c from Contrato c where c.ativo = true ";
        if (Utils.isNotEmpty(numero)) {
            sql += "and c.NumeroContrato = :numero ";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(numero)) {
            query.setParameter("numero", numero);
        }
        if (query.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Contratado> contratados(Usuario user) {
        String sql = "select c Contratado c where c.ativo = true";
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            sql += " and c.unidadeOrganizacional = :unidade ";
        }
        Query query = entityManager.createQuery(sql);
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            query.setParameter("unidade", user.getUnidadeOrganizacional());
        }
        return query.getResultList();

    }

}
