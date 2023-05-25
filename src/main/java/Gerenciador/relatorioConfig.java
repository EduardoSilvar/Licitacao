/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Macro.ArquivoEstilizacaoPDF;
import Macro.HeaderFooterPageEvent;
import Macro.MacroNotaFiscal;
import static Macro.MacroNotaFiscal.MacroNotaFiscal;
import Macro.MacroNotaFiscalGestor;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.ImageProvider;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import modelo.Acrescimo;
import modelo.CabecalhoRodape;
import modelo.Contratado;
import modelo.Contrato;
import modelo.ModeloDocumento;
import modelo.NotaFiscal;
import modelo.Repactuacao;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
public class relatorioConfig {

    public static final String CSS = "resources/modelo-documento.css";

    private final InputStream is;
    private ImageProvider imProvider;
    private CSSResolver cssResolver;

    public relatorioConfig(InputStream is) throws TransformerConfigurationException, TransformerException {
        this.is = is;
        //cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
        this.cssResolver = getCssResolver();
        this.imProvider = new Base64ImageProvider();
    }

    public relatorioConfig(String html) throws TransformerException {
        this(new ByteArrayInputStream(html.getBytes()));

    }

    public void setImageProvider(ImageProvider imProvider) {
        this.imProvider = imProvider;
    }

    public void addCss(String css) throws CssResolverException {
        cssResolver.addCss(css, Boolean.TRUE);
    }

    public void convertNotaFiscal(OutputStream file, String texto, ModeloDocumento modelo) throws DocumentException, IOException {

//           String textoPDf = "";
//        if (Utils.isNotEmpty(modelo)) {
//            if (Utils.isNotEmpty(modelo.getTexto())) {
//                textoPDf = ArquivoEstilizacaoPDF.corpoPDF(modelo.getTexto());
//            } else {
//                Msg.messagemError("O modelo de documento não tem corpo !");
//            }
//        } else {
//            Msg.messagemError("Não tem modelo de documento !");
//        }
        String textoPDF = "";
        if (Utils.isNotEmpty(texto)) {
            textoPDF = ArquivoEstilizacaoPDF.corpoPDF(texto);

        } else {
            Msg.messagemError("O modelo de documento não tem corpo !");
        }
        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(file);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A4);
        HeaderFooterPageEvent handler = new HeaderFooterPageEvent();
//        handler = aplicarCabecahoRodape(modelo);

        // Adicione o Evento de Cabeçalho e Rodapé ao PdfDocument
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, handler);

        com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDoc);
//        doc = aplicarMargensDocumento(doc, modelo);

        List<IElement> elements = HtmlConverter.convertToElements(textoPDF);

        // Adiciona os elementos ao Document
        for (IElement element : elements) {
            doc.add((IBlockElement) element);

        }

        doc.close();

    }

    public HeaderFooterPageEvent aplicarCabecahoRodape(CabecalhoRodape cabecalho, CabecalhoRodape rodape) {
        HeaderFooterPageEvent handler = new HeaderFooterPageEvent();

        if (Utils.isNotEmpty(cabecalho)) {
            if (Utils.isNotEmpty(cabecalho.getMargemEsquerda())) {
                handler.setMargemEsquerdaCabecalho(cabecalho.getMargemEsquerda());
            }
            if (Utils.isNotEmpty(cabecalho.getMargemDireita())) {
                handler.setMargemDireitaCabecalho(cabecalho.getMargemDireita());
            }
            if (Utils.isNotEmpty(cabecalho.getMargemTopo())) {
                handler.setMargemTopoCabecalho(cabecalho.getMargemTopo());
            }
            if (Utils.isNotEmpty(cabecalho.getMargemBaixo())) {
                handler.setMargemBaixoCabecalho(cabecalho.getMargemBaixo());
            }
            if (Utils.isNotEmpty(cabecalho.getTexto())) {
                handler.setTextoCabecalho(cabecalho.getTexto());
            }
            if (Utils.isNotEmpty(cabecalho.isImagemEsquerda())) {
                handler.setImagemCabecalhoEsquerda(cabecalho.isImagemEsquerda());
            }
            if (Utils.isNotEmpty(cabecalho.getImagem())) {
                if (Utils.isNotEmpty(cabecalho.getImagem().getCaminho())) {
                    handler.setUrlImagemCabecalho(cabecalho.getImagem().getUrl() + cabecalho.getImagem().getNome());
                }
            }
            if (Utils.isNotEmpty(cabecalho.getFonSize())) {
                handler.setFonSizeCabecalho(cabecalho.getFonSize());
            }
            if (Utils.isNotEmpty(cabecalho.getAlturaImagem())) {
                handler.setAlturaImagemCabecalho(cabecalho.getAlturaImagem());
            }
            if (Utils.isNotEmpty(cabecalho.getMargemDireitaImagem())) {
                handler.setMargemDireitaImagemCabecalho(cabecalho.getMargemDireitaImagem());
            }
            if (Utils.isNotEmpty(cabecalho.getMargemEsquerdaImagem())) {
                handler.setMargemEsquerdaCabecalho(cabecalho.getMargemEsquerdaImagem());
            }
            if (Utils.isNotEmpty(cabecalho.getMargemEsquerdaTexto())) {
                handler.setMargemEsquerdaTextoCabecalho(cabecalho.getMargemEsquerdaTexto());
            }
            if (Utils.isNotEmpty(cabecalho.getMargemDireitaTexto())) {
                handler.setMargemDireitaTextoCabecalho(cabecalho.getMargemDireitaTexto());
            }
        }
        if (Utils.isNotEmpty(rodape)) {
            if (Utils.isNotEmpty(rodape.getMargemEsquerda())) {
                handler.setMargemEsquerdaRodape(rodape.getMargemEsquerda());
            }
            if (Utils.isNotEmpty(rodape.getMargemDireita())) {
                handler.setMargemDireitarodape(rodape.getMargemDireita());
            }
            if (Utils.isNotEmpty(rodape.getMargemTopo())) {
                handler.setMargemTopoRodape(rodape.getMargemTopo());
            }
            if (Utils.isNotEmpty(rodape.getMargemBaixo())) {
                handler.setMargemBaixoRodape(rodape.getMargemBaixo());
            }
            if (Utils.isNotEmpty(rodape.getTexto())) {
                handler.setTextoRodapé(rodape.getTexto());
            }
            if (Utils.isNotEmpty(rodape.isImagemEsquerda())) {
                handler.setImagemRodapeEsquerda(rodape.isImagemEsquerda());
            }
            if (Utils.isNotEmpty(rodape.getImagem())) {
                if (Utils.isNotEmpty(rodape.getImagem().getCaminho())) {
                    handler.setUrlImagemRodape(rodape.getImagem().getUrl() + rodape.getImagem().getNome());
                }
            }
            if (Utils.isNotEmpty(rodape.getFonSize())) {
                handler.setFonSizeRodape(rodape.getFonSize());
            }
            if (Utils.isNotEmpty(rodape.getAlturaImagem())) {
                handler.setAlturaImagemRodape(rodape.getAlturaImagem());
            }
            if (Utils.isNotEmpty(rodape.getMargemDireitaImagem())) {
                handler.setMargemDireitaImagemRodape(rodape.getMargemDireitaImagem());
            }
            if (Utils.isNotEmpty(rodape.getMargemEsquerdaImagem())) {
                handler.setMargemEsquerdaRodape(rodape.getMargemEsquerdaImagem());
            }
            if (Utils.isNotEmpty(rodape.getMargemEsquerdaTexto())) {
                handler.setMargemEsquerdaTextoRodape(rodape.getMargemEsquerdaTexto());
            }
            if (Utils.isNotEmpty(rodape.getMargemDireitaTexto())) {
                handler.setMargemDireitaTextoRodape(rodape.getMargemDireitaTexto());
            }

        }

        return handler;
    }

    public void convert(OutputStream file, Document document, List<String> textos) throws DocumentException, IOException {

        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, file);
        // step 3
        document.open();
        // step 4        

//         Image imagem = Image.getInstance("/home/foxdell/qrcode.png");
//         
//         imagem.setAlignment(Image.ALIGN_LEFT);
//         document.add(imagem);
        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        if (imProvider != null) {
            htmlContext.setImageProvider(imProvider);

        }

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        // XML Worker
        for (String texto : textos) {
            XMLWorker worker = new XMLWorker(css, true);
            XMLParser p = new XMLParser(worker);
            p.parse(new StringReader(texto));
            document.newPage();

        }

//        XMLParser t = new XMLParser(worker);
//        t.parse(new StringReader(textos.get(0)));
        // step 5
        document.close();
    }

    public void convert(OutputStream file, Document document) throws DocumentException, IOException {

        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, file);
        // step 3
        document.open();
        document.newPage();
        // step 4        
//        PdfPTable table = new PdfPTable(1);
//        table.setWidthPercentage(90);
//        PdfPCell header = new PdfPCell(new Paragraph("DADOS DO LEGITIMADO", new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.BLACK)));
//        header.setColspan(3);
//        header.setBackgroundColor(new BaseColor(204, 203, 200));
//        header.setHorizontalAlignment(1);
//        table.addCell(header);
//        table.addCell("nome: ");
//        table.addCell("nome da mãe: ");
//        table.addCell("nome do pai: ");
//        table.addCell("telefone: ");
//        document.add(table);
//         Image imagem = Image.getInstance("/home/foxdell/qrcode.png");
//         
//         imagem.setAlignment(Image.ALIGN_LEFT);
//         document.add(imagem);
        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        if (imProvider != null) {
            htmlContext.setImageProvider(imProvider);

        }

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
//        document = macroRodapéMemorial.macroMemorial(document, new Imovel());
        document.open();
        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(is);

        // step 5
        document.close();
    }

    public void converts(OutputStream file, Document document, Contratado contratado, Contrato contrato, NotaFiscal nota, UnidadeOrganizacional orgao, List<Acrescimo> acrescimos, List<Repactuacao> apostilamentos, Usuario usuario, List<NotaFiscal> notasFiscais) throws DocumentException, IOException {

        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(file);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A4);
        HeaderFooterPageEvent handler = new HeaderFooterPageEvent();
        if (Utils.isNotEmpty(orgao.getConfiguracao())) {
            if (Utils.isNotEmpty(orgao.getConfiguracao())) {
                if (Utils.isNotEmpty(orgao.getConfiguracao().getCabecalhoNotaFiscal()) && Utils.isNotEmpty(orgao.getConfiguracao().getRodapeNotaFiscal())) {
                    handler = aplicarCabecahoRodape(orgao.getConfiguracao().getCabecalhoNotaFiscal(), orgao.getConfiguracao().getRodapeNotaFiscal());

                }
            }
        }

        // Adicione o Evento de Cabeçalho e Rodapé ao PdfDocument
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, handler);

        com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDoc);
//        doc = aplicarMargensDocumento(doc, modelo);

        doc = MacroNotaFiscalGestor.MacroNotaFiscal(doc, contratado, contrato, nota, orgao, acrescimos, apostilamentos, usuario, notasFiscais);

        doc.close();

    }

    public void converts(OutputStream file, Document document, Contratado contratado, Contrato contrato, NotaFiscal nota, UnidadeOrganizacional orgao) throws DocumentException, IOException {
//document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/opt/Licitacao/uploads/2509202211291805571_memorial.pdf"));
        // step 3
        document.open();
        // step 4        

        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        if (imProvider != null) {
            htmlContext.setImageProvider(imProvider);

        }

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(is);
        document = MacroNotaFiscal.MacroNotaFiscal(document, contratado, contrato, nota, orgao);

        // step 5
        document.close();
    }

    public void convert(File os) throws FileNotFoundException, IOException, DocumentException {
        try ( OutputStream out = new BufferedOutputStream(new FileOutputStream(os))) {
            convert(out, null);
        }
    }

    static class Base64ImageProvider extends AbstractImageProvider {

        @Override
        public Image retrieve(String src) {
            int pos = src.indexOf("base64,");
            try {
                if (src.startsWith("data") && pos > 0) {
                    byte[] img = Base64.decode(src.substring(pos + 7));
                    return Image.getInstance(img);
                } else {
                    return Image.getInstance(src);
                }
            } catch (BadElementException | IOException ex) {
                return null;
            }
        }

        @Override
        public String getImageRootPath() {
            return null;
        }
    }

    private CSSResolver getCssResolver() {

        CSSResolver cr = null;

        try {

            String cssFileName = FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath(CSS);

            cr = new StyleAttrCSSResolver();
            CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(cssFileName));
            cr.addCss(cssFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(relatorioConfig.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cr;

    }

}
