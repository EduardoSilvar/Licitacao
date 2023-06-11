/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Macro;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import util.Utils;

/**
 *
 * @author eduardo
 */
public class HeaderFooterPageEvent implements IEventHandler {

    private int margemTopoCabecalho;
    private int margemBaixoCabecalho;
    private int margemEsquerdaCabecalho;
    private int margemDireitaCabecalho;
    private int margemDireitaTextoCabecalho;
    private int margemEsquerdaTextoCabecalho;

    private int margemTopoRodape;
    private int margemBaixoRodape;
    private int margemEsquerdaRodape;
    private int margemDireitarodape;
    private int margemDireitaTextoRodape;
    private int margemEsquerdaTextoRodape;

    private String textoCabecalho;
    private String textoRodapé;

    private String urlImagemCabecalho;
    private String urlTimbrado;

    private int alturaImagemCabecalho;
    private boolean imagemCabecalhoEsquerda;
    private int margemDireitaImagemCabecalho;
    private int margemEsquerdaImagemCabecalho;

    private String urlImagemRodape;
    private int alturaImagemRodape;
    private boolean imagemRodapeEsquerda;
    private int margemDireitaImagemRodape;
    private int margemEsquerdaImagemRodape;

    private int fonSizeCabecalho;

    private int fonSizeRodape;

    @Override
    public void handleEvent(Event event) {
        try {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            com.itextpdf.kernel.geom.Rectangle pageSize = page.getPageSize();

            Table table = new Table(1);
            if (Utils.isNotEmpty(margemBaixoCabecalho)) {
                table.setPaddingBottom(margemBaixoCabecalho);
            } else {
                table.setPaddingBottom(10);
            }
            if (Utils.isNotEmpty(margemTopoCabecalho)) {
                table.setPaddingTop(margemTopoCabecalho);
            } else {
                table.setPaddingBottom(10);
            }
            if (Utils.isNotEmpty(margemEsquerdaCabecalho)) {
                table.setPaddingLeft(margemEsquerdaCabecalho);
            } else {
                table.setPaddingBottom(10);
            }
            if (Utils.isNotEmpty(margemDireitaCabecalho)) {
                table.setPaddingRight(margemDireitaCabecalho);
            } else {
                table.setPaddingRight(10);
            }
            if (Utils.isNotEmpty(urlTimbrado)) {
                ImageData imageData = ImageDataFactory.create(urlTimbrado);
                Image imagemCabecalho = new Image(imageData);
                pdfCanvas.addImage(imageData, pageSize, true);
            }
            // Criando o cabeçalho do PDF
            if (Utils.isNotEmpty(textoCabecalho)) {
                if (Utils.isNotEmpty(urlImagemCabecalho)) {
                    table = new Table(2);

                    if (Utils.isNotEmpty(margemBaixoCabecalho)) {
                        table.setPaddingBottom(margemBaixoCabecalho);
                    } else {
                        table.setPaddingBottom(10);
                    }
                    if (Utils.isNotEmpty(margemTopoCabecalho)) {
                        table.setPaddingTop(margemTopoCabecalho);
                    } else {
                        table.setPaddingTop(10);
                    }
                    if (Utils.isNotEmpty(margemEsquerdaCabecalho)) {
                        table.setPaddingLeft(margemEsquerdaCabecalho);
                    } else {
                        table.setPaddingLeft(10);
                    }
                    if (Utils.isNotEmpty(margemDireitaCabecalho)) {
                        table.setPaddingRight(margemDireitaCabecalho);
                    } else {
                        table.setPaddingRight(10);
                    }
                    table.setBorder(Border.NO_BORDER);
                    if (Utils.isNotEmpty(margemTopoCabecalho)) {
                        table.setMarginTop(margemTopoCabecalho);
                    }
                    table.setFontSize(9);
                    if (imagemCabecalhoEsquerda) {

                        //Configuração da imagem do cabeçalho 
                        ImageData imageData = ImageDataFactory.create(urlImagemCabecalho);
                        Image imagemCabecalho = new Image(imageData);

                        if (Utils.isNotEmpty(alturaImagemCabecalho)) {
                            imagemCabecalho.setHeight(alturaImagemCabecalho);
                        } else {
                            imagemCabecalho.setHeight(50);
                        }
                        Cell celulaImagemCabecalho = new Cell();
                        celulaImagemCabecalho.add(imagemCabecalho);
                        celulaImagemCabecalho.setBorder(Border.NO_BORDER);

                        if (Utils.isNotEmpty(margemEsquerdaImagemCabecalho)) {
                            celulaImagemCabecalho.setPaddingLeft(margemEsquerdaImagemCabecalho);
                        }
                        if (Utils.isNotEmpty(margemDireitaImagemCabecalho)) {
                            celulaImagemCabecalho.setPaddingRight(margemDireitaImagemCabecalho);
                        }

                        table.addCell(celulaImagemCabecalho);

                        //Configuração do texto do cabeçalho 
                        Cell celulaTextoCabecalho = new Cell();
                        table.setBorder(Border.NO_BORDER);
                        if (Utils.isNotEmpty(fonSizeCabecalho)) {
                            celulaTextoCabecalho.setFontSize(fonSizeCabecalho);
                        } else {
                            celulaTextoCabecalho.setFontSize(12);
                        }
                        celulaTextoCabecalho.add(new Paragraph(textoCabecalho));
                        celulaTextoCabecalho.setBorder(Border.NO_BORDER);
                        if (Utils.isNotEmpty(margemEsquerdaTextoCabecalho)) {
                            celulaTextoCabecalho.setPaddingLeft(margemEsquerdaTextoCabecalho);
                        }
                        if (Utils.isNotEmpty(margemDireitaTextoCabecalho)) {
                            celulaTextoCabecalho.setPaddingRight(margemDireitaTextoCabecalho);
                        }
                        celulaTextoCabecalho.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        celulaTextoCabecalho.setVerticalAlignment(VerticalAlignment.MIDDLE);
                        table.addCell(celulaTextoCabecalho);

                    } else {
                        //Configuração do texto do cabeçalho 
                        Cell celulaTextoCabecalho = new Cell();
                        table.setBorder(Border.NO_BORDER);
                        if (Utils.isNotEmpty(fonSizeCabecalho)) {
                            celulaTextoCabecalho.setFontSize(fonSizeCabecalho);
                        } else {
                            celulaTextoCabecalho.setFontSize(12);
                        }
                        celulaTextoCabecalho.add(new Paragraph(textoCabecalho));
                        celulaTextoCabecalho.setBorder(Border.NO_BORDER);
                        if (Utils.isNotEmpty(margemEsquerdaTextoCabecalho)) {
                            celulaTextoCabecalho.setPaddingLeft(margemEsquerdaTextoCabecalho);
                        }
                        if (Utils.isNotEmpty(margemDireitaTextoCabecalho)) {
                            celulaTextoCabecalho.setPaddingRight(margemDireitaTextoCabecalho);
                        }
                        celulaTextoCabecalho.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        celulaTextoCabecalho.setVerticalAlignment(VerticalAlignment.MIDDLE);
                        table.addCell(celulaTextoCabecalho);

                        //Configuração da imagem do cabeçalho 
                        ImageData imageData = ImageDataFactory.create(urlImagemCabecalho);
                        Image imagemCabecalho = new Image(imageData);
                        if (Utils.isNotEmpty(alturaImagemCabecalho)) {
                            imagemCabecalho.setHeight(alturaImagemCabecalho);
                        } else {
                            imagemCabecalho.setHeight(50);
                        }
                        Cell celulaImagemCabecalho = new Cell();
                        celulaImagemCabecalho.add(imagemCabecalho);
                        celulaImagemCabecalho.setBorder(Border.NO_BORDER);

                        if (Utils.isNotEmpty(margemEsquerdaImagemCabecalho)) {
                            celulaImagemCabecalho.setPaddingLeft(margemEsquerdaImagemCabecalho);
                        }
                        if (Utils.isNotEmpty(margemDireitaImagemCabecalho)) {
                            celulaImagemCabecalho.setPaddingRight(margemDireitaImagemCabecalho);
                        }

                        table.addCell(celulaImagemCabecalho);

                    }
                } else {
                    //Configuração do texto do cabeçalho 

                    table.setBorder(Border.NO_BORDER);
                    Cell celulaTextoCabecalho = new Cell();
                    if (Utils.isNotEmpty(fonSizeCabecalho)) {
                        celulaTextoCabecalho.setFontSize(fonSizeCabecalho);
                    } else {
                        celulaTextoCabecalho.setFontSize(12);
                    }
                    celulaTextoCabecalho.add(new Paragraph(textoCabecalho));
                    celulaTextoCabecalho.setBorder(Border.NO_BORDER);
                    if (Utils.isNotEmpty(margemEsquerdaTextoCabecalho)) {
                        celulaTextoCabecalho.setPaddingLeft(margemEsquerdaTextoCabecalho);
                    }
                    if (Utils.isNotEmpty(margemDireitaTextoCabecalho)) {
                        celulaTextoCabecalho.setPaddingRight(margemDireitaTextoCabecalho);
                    }
                    celulaTextoCabecalho.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    celulaTextoCabecalho.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    table.addCell(celulaTextoCabecalho);
                }
            }

            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            pdfCanvas.saveState();
            pdfCanvas.beginText();
            pdfCanvas.moveText(100, 100);
            Canvas tableCanvas = new Canvas(pdfCanvas, pdfDoc, pdfDoc.getDefaultPageSize());
            tableCanvas.add(table);
            tableCanvas.close();
            pdfCanvas.endText();

            table = new Table(1);
            if (Utils.isNotEmpty(margemBaixoRodape)) {
                table.setMarginBottom(margemBaixoRodape);
            } else {
                table.setMarginBottom(10);
            }
            if (Utils.isNotEmpty(margemTopoRodape)) {
                table.setMarginTop(margemTopoRodape);
            } else {
                table.setMarginBottom(10);
            }
            if (Utils.isNotEmpty(margemEsquerdaCabecalho)) {
                table.setMarginLeft(margemEsquerdaCabecalho);
            } else {
                table.setMarginBottom(10);
            }
            if (Utils.isNotEmpty(margemDireitarodape)) {
                table.setMarginRight(margemDireitarodape);
            } else {
                table.setMarginBottom(10);
            }
            if (Utils.isNotEmpty(textoRodapé)) {
                if (Utils.isNotEmpty(urlImagemRodape)) {
                    table = new Table(2);
                    if (Utils.isNotEmpty(margemBaixoRodape)) {
                        table.setMarginBottom(margemBaixoRodape);
                    } else {
                        table.setMarginBottom(10);
                    }
                    if (Utils.isNotEmpty(margemTopoRodape)) {
                        table.setMarginTop(margemTopoRodape);
                    } else {
                        table.setMarginBottom(10);
                    }
                    if (Utils.isNotEmpty(margemEsquerdaRodape)) {
                        table.setMarginLeft(margemEsquerdaRodape);
                    } else {
                        table.setMarginBottom(10);
                    }
                    if (Utils.isNotEmpty(margemEsquerdaCabecalho)) {
                        table.setMarginRight(margemEsquerdaCabecalho);
                    } else {
                        table.setMarginBottom(10);
                    }
                    if (Utils.isNotEmpty(margemDireitarodape)) {
                        table.setMarginRight(margemDireitarodape);
                    } else {
                        table.setMarginBottom(10);
                    }
                    table.setBorder(Border.NO_BORDER);
                    if (Utils.isNotEmpty(margemBaixoRodape)) {
                        table.setMarginTop(margemBaixoRodape);
                    }

                    if (imagemRodapeEsquerda) {
                        //Configuração da imagem do rodape 
                        ImageData imageData = ImageDataFactory.create(urlImagemRodape);
                        Image imagemRodape = new Image(imageData);
                        if (Utils.isNotEmpty(alturaImagemRodape)) {
                            imagemRodape.setHeight(alturaImagemRodape);
                        } else {
                            imagemRodape.setHeight(50);
                        }
                        Cell celulaImagemRodape = new Cell();
                        celulaImagemRodape.add(imagemRodape);
                        celulaImagemRodape.setBorder(Border.NO_BORDER);

                        if (Utils.isNotEmpty(margemEsquerdaImagemRodape)) {
                            celulaImagemRodape.setPaddingLeft(margemEsquerdaImagemRodape);
                        }
                        if (Utils.isNotEmpty(margemDireitaImagemRodape)) {
                            celulaImagemRodape.setPaddingRight(margemDireitaImagemRodape);
                        }

                        table.addCell(celulaImagemRodape);
                        //Configuração do texto do cabeçalho 
                        Cell celulaTextoRodape = new Cell();
                        table.setBorder(Border.NO_BORDER);
                        if (Utils.isNotEmpty(fonSizeRodape)) {
                            celulaTextoRodape.setFontSize(fonSizeRodape);
                        } else {
                            celulaTextoRodape.setFontSize(12);
                        }
                        celulaTextoRodape.add(new Paragraph(textoRodapé).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
                        celulaTextoRodape.setBorder(Border.NO_BORDER);
                        if (Utils.isNotEmpty(margemEsquerdaTextoRodape)) {
                            celulaTextoRodape.setPaddingLeft(margemEsquerdaTextoRodape);
                        }
                        if (Utils.isNotEmpty(margemDireitaTextoRodape)) {
                            celulaTextoRodape.setPaddingRight(margemDireitaTextoRodape);
                        }
                        celulaTextoRodape.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        celulaTextoRodape.setVerticalAlignment(VerticalAlignment.MIDDLE);
                        table.addCell(celulaTextoRodape);

                    } else {
                        //Configuração do texto do cabeçalho 
                        Cell celulaTextoRodape = new Cell();
                        table.setBorder(Border.NO_BORDER);
                        if (Utils.isNotEmpty(fonSizeRodape)) {
                            celulaTextoRodape.setFontSize(fonSizeRodape);
                        } else {
                            celulaTextoRodape.setFontSize(12);
                        }
                        celulaTextoRodape.add(new Paragraph(textoRodapé).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
                        celulaTextoRodape.setBorder(Border.NO_BORDER);
                        if (Utils.isNotEmpty(margemEsquerdaTextoRodape)) {
                            celulaTextoRodape.setPaddingLeft(margemEsquerdaTextoRodape);
                        }
                        if (Utils.isNotEmpty(margemDireitaTextoRodape)) {
                            celulaTextoRodape.setPaddingRight(margemDireitaTextoRodape);
                        }
                        celulaTextoRodape.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        celulaTextoRodape.setVerticalAlignment(VerticalAlignment.MIDDLE);
                        table.addCell(celulaTextoRodape);

                        //Configuração da imagem do rodape
                        ImageData imageData = ImageDataFactory.create(urlImagemRodape);
                        Image imagemRodape = new Image(imageData);
                        if (Utils.isNotEmpty(alturaImagemRodape)) {
                            imagemRodape.setHeight(alturaImagemRodape);
                        } else {
                            imagemRodape.setHeight(50);
                        }
                        Cell celulaImagemRodape = new Cell();
                        celulaImagemRodape.add(imagemRodape);
                        celulaImagemRodape.setBorder(Border.NO_BORDER);

                        if (Utils.isNotEmpty(margemEsquerdaImagemRodape)) {
                            celulaImagemRodape.setPaddingLeft(margemEsquerdaImagemRodape);
                        }
                        if (Utils.isNotEmpty(margemDireitaImagemRodape)) {
                            celulaImagemRodape.setPaddingRight(margemDireitaImagemRodape);
                        }

                        table.addCell(celulaImagemRodape);

                    }
                } else {
                    //Configuração do texto do rodape

                    table.setBorder(Border.NO_BORDER);
                    Cell celulaTextoRodaPe = new Cell();
                    if (Utils.isNotEmpty(fonSizeRodape)) {
                        celulaTextoRodaPe.setFontSize(fonSizeRodape);
                    } else {
                        celulaTextoRodaPe.setFontSize(12);
                    }
                    celulaTextoRodaPe.add(new Paragraph(textoRodapé).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
                    celulaTextoRodaPe.setBorder(Border.NO_BORDER);
                    if (Utils.isNotEmpty(margemEsquerdaTextoRodape)) {
                        celulaTextoRodaPe.setPaddingLeft(margemEsquerdaTextoRodape);
                    }
                    if (Utils.isNotEmpty(margemDireitaTextoCabecalho)) {
                        celulaTextoRodaPe.setPaddingRight(margemDireitaTextoRodape);
                    }
                    celulaTextoRodaPe.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    celulaTextoRodaPe.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    table.addCell(celulaTextoRodaPe);
                }
            }
            table.setMarginTop(755);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            pdfCanvas.saveState();
            pdfCanvas.beginText();
            pdfCanvas.moveText(100, 100);
            tableCanvas = new Canvas(pdfCanvas, pdfDoc, pdfDoc.getDefaultPageSize());
            tableCanvas.add(table);
            tableCanvas.close();
            pdfCanvas.endText();

            pdfCanvas.release();
        } catch (IOException ex) {
            Logger.getLogger(HeaderFooterPageEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getMargemTopoCabecalho() {
        return margemTopoCabecalho;
    }

    public void setMargemTopoCabecalho(int margemTopoCabecalho) {
        this.margemTopoCabecalho = margemTopoCabecalho;
    }

    public int getMargemBaixoCabecalho() {
        return margemBaixoCabecalho;
    }

    public void setMargemBaixoCabecalho(int margemBaixoCabecalho) {
        this.margemBaixoCabecalho = margemBaixoCabecalho;
    }

    public int getMargemEsquerdaCabecalho() {
        return margemEsquerdaCabecalho;
    }

    public void setMargemEsquerdaCabecalho(int margemEsquerdaCabecalho) {
        this.margemEsquerdaCabecalho = margemEsquerdaCabecalho;
    }

    public int getMargemDireitaCabecalho() {
        return margemDireitaCabecalho;
    }

    public void setMargemDireitaCabecalho(int margemDireitaCabecalho) {
        this.margemDireitaCabecalho = margemDireitaCabecalho;
    }

    public int getMargemTopoRodape() {
        return margemTopoRodape;
    }

    public void setMargemTopoRodape(int margemTopoRodape) {
        this.margemTopoRodape = margemTopoRodape;
    }

    public int getMargemBaixoRodape() {
        return margemBaixoRodape;
    }

    public void setMargemBaixoRodape(int margemBaixoRodape) {
        this.margemBaixoRodape = margemBaixoRodape;
    }

    public int getMargemEsquerdaRodape() {
        return margemEsquerdaRodape;
    }

    public void setMargemEsquerdaRodape(int margemEsquerdaRodape) {
        this.margemEsquerdaRodape = margemEsquerdaRodape;
    }

    public int getMargemDireitarodape() {
        return margemDireitarodape;
    }

    public void setMargemDireitarodape(int margemDireitarodape) {
        this.margemDireitarodape = margemDireitarodape;
    }

    public String getUrlImagemCabecalho() {
        return urlImagemCabecalho;
    }

    public void setUrlImagemCabecalho(String urlImagemCabecalho) {
        this.urlImagemCabecalho = urlImagemCabecalho;
    }

    public String getUrlImagemRodape() {
        return urlImagemRodape;
    }

    public void setUrlImagemRodape(String urlImagemRodape) {
        this.urlImagemRodape = urlImagemRodape;
    }

    public String getTextoCabecalho() {
        return textoCabecalho;
    }

    public void setTextoCabecalho(String textoCabecalho) {
        this.textoCabecalho = textoCabecalho;
    }

    public String getTextoRodapé() {
        return textoRodapé;
    }

    public void setTextoRodapé(String textoRodapé) {
        this.textoRodapé = textoRodapé;
    }

    public int getAlturaImagemCabecalho() {
        return alturaImagemCabecalho;
    }

    public void setAlturaImagemCabecalho(int alturaImagemCabecalho) {
        this.alturaImagemCabecalho = alturaImagemCabecalho;
    }

    public int getAlturaImagemRodape() {
        return alturaImagemRodape;
    }

    public void setAlturaImagemRodape(int alturaImagemRodape) {
        this.alturaImagemRodape = alturaImagemRodape;
    }

    public boolean isImagemCabecalhoEsquerda() {
        return imagemCabecalhoEsquerda;
    }

    public void setImagemCabecalhoEsquerda(boolean imagemCabecalhoEsquerda) {
        this.imagemCabecalhoEsquerda = imagemCabecalhoEsquerda;
    }

    public boolean isImagemRodapeEsquerda() {
        return imagemRodapeEsquerda;
    }

    public void setImagemRodapeEsquerda(boolean imagemRodapeEsquerda) {
        this.imagemRodapeEsquerda = imagemRodapeEsquerda;
    }

    public int getFonSizeCabecalho() {
        return fonSizeCabecalho;
    }

    public void setFonSizeCabecalho(int fonSizeCabecalho) {
        this.fonSizeCabecalho = fonSizeCabecalho;
    }

    public int getFonSizeRodape() {
        return fonSizeRodape;
    }

    public void setFonSizeRodape(int fonSizeRodape) {
        this.fonSizeRodape = fonSizeRodape;
    }

    public int getMargemDireitaTextoCabecalho() {
        return margemDireitaTextoCabecalho;
    }

    public void setMargemDireitaTextoCabecalho(int margemDireitaTextoCabecalho) {
        this.margemDireitaTextoCabecalho = margemDireitaTextoCabecalho;
    }

    public int getMargemEsquerdaTextoCabecalho() {
        return margemEsquerdaTextoCabecalho;
    }

    public void setMargemEsquerdaTextoCabecalho(int margemEsquerdaTextoCabecalho) {
        this.margemEsquerdaTextoCabecalho = margemEsquerdaTextoCabecalho;
    }

    public int getMargemDireitaTextoRodape() {
        return margemDireitaTextoRodape;
    }

    public void setMargemDireitaTextoRodape(int margemDireitaTextoRodape) {
        this.margemDireitaTextoRodape = margemDireitaTextoRodape;
    }

    public int getMargemEsquerdaTextoRodape() {
        return margemEsquerdaTextoRodape;
    }

    public void setMargemEsquerdaTextoRodape(int margemEsquerdaTextoRodape) {
        this.margemEsquerdaTextoRodape = margemEsquerdaTextoRodape;
    }

    public int getMargemDireitaImagemCabecalho() {
        return margemDireitaImagemCabecalho;
    }

    public void setMargemDireitaImagemCabecalho(int margemDireitaImagemCabecalho) {
        this.margemDireitaImagemCabecalho = margemDireitaImagemCabecalho;
    }

    public int getMargemEsquerdaImagemCabecalho() {
        return margemEsquerdaImagemCabecalho;
    }

    public void setMargemEsquerdaImagemCabecalho(int margemEsquerdaImagemCabecalho) {
        this.margemEsquerdaImagemCabecalho = margemEsquerdaImagemCabecalho;
    }

    public int getMargemDireitaImagemRodape() {
        return margemDireitaImagemRodape;
    }

    public void setMargemDireitaImagemRodape(int margemDireitaImagemRodape) {
        this.margemDireitaImagemRodape = margemDireitaImagemRodape;
    }

    public int getMargemEsquerdaImagemRodape() {
        return margemEsquerdaImagemRodape;
    }

    public void setMargemEsquerdaImagemRodape(int margemEsquerdaImagemRodape) {
        this.margemEsquerdaImagemRodape = margemEsquerdaImagemRodape;
    }

    public String getUrlTimbrado() {
        return urlTimbrado;
    }

    public void setUrlTimbrado(String urlTimbrado) {
        this.urlTimbrado = urlTimbrado;
    }

}
