/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Macro;

import Enum.StatusCumprimento;
import Enum.TipoFiscalizacaoEnum;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Contratado;
import modelo.Contrato;
import modelo.NotaFiscal;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import util.DateUtils;
import util.Utils;

/**
 *
 * @author eduardo
 */
public class MacroNotaFiscal {

    public static Document MacroNotaFiscal(Document document, Contratado contratado, Contrato contrato, NotaFiscal nota, UnidadeOrganizacional orgao) throws DocumentException {
        document.open();
        document.setRole(new PdfName("RecebimentoProvisorio.pdf"));
        PdfPTable table1 = new PdfPTable(1);
        table1.getDefaultCell().setBorder(0);
        Font fonter = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(255, 255, 255));
        PdfPCell cell = new PdfPCell(new Phrase(".", fonter));
        cell.setBorderColor(BaseColor.WHITE);
        cell.setPaddingBottom(30);
        table1.addCell(cell);
        document.add(table1);
        PdfPTable table = new PdfPTable(1);
        table.setPaddingTop(50);
        table.getDefaultCell().setPadding(3);
        table.setWidthPercentage(95);

        table.getDefaultCell().setPaddingBottom(6);
        Font fonteTexto = FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD, new BaseColor(18, 18, 18));
        Font fonte = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(18, 18, 18));
        Font fonteNome = new Font(Font.FontFamily.HELVETICA, 8);

        Font regular = new Font(Font.FontFamily.HELVETICA, 9);
        PdfPCell header = new PdfPCell(new Phrase("DADOS DO CONTRATO E CONTRATADO(A)", fonteTexto));
        Paragraph paragraphGlobal = new Paragraph();

        header.setColspan(3);
        header.setPaddingBottom(6);
        header.setBackgroundColor(new BaseColor(204, 203, 200));
        header.setHorizontalAlignment(1);

        table.addCell(header);
        table.setPaddingTop(300);

        PdfPTable tableMaePai = new PdfPTable(2);
        tableMaePai.setPaddingTop(50);
        tableMaePai.getDefaultCell().setPadding(3);
        tableMaePai.setWidthPercentage(95);

        if (Utils.isNotEmpty(contrato.getObjetoContrato())) {
            paragraphGlobal = new Paragraph("Objeto : ", fonte);
            paragraphGlobal.add(new Chunk(contrato.getObjetoContrato().toString(), regular));
            table.addCell(paragraphGlobal);
        } else {
            paragraphGlobal = new Paragraph("Objeto : ", fonte);
            table.addCell(paragraphGlobal);
        }
        if (Utils.isNotEmpty(contrato.getDataInicio())) {
            if (Utils.isNotEmpty(contrato.getDataFinal())) {
                paragraphGlobal = new Paragraph("Vigência : ", fonte);
                paragraphGlobal.add(new Chunk(DateUtils.format(DateUtils.SIMPLE_DATE, contrato.getDataInicio()) + " a " + DateUtils.format(DateUtils.SIMPLE_DATE, contrato.getDataFinal()), regular));

                table.addCell(paragraphGlobal);
            }
        } else {
            paragraphGlobal = new Paragraph("Vigência : ", fonte);
            table.addCell(paragraphGlobal);
        }
        document.add(table);
//        if (Utils.isNotEmpty(contrato.getDataAssinatura())) {
//            paragraphGlobal = new Paragraph("Ordem de serviço : ", fonte);
//            paragraphGlobal.add(new Chunk(DateUtils.format(DateUtils.SIMPLE_DATE, contrato.getDataInicio()), regular));
//            tableMaePai.addCell(paragraphGlobal);
//        } else {
//            paragraphGlobal = new Paragraph("Ordem de serviço : ", fonte);
//            tableMaePai.addCell(paragraphGlobal);
//        }

        if (Utils.isNotEmpty(contrato.getDataInicio())) {
            paragraphGlobal = new Paragraph("Início da execução : ", fonte);
            paragraphGlobal.add(new Chunk(DateUtils.format(DateUtils.SIMPLE_DATE, contrato.getDataInicio()), regular));
            tableMaePai.addCell(paragraphGlobal);
        } else {
            paragraphGlobal = new Paragraph("Início da execução : ", fonte);
            tableMaePai.addCell(paragraphGlobal);
        }
//        if (Utils.isNotEmpty(nota.getDataPagamento())) {
//            paragraphGlobal = new Paragraph("Data da publicação do extrato : ", fonte);
//            paragraphGlobal.add(new Chunk(DateUtils.format(DateUtils.SIMPLE_DATE, nota.getDataPagamento()), regular));
//            tableMaePai.addCell(paragraphGlobal);
//        } else {
//            paragraphGlobal = new Paragraph("Data da publicação do extrato : ", fonte);
//            tableMaePai.addCell(paragraphGlobal);
//        }
        if (Utils.isNotEmpty(contratado.getNome())) {
            paragraphGlobal = new Paragraph("Contratado(a) : ", fonte);
            paragraphGlobal.add(new Chunk(contratado.getNome(), regular));
            tableMaePai.addCell(paragraphGlobal);
        } else {
            paragraphGlobal = new Paragraph("Contratado(a) : ", fonte);
            tableMaePai.addCell(paragraphGlobal);
        }
        if (Utils.isNotEmpty(contratado.getCnpj())) {
            paragraphGlobal = new Paragraph("CNPJ/MF : ", fonte);
            paragraphGlobal.add(new Chunk(contratado.getCnpj(), regular));
            tableMaePai.addCell(paragraphGlobal);
        } else {
            paragraphGlobal = new Paragraph("CNPJ/MF : ", fonte);
            tableMaePai.addCell(paragraphGlobal);
        }
        if (Utils.isNotEmpty(contratado.getInscricaoEstadual())) {
            paragraphGlobal = new Paragraph("Insc. Estadual : ", fonte);
            paragraphGlobal.add(new Chunk(contratado.getInscricaoEstadual(), regular));
            tableMaePai.addCell(paragraphGlobal);
        } else {
            paragraphGlobal = new Paragraph("Insc. Estadual : ", fonte);
            tableMaePai.addCell(paragraphGlobal);
        }
        if (Utils.isNotEmpty(contratado.getCpf())) {
            paragraphGlobal = new Paragraph("CPF : ", fonte);
            paragraphGlobal.add(new Chunk(contratado.getCpf(), regular));
            tableMaePai.addCell(paragraphGlobal);
        } else {
            paragraphGlobal = new Paragraph("CPF : ", fonte);
            tableMaePai.addCell(paragraphGlobal);
        }
        if (Utils.isNotEmpty(contratado.getRg())) {
            paragraphGlobal = new Paragraph("RG : ", fonte);
            paragraphGlobal.add(new Chunk(contratado.getRg(), regular));
            tableMaePai.addCell(paragraphGlobal);
        } else {
            paragraphGlobal = new Paragraph("RG : ", fonte);
            tableMaePai.addCell(paragraphGlobal);
        }
        document.add(tableMaePai);

        header = new PdfPCell(new Phrase("DADOS DO FISCAL DESIGNADO", fonteTexto));
        table = new PdfPTable(1);
        table.setPaddingTop(50);
        table.getDefaultCell().setPadding(3);
        table.setWidthPercentage(95);

        table.getDefaultCell().setPaddingBottom(6);
        header.setColspan(3);
        header.setPaddingBottom(6);
        header.setBackgroundColor(new BaseColor(204, 203, 200));
        header.setHorizontalAlignment(1);

        table.addCell(header);
//        }
        document.add(table);

        tableMaePai = new PdfPTable(1);
        tableMaePai.setPaddingTop(50);
        tableMaePai.getDefaultCell().setPadding(3);
        tableMaePai.setWidthPercentage(95);
        if (contrato.getTipoFiscalizacao().equals(TipoFiscalizacaoEnum.INDIVIDUAL)) {
            if (Utils.isNotEmpty(contrato.getFiscal())) {
                if (Utils.isNotEmpty(contrato.getFiscal().getNome())) {
                    paragraphGlobal = new Paragraph("Nome : ", fonte);
                    paragraphGlobal.add(new Chunk(contrato.getFiscal().getNome(), regular));
                    tableMaePai.addCell(paragraphGlobal);
                } else {
                    paragraphGlobal = new Paragraph("Nome : ", fonte);
                    tableMaePai.addCell(paragraphGlobal);
                }
            }
        } else {
            String nomeFiscais = "";
            if (Utils.isNotEmpty(contrato.getFiscaisContrato())) {
//                if (Utils.isNotEmpty(contrato.getFiscal().getNome())) {
                paragraphGlobal = new Paragraph("Nome : ", fonte);
                List<Usuario> fiscais = new ArrayList<>();
                for (Usuario user : contrato.getFiscaisContrato()) {
                    fiscais.add(user);
                }
                nomeFiscais = fiscais.get(0).getNome();
                fiscais.remove(0);
                for (Usuario user : fiscais) {
                    nomeFiscais += ", " + user.getNome();
                }
                nomeFiscais += ".";
//                }
                paragraphGlobal.add(new Chunk(nomeFiscais, regular));
                tableMaePai.addCell(paragraphGlobal);
            } else {
                paragraphGlobal = new Paragraph("Nome : ", fonte);
                tableMaePai.addCell(paragraphGlobal);
            }

        }
        document.add(tableMaePai);
//
//        tableMaePai = new PdfPTable(1);
//        tableMaePai.setPaddingTop(50);
//        tableMaePai.getDefaultCell().setPadding(3);
//        tableMaePai.setWidthPercentage(95);
//        if (Utils.isNotEmpty(nota.getApartir())) {
//            paragraphGlobal = new Paragraph("Apartir de : ", fonte);
//            paragraphGlobal.add(new Chunk(DateUtils.format(DateUtils.SIMPLE_DATE, nota.getApartir()), regular));
//            tableMaePai.addCell(paragraphGlobal);
//        } else {
//            paragraphGlobal = new Paragraph("Apartir de : ", fonte);
//            tableMaePai.addCell(paragraphGlobal);
//        }
//        document.add(tableMaePai);

        header = new PdfPCell(new Phrase("DADOS DA FISCALIZAÇÃO", fonteTexto));
        table = new PdfPTable(1);
        table.setPaddingTop(50);
        table.getDefaultCell().setPadding(3);
        table.setWidthPercentage(95);

        table.getDefaultCell().setPaddingBottom(6);
        header.setColspan(3);
        header.setPaddingBottom(6);
        header.setBackgroundColor(new BaseColor(204, 203, 200));
        header.setHorizontalAlignment(1);

        table.addCell(header);
        document.add(table);
        tableMaePai = new PdfPTable(1);
        tableMaePai.setPaddingTop(50);
        tableMaePai.getDefaultCell().setPadding(3);
        tableMaePai.setWidthPercentage(95);
        if (Utils.isNotEmpty(nota.getInicioFiscalizado()) && Utils.isNotEmpty(nota.getFinalFiscalizado())) {
            paragraphGlobal = new Paragraph("Período Fiscalizado: de " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getInicioFiscalizado()) + " a " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getFinalFiscalizado()) + " : ", fonte);
            tableMaePai.addCell(paragraphGlobal);
            document.add(tableMaePai);
        } else {
            paragraphGlobal = new Paragraph("Periodo Fiscalizado: de ___/___/2022 a ___/___/2022 : ", fonte);
            tableMaePai.addCell(paragraphGlobal);
            document.add(tableMaePai);
        }

        header = new PdfPCell(new Phrase("LISTA DE VERIFICAÇÕES ", fonteTexto));
        table = new PdfPTable(1);
        table.setPaddingTop(50);
        table.getDefaultCell().setPadding(3);
        table.setWidthPercentage(95);

        table.getDefaultCell().setPaddingBottom(6);
        header.setColspan(3);
        header.setPaddingBottom(6);
        header.setBackgroundColor(new BaseColor(204, 203, 200));
        header.setHorizontalAlignment(1);

        table.addCell(header);
        document.add(table);
        table = new PdfPTable(new float[]{10, 3});

        header = new PdfPCell(new Phrase("OCORRÊNCIAS ", fonteTexto));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
        header = new PdfPCell(new Phrase("CUMPRIU", fonteTexto));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
        table.setPaddingTop(50);
        table.getDefaultCell().setPadding(3);
        table.setWidthPercentage(95);
        document.add(table);

        tableMaePai = new PdfPTable(new float[]{10, 3});
        tableMaePai.setPaddingTop(50);
        tableMaePai.getDefaultCell().setPadding(3);
        tableMaePai.setWidthPercentage(95);
        paragraphGlobal = new Paragraph("1. Cumpriu as obrigações contratuais mensais ", fonte);
        tableMaePai.addCell(paragraphGlobal);

        if (nota.getObrigacaoMensal().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("Sim", regular);
        } else {
            paragraphGlobal = new Paragraph("Não", regular);
        }
        tableMaePai.addCell(paragraphGlobal);
        paragraphGlobal = new Paragraph("2. Obedeceu aos prazos estabelecidos ", fonte);
        tableMaePai.addCell(paragraphGlobal);

        if (nota.getPrazoEstabelecido().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("Sim", regular);
        } else {
            paragraphGlobal = new Paragraph("Não", regular);
        }

        tableMaePai.addCell(paragraphGlobal);
        paragraphGlobal = new Paragraph("3. Entregou documentos a que estava obrigado ", fonte);
        tableMaePai.addCell(paragraphGlobal);

        if (nota.getDocumentoObrigatorio().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("Sim", regular);
        } else {
            paragraphGlobal = new Paragraph("Não", regular);
        }
        tableMaePai.addCell(paragraphGlobal);
        paragraphGlobal = new Paragraph("4. Elaborou e encaminhou relatório mensal de atividades ", fonte);
        tableMaePai.addCell(paragraphGlobal);

        if (nota.getObrigacaoMensal().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("Sim", regular);
        } else {
            paragraphGlobal = new Paragraph("Não", regular);
        }
        tableMaePai.addCell(paragraphGlobal);
        paragraphGlobal = new Paragraph("5. Prestou serviço com a qualidade esperada ", fonte);
        tableMaePai.addCell(paragraphGlobal);

        if (nota.getQualidadeEsperada().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("Sim", regular);
        } else {
            paragraphGlobal = new Paragraph("Não", regular);
        }
        tableMaePai.addCell(paragraphGlobal);
        paragraphGlobal = new Paragraph("6. Informou ou comunicou situações a que estava  obrigado ", fonte);
        tableMaePai.addCell(paragraphGlobal);

        if (nota.getInformouSituacao().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("Sim", regular);
        } else {
            paragraphGlobal = new Paragraph("Não", regular);
        }
        tableMaePai.addCell(paragraphGlobal);
        paragraphGlobal = new Paragraph("7. Realizou diligências necessárias ", fonte);
        tableMaePai.addCell(paragraphGlobal);

        if (nota.getDiligenciaNecessarias().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("Sim", regular);
        } else {
            paragraphGlobal = new Paragraph("Não", regular);
        }
        tableMaePai.addCell(paragraphGlobal);

        document.add(tableMaePai);
        tableMaePai = new PdfPTable(1);
        tableMaePai.setPaddingTop(50);
        tableMaePai.getDefaultCell().setPadding(3);
        tableMaePai.setWidthPercentage(95);
        paragraphGlobal = new Paragraph("Observações sobre as ocorrências :", fonte);
        tableMaePai.addCell(paragraphGlobal);
        document.add(tableMaePai);

        header = new PdfPCell(new Phrase("CUMPRIMENTO DO CONTRATO E RECEBIMENTO DO OBJETO", fonteTexto));
        table = new PdfPTable(1);
        table.setPaddingTop(50);
        table.getDefaultCell().setPadding(3);
        table.setWidthPercentage(95);

        table.getDefaultCell().setPaddingBottom(6);
        header.setColspan(3);
        header.setPaddingBottom(6);
        header.setBackgroundColor(new BaseColor(204, 203, 200));
        header.setHorizontalAlignment(1);

        table.addCell(header);
        document.add(table);

        tableMaePai = new PdfPTable(1);
        tableMaePai.setPaddingTop(50);
        tableMaePai.getDefaultCell().setPadding(3);
        tableMaePai.setWidthPercentage(95);
        paragraphGlobal = new Paragraph("Considerações : ", fonte);
        tableMaePai.addCell(paragraphGlobal);
        document.add(tableMaePai);

        tableMaePai = new PdfPTable(1);
        tableMaePai.setPaddingTop(50);
        tableMaePai.getDefaultCell().setPadding(3);
        tableMaePai.setWidthPercentage(95);
        String textoLocal = "";
        if (Utils.isNotEmpty(orgao)) {
            if (Utils.isNotEmpty(orgao.getEndereco())) {
                if (Utils.isNotEmpty(orgao.getEndereco().getCidade())) {
                    textoLocal += orgao.getEndereco().getCidade();
                }
                if (Utils.isNotEmpty(orgao.getEndereco().getEstado())) {
                    textoLocal += " - " + orgao.getEndereco().getEstado();
                }
                textoLocal += "., de " + DateUtils.format(DateUtils.DD_MM_YYYY, new Date());
            }
        }
        paragraphGlobal = new Paragraph(textoLocal, fonte);
        tableMaePai.addCell(paragraphGlobal);
        document.add(tableMaePai);

        tableMaePai = new PdfPTable(new float[]{10, 10});
        tableMaePai.setPaddingTop(50);
        tableMaePai.getDefaultCell().setPaddingTop(10);
        tableMaePai.getDefaultCell().setPaddingBottom(10);

        tableMaePai.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableMaePai.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);

        tableMaePai.setWidthPercentage(95);

        paragraphGlobal = new Paragraph("\n\n\n\n________________________________________\n Assinatura do fiscal ", fonte);
        tableMaePai.addCell(paragraphGlobal);
        table = new PdfPTable(1);
        table.setPaddingTop(50);
        table.getDefaultCell().setBorderWidth(0);
        table.getDefaultCell().setPadding(3);
        table.setWidthPercentage(95);

        paragraphGlobal = new Paragraph("Cliente contratado(a) em " + DateUtils.format(DateUtils.DD_MM_YYYY, contrato.getDataInicio()) + "\n\n\n", fonte);
        table.addCell(paragraphGlobal);

        paragraphGlobal = new Paragraph("            ________________________________________\n                                            Assinatura", fonte);
        paragraphGlobal.setAlignment(Element.ALIGN_CENTER);
        table.addCell(paragraphGlobal);
        tableMaePai.addCell(table);
        document.add(tableMaePai);

        return document;
    }
}
