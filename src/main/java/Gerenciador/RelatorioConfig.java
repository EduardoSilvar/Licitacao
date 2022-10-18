/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Macro.MacroNotaFiscal;
import static Macro.MacroNotaFiscal.MacroNotaFiscal;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import modelo.Contratado;
import modelo.Contrato;
import modelo.NotaFiscal;

/**
 *
 * @author eduardo
 */
public class RelatorioConfig {

    public static final String CSS = "resources/modelo-documento.css";

    private final InputStream is;
    private ImageProvider imProvider;
    private CSSResolver cssResolver;

    public RelatorioConfig(InputStream is) throws TransformerConfigurationException, TransformerException {
        this.is = is;
        //cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
        this.cssResolver = getCssResolver();
        this.imProvider = new Base64ImageProvider();
    }

    public RelatorioConfig(String html) throws TransformerException {
        this(new ByteArrayInputStream(html.getBytes()));

    }

    public void setImageProvider(ImageProvider imProvider) {
        this.imProvider = imProvider;
    }

    public void addCss(String css) throws CssResolverException {
        cssResolver.addCss(css, Boolean.TRUE);
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
        PdfWriter writer = PdfWriter.getInstance(document,file);
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

    public void converts(OutputStream file, Document document, Contratado contratado, Contrato contrato, NotaFiscal nota) throws DocumentException, IOException {
//document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("/opt/Licitacao/uploads/2509202211291805571_memorial.pdf"));
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
        document = MacroNotaFiscal.MacroNotaFiscal(document, contratado, contrato, nota);

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
            Logger.getLogger(RelatorioConfig.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cr;

    }

}
