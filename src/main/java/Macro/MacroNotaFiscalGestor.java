/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Macro;

import Enum.StatusCumprimento;
import Enum.TipoFiscalizacaoEnum;
import Enum.TipoRecebimentoEnum;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import modelo.Acrescimo;
import modelo.Contratado;
import modelo.Contrato;
import modelo.NotaFiscal;
import modelo.Repactuacao;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import util.DateUtils;
import util.Utils;

/**
 *
 * @author eduardo
 */
public class MacroNotaFiscalGestor {

    public static Document MacroNotaFiscal(Document document, Contratado contratado, Contrato contrato, NotaFiscal nota, UnidadeOrganizacional orgao, List<Acrescimo> acrescimo, List<Repactuacao> termosApostilamentos, Usuario user, List<NotaFiscal> notas) throws DocumentException, IOException {
        for (NotaFiscal notaFiscal : notas) {
            document.setMargins(100, 25, 100, 25);
            Table table1 = new Table(1);
            PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
            PdfFont font2 = PdfFontFactory.createFont(FontConstants.HELVETICA);
            Color fontColor = new DeviceRgb(38, 38, 38);

            Cell cell = new Cell();
            DeviceRgb lightGray = new DeviceRgb(211, 211, 211);
            Table segundaTable = new Table(1);

            String dataString = DateUtils.format(DateUtils.SIMPLE_DATE, new Date());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(dataString, formatter);
            int ano = data.getYear();
            segundaTable.addCell(new Paragraph("RELATÓRIO " + notaFiscal.getTipoRecebimento().getNome().toUpperCase() + " DE FISCALIZAÇÃO DE CONTRATOS\n"
                    + "EXERCÍCIO FINANDEIRO " + ano)).setBackgroundColor(lightGray).setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
            segundaTable.setPadding(3);
            segundaTable.setWidth(550);

            document.add(segundaTable);
            Table tableMaiorTerceira = new Table(1);
            tableMaiorTerceira.setPadding(3);
            tableMaiorTerceira.setWidth(550);
            Table terceiraTable = new Table(1);
            terceiraTable.setPadding(3);
            terceiraTable.setWidth(550);
            Paragraph paragraphGlobal = new Paragraph("CONTRATO Nº   " + contrato.getNumeroProcesso() + " - DL").setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.add(paragraphGlobal);
            terceiraTable.addCell(cell);
            paragraphGlobal = new Paragraph("Objeto: \n");
            paragraphGlobal.setBorder(Border.NO_BORDER).setFontSize(10).setFont(font);
            if (Utils.isNotEmpty(contrato.getObjetoContrato())) {
                Text text2 = new Text(contrato.getObjetoContrato());
                text2.setFont(font2);
                text2.setFontSize(9);
                text2.setFontColor(fontColor);
                paragraphGlobal.add(text2);
            }
            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.add(paragraphGlobal);
            terceiraTable.addCell(cell);
            tableMaiorTerceira.addCell(terceiraTable);
            document.add(tableMaiorTerceira);

            Table quartaTable = new Table(1);
            quartaTable.setPadding(3);
            quartaTable.setWidth(550);
            quartaTable.addCell(new Paragraph("DADOS DO FISCAL DESIGNADO")).setBackgroundColor(lightGray).setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
            Table quartintaTable = new Table(1);
            quartintaTable.setPadding(3);
            quartintaTable.setWidth(550);
            paragraphGlobal = new Paragraph("FISCAL DE CONTRATOS : ").setFontSize(9);
            if (Utils.isNotEmpty(contrato.getTipoFiscalizacao())) {
                if (contrato.getTipoFiscalizacao().equals(TipoFiscalizacaoEnum.INDIVIDUAL)) {
                    if (Utils.isNotEmpty(contrato.getFiscal())) {
                        Text text2 = new Text(contrato.getFiscal().getNome());
                        text2.setFont(font2);
                        text2.setFontSize(9);
                        text2.setFontColor(fontColor);
                        paragraphGlobal.add(text2);
                    }
                } else {
                    if (Utils.isNotEmpty(contrato.getFiscaisContrato())) {
                        String nomes = "";
                        for (Usuario users : contrato.getFiscaisContrato()) {
                            nomes += users.getNome() + ", ";
                        }
                        int ultimaVirgula = nomes.lastIndexOf(",");
                        if (ultimaVirgula >= 0) {
                            nomes = nomes.substring(0, ultimaVirgula) + nomes.substring(ultimaVirgula + 1);
                        }
                        Text text2 = new Text(nomes + ".");
                        text2.setFont(font2);
                        text2.setFontSize(9);
                        text2.setFontColor(fontColor);
                        paragraphGlobal.add(text2);
                    }
                }
            }
            Text text2 = new Text("\nPORTARIA DE NOMEAÇÃO: ");
            text2.setFont(font2);
            text2.setFontSize(9);
            paragraphGlobal.add(text2);
            if (Utils.isNotEmpty(contrato.getPortariaNomeacao())) {
                text2 = new Text(contrato.getPortariaNomeacao());
                text2.setFont(font2);
                text2.setFontSize(9);
                text2.setFontColor(fontColor);
                paragraphGlobal.add(text2);
            }

            quartintaTable.addCell(paragraphGlobal);
            document.add(quartaTable);
            document.add(quartintaTable);

            Table quintaTable = new Table(1);
            quintaTable.setPadding(3);
            quintaTable.setWidth(550);
            quintaTable.addCell(new Paragraph("COMPETÊNCIA DA FISCALIZAÇÃO")).setBackgroundColor(lightGray).setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
            document.add(quintaTable);

            Table sextaTable = new Table(1);
            sextaTable.setPadding(3);
            sextaTable.setWidth(550);
            paragraphGlobal = new Paragraph("Período fiscalizado: de " + DateUtils.format(DateUtils.DD_MM_YYYY, notaFiscal.getInicioFiscalizado()) + " a " + DateUtils.format(DateUtils.DD_MM_YYYY, notaFiscal.getFinalFiscalizado()) + ".").setFontSize(9).setFont(font2);
            sextaTable.addCell(paragraphGlobal);
            document.add(sextaTable);

            Table setimaTable = new Table(1);
            setimaTable.addCell(new Paragraph("OCORRÊNCIAS")).setBackgroundColor(lightGray).setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
            setimaTable.setPadding(3);
            setimaTable.setWidth(550);
            document.add(setimaTable);
            Table oitavaTable = new Table(4);
            oitavaTable.setPadding(3);
            oitavaTable.setWidth(550);
            paragraphGlobal = new Paragraph("1. Cumpriu as obrigações contratuais mensais").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            if (notaFiscal.getObrigacaoMensal().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getObrigacaoMensal().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getObrigacaoMensal().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

            paragraphGlobal = new Paragraph("2. Obedeceu aos prazos estabelecidos").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getPrazoEstabelecido().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getPrazoEstabelecido().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getPrazoEstabelecido().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }
            paragraphGlobal = new Paragraph("3. Entregou documentos a que estava obrigado").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getDocumentoObrigatorio().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getDocumentoObrigatorio().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getDocumentoObrigatorio().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

            paragraphGlobal = new Paragraph("4. Elaborou e encaminhou relatório mensal de atividades").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getRelatorio().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getRelatorio().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getRelatorio().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

            paragraphGlobal = new Paragraph("5. Prestou serviço com a qualidade esperada").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getQualidadeEsperada().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getQualidadeEsperada().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getQualidadeEsperada().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

            paragraphGlobal = new Paragraph("6. Informou ou comunicou situações a que estava obrigado").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getInformouSituacao().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getInformouSituacao().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getInformouSituacao().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

            paragraphGlobal = new Paragraph("7. Realizou diligências necessárias").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getDiligenciaNecessarias().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getDiligenciaNecessarias().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getDiligenciaNecessarias().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

            paragraphGlobal = new Paragraph("8. Necessidade de Notificação Extrajudicial").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getNotificacaoExtrajudicial().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getNotificacaoExtrajudicial().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getNotificacaoExtrajudicial().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

            paragraphGlobal = new Paragraph("9.Necessidade de Abertura de Procedimento de Penalização").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getProcedimentoPenalizacao().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getProcedimentoPenalizacao().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getProcedimentoPenalizacao().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

//        paragraphGlobal = new Paragraph("10. Suspensão do Contrato ou Paralisação dos Serviços").setFont(font2).setFontSize(9);
//        oitavaTable.addCell(paragraphGlobal);
//
//        if (nota.getSuspensaoParalizacao().equals(StatusCumprimento.SIM)) {
//            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//        } else if (nota.getSuspensaoParalizacao().equals(StatusCumprimento.NAO)) {
//            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//        } else if (nota.getSuspensaoParalizacao().equals(StatusCumprimento.NAO_APLICA)) {
//            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//        }
            paragraphGlobal = new Paragraph("10. Suspensão do Contrato ou Paralisação dos Serviços").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getSuspensaoParalizacao().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getSuspensaoParalizacao().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getSuspensaoParalizacao().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

            paragraphGlobal = new Paragraph("11. Necessidade de Rescisão").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);

            if (notaFiscal.getNecessidadeRescisao().equals(StatusCumprimento.SIM)) {
                paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getNecessidadeRescisao().equals(StatusCumprimento.NAO)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            } else if (notaFiscal.getNecessidadeRescisao().equals(StatusCumprimento.NAO_APLICA)) {
                paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
                paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
                oitavaTable.addCell(paragraphGlobal);
            }

            document.add(oitavaTable);
            Table notaTable = new Table(1);
            notaTable.setPadding(3);
            notaTable.setWidth(550);
            if (notaFiscal.getTipoRecebimento().equals(TipoRecebimentoEnum.DEFINITIVO)) {
                paragraphGlobal = new Paragraph("(   ) Recebimento Provisório (mensal) ").setFont(font2).setFontSize(9);
                cell = new Cell();
                cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
                notaTable.addCell(cell);
                paragraphGlobal = new Paragraph("( X ) Recebimento Definitivo – Data: " + DateUtils.format(DateUtils.SIMPLE_DATE, nota.getDataPagamento())).setFont(font2).setFontSize(9);
                cell = new Cell();
                cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
                notaTable.addCell(cell);
                if (Utils.isNotEmpty(notaFiscal.getConsideracoes())) {
                    paragraphGlobal = new Paragraph("Considerações : " + notaFiscal.getConsideracoes()).setFont(font2).setFontSize(9);
                } else {
                    paragraphGlobal = new Paragraph("Considerações : ").setFont(font2).setFontSize(9);
                }
                cell = new Cell();
                cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
                notaTable.addCell(cell);
            } else {
                paragraphGlobal = new Paragraph("( X ) Recebimento Provisório (mensal) ").setFont(font2).setFontSize(9);
                cell = new Cell();
                cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
                notaTable.addCell(cell);
                paragraphGlobal = new Paragraph("(   ) Recebimento Definitivo – Data: __/__/____").setFont(font2).setFontSize(9);
                cell = new Cell();
                cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
                notaTable.addCell(cell);
                if (Utils.isNotEmpty(notaFiscal.getConsideracoes())) {
                    paragraphGlobal = new Paragraph("Considerações : " + notaFiscal.getConsideracoes()).setFont(font2).setFontSize(9);
                } else {
                    paragraphGlobal = new Paragraph("Considerações : ").setFont(font2).setFontSize(9);

                }
                cell = new Cell();
                cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
                notaTable.addCell(cell);
            }
            Table decimaSegundaTable = new Table(1);
            decimaSegundaTable.setPadding(3);
            decimaSegundaTable.setWidth(550);
            decimaSegundaTable.addCell(notaTable);
            document.add(decimaSegundaTable);

            if (notaFiscal.getDiligenciaNecessarias().equals(StatusCumprimento.SIM)
                    || notaFiscal.getNotificacaoExtrajudicial().equals(StatusCumprimento.SIM)
                    || notaFiscal.getSuspensaoParalizacao().equals(StatusCumprimento.SIM)
                    || notaFiscal.getProcedimentoPenalizacao().equals(StatusCumprimento.SIM)
                    || notaFiscal.getNecessidadeRescisao().equals(StatusCumprimento.SIM)) {

                Table decimaTable = new Table(1);
                decimaTable.setPadding(3);
                decimaTable.setWidth(550);
                if (Utils.isNotEmpty(notaFiscal.getObservacoes())) {
                    paragraphGlobal = new Paragraph("OBSERVAÇÕES: " + notaFiscal.getObservacoes()).setFont(font2).setFontSize(10);
                } else {
                    paragraphGlobal = new Paragraph("OBSERVAÇÕES: ").setFont(font2).setFontSize(10);

                }
                decimaTable.addCell(paragraphGlobal);

                document.add(decimaTable);
            }
            Table decimaPrimeiraTable = new Table(1);
            decimaPrimeiraTable.setPadding(3);
            decimaPrimeiraTable.setWidth(550);
            paragraphGlobal = new Paragraph(orgao.getEndereco().getCidade() + " - " + orgao.getEndereco().getEstado() + ", " + DateUtils.format(DateUtils.DD_DE_MMMM_DE_YYYY, nota.getFinalFiscalizado())).setFont(font2).setPaddingTop(10).setFontSize(10);
            cell = new Cell();
            cell.add(paragraphGlobal);
            cell.setBorder(Border.NO_BORDER);
            decimaPrimeiraTable.addCell(cell);
            document.add(decimaPrimeiraTable);

            Table decimaTerceira = new Table(1);

            decimaTerceira.setPadding(3);
            decimaTerceira.setWidth(550);
            paragraphGlobal = new Paragraph("FISCAL DE CONTRATOS").setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER);
            cell = new Cell();
            cell.add(paragraphGlobal).setPaddingTop(40);
            cell.setBorder(Border.NO_BORDER);
            decimaTerceira.addCell(cell);
            paragraphGlobal = new Paragraph(orgao.getNome()).setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER);
            cell = new Cell();
            cell.add(paragraphGlobal);
            cell.setBorder(Border.NO_BORDER);
            decimaTerceira.addCell(cell);
            paragraphGlobal = new Paragraph(contrato.getFiscal().getNome()).setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER);
            cell = new Cell();
            cell.add(paragraphGlobal);
            cell.setBorder(Border.NO_BORDER);
            decimaTerceira.addCell(cell);

            document.add(decimaTerceira);
            document.add(new AreaBreak());
        }

        document.setMargins(100, 25, 100, 25);
        Table table1 = new Table(1);
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont font2 = PdfFontFactory.createFont(FontConstants.HELVETICA);
        Color fontColor = new DeviceRgb(38, 38, 38);

        Cell cell = new Cell();
        DeviceRgb lightGray = new DeviceRgb(211, 211, 211);
        Table segundaTable = new Table(1);

        String dataString = DateUtils.format(DateUtils.SIMPLE_DATE, new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataString, formatter);
        int ano = data.getYear();
        segundaTable.addCell(new Paragraph("RELATÓRIO " + nota.getTipoRecebimento().getNome().toUpperCase() + " DE FISCALIZAÇÃO DE CONTRATOS\n"
                + "EXERCÍCIO FINANDEIRO " + ano)).setBackgroundColor(lightGray).setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
        segundaTable.setPadding(3);
        segundaTable.setWidth(550);

        document.add(segundaTable);
        Table tableMaiorTerceira = new Table(1);
        tableMaiorTerceira.setPadding(3);
        tableMaiorTerceira.setWidth(550);
        Table terceiraTable = new Table(1);
        terceiraTable.setPadding(3);
        terceiraTable.setWidth(550);
        Paragraph paragraphGlobal = new Paragraph("CONTRATO Nº   " + contrato.getNumeroProcesso() + " - DL").setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.add(paragraphGlobal);
        terceiraTable.addCell(cell);
        paragraphGlobal = new Paragraph("Objeto: \n");
        paragraphGlobal.setBorder(Border.NO_BORDER).setFontSize(10).setFont(font);
        if (Utils.isNotEmpty(contrato.getObjetoContrato())) {
            Text text2 = new Text(contrato.getObjetoContrato());
            text2.setFont(font2);
            text2.setFontSize(9);
            text2.setFontColor(fontColor);
            paragraphGlobal.add(text2);
        }
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.add(paragraphGlobal);
        terceiraTable.addCell(cell);
        tableMaiorTerceira.addCell(terceiraTable);
        document.add(tableMaiorTerceira);

        Table quartaTable = new Table(1);
        quartaTable.setPadding(3);
        quartaTable.setWidth(550);
        quartaTable.addCell(new Paragraph("DADOS DO FISCAL DESIGNADO")).setBackgroundColor(lightGray).setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
        Table quartintaTable = new Table(1);
        quartintaTable.setPadding(3);
        quartintaTable.setWidth(550);
        paragraphGlobal = new Paragraph("FISCAL DE CONTRATOS : ").setFontSize(9);
        if (Utils.isNotEmpty(contrato.getObjetoContrato())) {
            Text text2 = new Text(contrato.getFiscal().getNome());
            text2.setFont(font2);
            text2.setFontSize(9);
            text2.setFontColor(fontColor);
            paragraphGlobal.add(text2);
        }

        Text text2 = new Text("\nPORTARIA DE NOMEAÇÃO: ");
        text2.setFont(font2);
        text2.setFontSize(9);
        text2.setFontColor(fontColor);
        paragraphGlobal.add(text2);

        if (Utils.isNotEmpty(contrato.getPortariaNomeacao())) {
            text2 = new Text(contrato.getPortariaNomeacao());
            text2.setFont(font2);
            text2.setFontSize(9);
            text2.setFontColor(fontColor);
            paragraphGlobal.add(text2);
        }
        quartintaTable.addCell(paragraphGlobal);
        document.add(quartaTable);
        document.add(quartintaTable);

        Table quintaTable = new Table(1);
        quintaTable.setPadding(3);
        quintaTable.setWidth(550);
        quintaTable.addCell(new Paragraph("COMPETÊNCIA DA FISCALIZAÇÃO")).setBackgroundColor(lightGray).setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
        document.add(quintaTable);

        Table sextaTable = new Table(1);
        sextaTable.setPadding(3);
        sextaTable.setWidth(550);
        paragraphGlobal = new Paragraph("Período fiscalizado: de " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getInicioFiscalizado()) + " a " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getFinalFiscalizado()) + ".").setFontSize(9).setFont(font2);
        sextaTable.addCell(paragraphGlobal);
        document.add(sextaTable);

        Table setimaTable = new Table(1);
        setimaTable.addCell(new Paragraph("OCORRÊNCIAS")).setBackgroundColor(lightGray).setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(font);
        setimaTable.setPadding(3);
        setimaTable.setWidth(550);
        document.add(setimaTable);
        Table oitavaTable = new Table(4);
        oitavaTable.setPadding(3);
        oitavaTable.setWidth(550);
        paragraphGlobal = new Paragraph("1. Cumpriu as obrigações contratuais mensais").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);
        if (nota.getObrigacaoMensal().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getObrigacaoMensal().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getObrigacaoMensal().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

        paragraphGlobal = new Paragraph("2. Obedeceu aos prazos estabelecidos").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getPrazoEstabelecido().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getPrazoEstabelecido().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getPrazoEstabelecido().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }
        paragraphGlobal = new Paragraph("3. Entregou documentos a que estava obrigado").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getDocumentoObrigatorio().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getDocumentoObrigatorio().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getDocumentoObrigatorio().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

        paragraphGlobal = new Paragraph("4. Elaborou e encaminhou relatório mensal de atividades").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getRelatorio().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getRelatorio().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getRelatorio().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

        paragraphGlobal = new Paragraph("5. Prestou serviço com a qualidade esperada").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getQualidadeEsperada().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getQualidadeEsperada().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getQualidadeEsperada().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

        paragraphGlobal = new Paragraph("6. Informou ou comunicou situações a que estava obrigado").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getInformouSituacao().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getInformouSituacao().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getInformouSituacao().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

        paragraphGlobal = new Paragraph("7. Realizou diligências necessárias").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getDiligenciaNecessarias().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getDiligenciaNecessarias().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getDiligenciaNecessarias().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

        paragraphGlobal = new Paragraph("8. Necessidade de Notificação Extrajudicial").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getNotificacaoExtrajudicial().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getNotificacaoExtrajudicial().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getNotificacaoExtrajudicial().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

        paragraphGlobal = new Paragraph("9.Necessidade de Abertura de Procedimento de Penalização").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getProcedimentoPenalizacao().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getProcedimentoPenalizacao().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getProcedimentoPenalizacao().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

//        paragraphGlobal = new Paragraph("10. Suspensão do Contrato ou Paralisação dos Serviços").setFont(font2).setFontSize(9);
//        oitavaTable.addCell(paragraphGlobal);
//
//        if (nota.getSuspensaoParalizacao().equals(StatusCumprimento.SIM)) {
//            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//        } else if (nota.getSuspensaoParalizacao().equals(StatusCumprimento.NAO)) {
//            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//        } else if (nota.getSuspensaoParalizacao().equals(StatusCumprimento.NAO_APLICA)) {
//            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
//            oitavaTable.addCell(paragraphGlobal);
//        }
        paragraphGlobal = new Paragraph("10. Suspensão do Contrato ou Paralisação dos Serviços").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getSuspensaoParalizacao().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getSuspensaoParalizacao().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getSuspensaoParalizacao().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

        paragraphGlobal = new Paragraph("11. Necessidade de Rescisão").setFont(font2).setFontSize(9);
        oitavaTable.addCell(paragraphGlobal);

        if (nota.getNecessidadeRescisao().equals(StatusCumprimento.SIM)) {
            paragraphGlobal = new Paragraph("( X )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getNecessidadeRescisao().equals(StatusCumprimento.NAO)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        } else if (nota.getNecessidadeRescisao().equals(StatusCumprimento.NAO_APLICA)) {
            paragraphGlobal = new Paragraph("(  )Sim").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("(  )Não").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
            paragraphGlobal = new Paragraph("( X )Não se Aplica").setFont(font2).setFontSize(9);
            oitavaTable.addCell(paragraphGlobal);
        }

        document.add(oitavaTable);
        Table notaTable = new Table(1);
        notaTable.setPadding(3);
        notaTable.setWidth(550);
        if (nota.getTipoRecebimento().equals(TipoRecebimentoEnum.DEFINITIVO)) {
            paragraphGlobal = new Paragraph("(   ) Recebimento Provisório (mensal) ").setFont(font2).setFontSize(9);
            cell = new Cell();
            cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
            notaTable.addCell(cell);
            paragraphGlobal = new Paragraph("( X ) Recebimento Definitivo – Data: " + DateUtils.format(DateUtils.SIMPLE_DATE, nota.getDataPagamento())).setFont(font2).setFontSize(9);
            cell = new Cell();
            cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
            notaTable.addCell(cell);
            if (Utils.isNotEmpty(nota.getConsideracoes())) {
                paragraphGlobal = new Paragraph("Considerações : " + nota.getConsideracoes()).setFont(font2).setFontSize(9);
            } else {
                paragraphGlobal = new Paragraph("Considerações : ").setFont(font2).setFontSize(9);
            }
            cell = new Cell();
            cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
            notaTable.addCell(cell);
        } else {
            paragraphGlobal = new Paragraph("( X ) Recebimento Provisório (mensal) ").setFont(font2).setFontSize(9);
            cell = new Cell();
            cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
            notaTable.addCell(cell);
            paragraphGlobal = new Paragraph("(   ) Recebimento Definitivo – Data: __/__/____").setFont(font2).setFontSize(9);
            cell = new Cell();
            cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
            notaTable.addCell(cell);
            if (Utils.isNotEmpty(nota.getConsideracoes())) {
                paragraphGlobal = new Paragraph("Considerações : " + nota.getConsideracoes()).setFont(font2).setFontSize(9);
            } else {
                paragraphGlobal = new Paragraph("Considerações : ").setFont(font2).setFontSize(9);

            }
            cell = new Cell();
            cell.add(paragraphGlobal).setBorder(Border.NO_BORDER);
            notaTable.addCell(cell);
        }
        Table decimaSegundaTable = new Table(1);
        decimaSegundaTable.setPadding(3);
        decimaSegundaTable.setWidth(550);
        decimaSegundaTable.addCell(notaTable);
        document.add(decimaSegundaTable);

        if (nota.getDiligenciaNecessarias().equals(StatusCumprimento.SIM)
                || nota.getNotificacaoExtrajudicial().equals(StatusCumprimento.SIM)
                || nota.getSuspensaoParalizacao().equals(StatusCumprimento.SIM)
                || nota.getProcedimentoPenalizacao().equals(StatusCumprimento.SIM)
                || nota.getNecessidadeRescisao().equals(StatusCumprimento.SIM)) {

            Table decimaTable = new Table(1);
            decimaTable.setPadding(3);
            decimaTable.setWidth(550);
            if (Utils.isNotEmpty(nota.getObservacoes())) {
                paragraphGlobal = new Paragraph("OBSERVAÇÕES: " + nota.getObservacoes()).setFont(font2).setFontSize(10);
            } else {
                paragraphGlobal = new Paragraph("OBSERVAÇÕES: ").setFont(font2).setFontSize(10);

            }
            decimaTable.addCell(paragraphGlobal);

            document.add(decimaTable);
        }
        Table decimaPrimeiraTable = new Table(1);
        decimaPrimeiraTable.setPadding(3);
        decimaPrimeiraTable.setWidth(550);
        paragraphGlobal = new Paragraph(orgao.getEndereco().getCidade() + " - " + orgao.getEndereco().getEstado() + ", " + DateUtils.format(DateUtils.DD_DE_MMMM_DE_YYYY, nota.getFinalFiscalizado())).setFont(font2).setPaddingTop(10).setFontSize(10);
        cell = new Cell();
        cell.add(paragraphGlobal);
        cell.setBorder(Border.NO_BORDER);
        decimaPrimeiraTable.addCell(cell);
        document.add(decimaPrimeiraTable);

        Table decimaTerceira = new Table(1);

        decimaTerceira.setPadding(3);
        decimaTerceira.setWidth(550);
        paragraphGlobal = new Paragraph("FISCAL DE CONTRATOS").setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER);
        cell = new Cell();
        cell.add(paragraphGlobal).setPaddingTop(40);
        cell.setBorder(Border.NO_BORDER);
        decimaTerceira.addCell(cell);
        paragraphGlobal = new Paragraph(orgao.getNome()).setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER);
        cell = new Cell();
        cell.add(paragraphGlobal);
        cell.setBorder(Border.NO_BORDER);
        decimaTerceira.addCell(cell);
        paragraphGlobal = new Paragraph(contrato.getFiscal().getNome()).setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER);
        cell = new Cell();
        cell.add(paragraphGlobal);
        cell.setBorder(Border.NO_BORDER);
        decimaTerceira.addCell(cell);

        document.add(decimaTerceira);

        return document;
    }

    public static String valorPorExtenso(double vlr) {
        if (vlr == 0) {
            return ("zero");
        }

        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) {
            return ("Erro: valor superior a 999 trilhões.");
        }

        String s = "", saux, vlrP;
        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "três", "quatro", "cinco",
            "seis", "sete", "oito", "nove", "dez", "onze",
            "doze", "treze", "quatorze", "quinze", "dezesseis",
            "dezessete", "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
            "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
            "sessenta", "setenta", "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
        String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

// definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
// 1a. parte = 789 (centena)
// 2a. parte = 456 (mil)
// 3a. parte = 123 (milhões)
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else { // última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = "cem";
                } else {
                    n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
                    cent = n / 100;                  // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
                    unid = (n % 100) % 10;           // unid = 1 (unidade um)
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0) {
                            saux = saux + " e " + unidade[n % 100];
                        } else {
                            saux = unidade[n % 100];
                        }
                    } else {
                        if (saux.length() != 0) {
                            saux = saux + " e " + dezena[dez];
                        } else {
                            saux = dezena[dez];
                        }
                        if (unid != 0) {
                            if (saux.length() != 0) {
                                saux = saux + " e " + unidade[unid];
                            } else {
                                saux = unidade[unid];
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (s.length() != 0) {
                    s = saux + ", " + s;
                } else {
                    s = saux;
                }
            }
            if (((i == 0) || (i == 1)) && s.length() != 0) {
                tem = true; // tem centena ou mil no valor
            }
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
        }

        if (s.length() != 0) {
            if (umReal) {
                s = s + " real";
            } else if (tem) {
                s = s + " reais";
            } else {
                s = s + " de reais";
            }
        }

// definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (s.length() != 0) // se não é valor somente com centavos
            {
                s = s + " e ";
            }
            if (centavos.equals("1")) {
                s = s + "um centavo";
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    s = s + unidade[n];
                } else {             // para n = 37, tem-se:
                    unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
                    s = s + dezena[dez];
                    if (unid != 0) {
                        s = s + " e " + unidade[unid];
                    }
                }
                s = s + " centavos";
            }
        }
        return (s);
    }
}
