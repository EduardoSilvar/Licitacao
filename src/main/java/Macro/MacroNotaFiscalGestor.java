/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Macro;

import Enum.NaturezaEnum;
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
import java.math.BigDecimal;
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

    public static Document MacroNotaFiscal(Document document, Contratado contratado, Contrato contrato, NotaFiscal nota, UnidadeOrganizacional orgao, List<Acrescimo> acrescimo, List<Repactuacao> termosApostilamentos, Usuario user, List<NotaFiscal> notas) throws DocumentException {
        document.open();
        document.setRole(new PdfName("RecebimentoProvisorio.pdf"));
        PdfPTable table1 = new PdfPTable(1);
        table1.getDefaultCell().setBorder(0);
        Font fonter = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(255, 255, 255));
        Font fonteTitulo = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(0, 0, 0));
        Font fonteSubTitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(0, 0, 0));
        Font fonteRegular = new Font(Font.FontFamily.HELVETICA, 9);
        Font fonteRegularNegrito = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0, 0, 0));
        Font fonteBranca = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(255, 255, 255));

        PdfPCell cell = new PdfPCell(new Phrase(".", fonter));
        cell.setBorderColor(BaseColor.WHITE);
        cell.setPaddingBottom(30);
        table1.addCell(cell);
        document.add(table1);
        PdfPTable segundaTable = new PdfPTable(1);
        segundaTable.getDefaultCell().setBorder(0);
        segundaTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        segundaTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);

        segundaTable.addCell(new Paragraph("TERMO DE RECEBIMENTO DEFINITIVO", fonteTitulo));
        if (Utils.isNotEmpty(nota.getInicioFiscalizado()) && Utils.isNotEmpty(nota.getFinalFiscalizado())) {
            segundaTable.addCell(new Paragraph("\nPERÍODO  – " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getInicioFiscalizado()) + " à " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getFinalFiscalizado()) + "\n\n", fonteSubTitulo));
        } else {
            segundaTable.addCell(new Paragraph("\nPERÍODO  – " + "__/__/____ à __/__/____" + "\n\n", fonteSubTitulo));
        }
        document.add(segundaTable);

        PdfPTable terceiraTable = new PdfPTable(1);
        terceiraTable.getDefaultCell().setBorder(0);
        Paragraph paragraphGlobal = new Paragraph("1) DESCRIÇÃO DO CONTRATO:\n\n", fonteRegularNegrito);
        paragraphGlobal.add(new Chunk(" Contrato de Serviços que entre si celebram a(s) instituição(ões):\n\n", fonteRegular));
        terceiraTable.addCell(paragraphGlobal);
        document.add(terceiraTable);

        PdfPTable quartaTable = new PdfPTable(2);
        quartaTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        quartaTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        quartaTable.getDefaultCell().setBackgroundColor(new BaseColor(204, 203, 200));
        quartaTable.addCell(new Paragraph("Nome", fonteSubTitulo));
        quartaTable.addCell(new Paragraph("CNPJ/CPF", fonteSubTitulo));

        document.add(quartaTable);

        PdfPTable quintaTable = new PdfPTable(2);
        quintaTable.addCell(new Paragraph(contratado.getNome(), fonteSubTitulo));
        if (contratado.getNatureza().equals(NaturezaEnum.FISICA)) {
            quintaTable.addCell(new Paragraph(contratado.getCpf(), fonteRegular));
        } else {
            quintaTable.addCell(new Paragraph(contratado.getCnpj(), fonteRegular));
        }

        document.add(quintaTable);

        PdfPTable sextaTable = new PdfPTable(1);
        sextaTable.getDefaultCell().setBorder(0);
        paragraphGlobal = new Paragraph("\n\nO presente contrato tem como objeto ", fonteRegular);
        if (Utils.isNotEmpty(contrato.getObjetoContrato())) {
            paragraphGlobal.add(new Chunk(contrato.getObjetoContrato() + "\n\n", fonteRegular));
        }
        sextaTable.addCell(paragraphGlobal);
        document.add(sextaTable);

        PdfPTable setimaTable = new PdfPTable(1);
        setimaTable.getDefaultCell().setBorder(0);
        paragraphGlobal = new Paragraph("2) FINALIDADE: \n\n", fonteRegularNegrito);
        paragraphGlobal.add(new Chunk(" Receber definitivamente o contrato " + contrato.getNumeroProcesso() + " assinado em " + contrato.getDataAssinatura() + ", com valor estimado de R$ " + contrato.getValor() + " (" + valorPorExtenso(new Double(contrato.getValor() + "")) + ").\n\n", fonteRegular));
        setimaTable.addCell(paragraphGlobal);
        document.add(setimaTable);

        PdfPTable oitavaTable = new PdfPTable(1);
        oitavaTable.getDefaultCell().setBorder(0);
        paragraphGlobal = new Paragraph("3) TERMOS ADITIVOS E APOSTILAMENTOS: \n\n", fonteRegularNegrito);
        paragraphGlobal.add(new Chunk("    Informamos que durante o período a que se refere o presente relatório, foram celebrados " + acrescimo.size() + " Termo(s) Aditivo(s) e " + termosApostilamentos.size() + " Termo(s) de Apostilamento, conforme descrito abaixo:\n"
                + "\n", fonteRegular));
        if (Utils.isNotEmpty(acrescimo)) {
            paragraphGlobal.add(new Chunk("Termos Aditivo, tendo como objeto : " + contrato.getNumeroProcesso() + "\n\n", fonteRegularNegrito));
            int termo = 1;
            for (Acrescimo ac : acrescimo) {
                paragraphGlobal.add(new Chunk("- " + termo + "º termo aditivo de número : " + ac.getNumeroTermo() + "\n\n", fonteRegular));
                termo = termo + 1;
            }
        }
        if (Utils.isNotEmpty(termosApostilamentos)) {

            paragraphGlobal.add(new Chunk("Termos Apostilamentos, tendo como objeto : " + contrato.getNumeroProcesso() + "\n\n", fonteRegularNegrito));
            int termo = 1;
            for (Repactuacao rp : termosApostilamentos) {
                paragraphGlobal.add(new Chunk("- " + termo + "º termo apostilamento de número : " + rp.getNumeroTermo() + "\n\n", fonteRegular));
                termo = termo + 1;

            }
        }
        oitavaTable.addCell(paragraphGlobal);
        document.add(oitavaTable);

        PdfPTable nonaTable = new PdfPTable(1);
        nonaTable.getDefaultCell().setBorder(0);
        paragraphGlobal = new Paragraph("4) PRESTAÇÃO DE CONTA: \n\n", fonteRegularNegrito);
        paragraphGlobal.add(new Chunk(" Gestor do Contrato: " + user.getNome() + ", designado pela Instrução de Serviço nº __ de __ de _____ de 20__.\n\n", fonteRegular));
        nonaTable.addCell(paragraphGlobal);
        document.add(nonaTable);

        PdfPTable decimaTable = new PdfPTable(1);
        decimaTable.getDefaultCell().setBorder(0);
        paragraphGlobal = new Paragraph("4.1 - RESULTADOS DAS ATIVIDADES \n\n", fonteRegularNegrito);
        if (Utils.isNotEmpty(nota.getDescricao())) {
            paragraphGlobal.add(new Chunk(nota.getDescricao() + "\n\n", fonteRegular));
            decimaTable.addCell(paragraphGlobal);
        } else {
            paragraphGlobal.add(new Chunk("Não houve resultados nas atividades.\n\n", fonteRegular));
            decimaTable.addCell(paragraphGlobal);
        }

        document.add(decimaTable);

        PdfPTable decimaPrimeiraTable = new PdfPTable(1);
        decimaPrimeiraTable.getDefaultCell().setBorder(0);
        paragraphGlobal = new Paragraph("CRONOGRAMA DESEMBOLSO – PAGAMENTO DE FATURAS \n\n", fonteRegularNegrito);
        decimaPrimeiraTable.addCell(paragraphGlobal);
        document.add(decimaPrimeiraTable);
        PdfPTable headerPagamentosTable = new PdfPTable(3);
        headerPagamentosTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        headerPagamentosTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        headerPagamentosTable.getDefaultCell().setBackgroundColor(new BaseColor(204, 203, 200));
        headerPagamentosTable.addCell(new Paragraph("Nº NOTA FISCAL", fonteSubTitulo));
        headerPagamentosTable.addCell(new Paragraph("DATA DA NOTA FISCAL", fonteSubTitulo));
        headerPagamentosTable.addCell(new Paragraph("VALOR (R$)", fonteSubTitulo));

        document.add(headerPagamentosTable);
        if (Utils.isNotEmpty(notas)) {
            BigDecimal valorTotal = new BigDecimal("0");
            for (NotaFiscal n : notas) {
                PdfPTable pagamentosTable = new PdfPTable(3);
                pagamentosTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                pagamentosTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
                if (n.getNumero() != null) {
                    pagamentosTable.addCell(new Paragraph(n.getNumero().toString(), fonteSubTitulo));
                } else {
                    pagamentosTable.addCell(new Paragraph(".", fonteBranca));
                }
                if (Utils.isNotEmpty(n.getDataPagamento())) {
                    pagamentosTable.addCell(new Paragraph(DateUtils.format(DateUtils.DD_MM_YYYY, n.getDataPagamento()), fonteSubTitulo));
                } else {
                    pagamentosTable.addCell(new Paragraph(".", fonteBranca));
                }
                if (Utils.isNotEmpty(n.getValor())) {
                    pagamentosTable.addCell(new Paragraph(n.getValor() + "", fonteSubTitulo));
                } else {
                    pagamentosTable.addCell(new Paragraph(".", fonteBranca));

                }
                document.add(pagamentosTable);
                valorTotal = valorTotal.add(new BigDecimal(n.getValor() + ""));
            }
            PdfPTable valorTable = new PdfPTable(1);
            paragraphGlobal = new Paragraph("Valor Total do Desembolso ", fonteRegularNegrito);
            paragraphGlobal.add(new Chunk(valorTotal + " R$", fonteRegular));
            valorTable.addCell(paragraphGlobal);

            document.add(valorTable);
        } else {
            PdfPTable pagamentosTable = new PdfPTable(3);
            pagamentosTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            pagamentosTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);

            pagamentosTable.addCell(new Paragraph(".", fonter));
            pagamentosTable.addCell(new Paragraph(".", fonter));
            pagamentosTable.addCell(new Paragraph(".", fonter));

            document.add(pagamentosTable);
        }
        PdfPTable decimaSegundaTable = new PdfPTable(1);
        decimaSegundaTable.getDefaultCell().setBorder(0);
        paragraphGlobal = new Paragraph("\n\n4.3. – GARANTIA CONTRATUAL \n\n", fonteRegularNegrito);
        if (Utils.isNotEmpty(nota.getDescricao())) {
            paragraphGlobal.add(new Chunk(nota.getGarantiaContratual() + "\n\n", fonteRegular));
        } else {
            paragraphGlobal.add(new Chunk("Não houve garantia contratual.\n\n", fonteRegular));
        }
        decimaSegundaTable.addCell(paragraphGlobal);
        document.add(decimaSegundaTable);

        PdfPTable decimaTerceiraTable = new PdfPTable(1);
        decimaTerceiraTable.getDefaultCell().setBorder(0);
        paragraphGlobal = new Paragraph("5) DECLARAÇÃO DE QUITAÇÃO \n\n", fonteRegularNegrito);
        paragraphGlobal.add(new Chunk(" Declaro para devidos fins que todos os débitos e obrigações originados pelo contrato nº " + contrato.getNumeroProcesso() + ", com a empresa " + contrato.getContratado().getNome() + " foram quitados, conforme documento X (informar documento SEI ou folha do processo físico digitalizado.):\n\n", fonteRegular));
        decimaTerceiraTable.addCell(paragraphGlobal);
        document.add(decimaTerceiraTable);

        PdfPTable decimaQuartaTable = new PdfPTable(1);
        decimaQuartaTable.getDefaultCell().setBorder(0);
        paragraphGlobal = new Paragraph("6) CONCLUSÃO \n\n", fonteRegularNegrito);

        if (Utils.isNotEmpty(nota.getInicioFiscalizado()) && Utils.isNotEmpty(nota.getFinalFiscalizado())) {
            paragraphGlobal.add(new Chunk("A empresa: " + contrato.getContratado().getNome() + ". Contratada com objetivo de " + contrato.getObjetoContrato() + ", conforme contrato " + contrato.getNumeroProcesso() + ", executou, no período de " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getInicioFiscalizado()) + " a " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getFinalFiscalizado()) + " as suas atividades (informe o resultado da prestação do serviço).\n\n ", fonteRegular));
        } else {
            paragraphGlobal.add(new Chunk("A empresa: " + contrato.getContratado().getNome() + ". Contratada com objetivo de " + contrato.getObjetoContrato() + ", conforme contrato " + contrato.getNumeroProcesso() + ", executou, no período de " + "__/__/____ a __/__/____" + " as suas atividades (informe o resultado da prestação do serviço).\n\n ", fonteRegular));
        }
        decimaQuartaTable.addCell(paragraphGlobal);
        document.add(decimaQuartaTable);

        PdfPTable decimaQuintaTable = new PdfPTable(1);
        decimaQuintaTable.getDefaultCell().setBorder(0);
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
        paragraphGlobal = new Paragraph(textoLocal + "\n\n", fonteRegularNegrito);
        decimaQuintaTable.addCell(paragraphGlobal);
        document.add(decimaQuintaTable);

        PdfPTable ultimaTable = new PdfPTable(1);
        ultimaTable.getDefaultCell().setBorder(0);
        ultimaTable.getDefaultCell().setPadding(0);
        ultimaTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        ultimaTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        paragraphGlobal = new Paragraph(user.getNome() + "\n", fonteRegularNegrito);
        ultimaTable.addCell(paragraphGlobal);
        paragraphGlobal = new Paragraph("Gestor do contrato " + contrato.getNumeroProcesso() + "\n", fonteRegularNegrito);
        ultimaTable.addCell(paragraphGlobal);
        if (Utils.isNotEmpty(nota.getInicioFiscalizado()) && Utils.isNotEmpty(nota.getFinalFiscalizado())) {
            paragraphGlobal = new Paragraph("Período de Gestão de " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getInicioFiscalizado()) + " à " + DateUtils.format(DateUtils.DD_MM_YYYY, nota.getFinalFiscalizado()) + "\n", fonteRegularNegrito
            );
        } else {
            paragraphGlobal = new Paragraph("Período de Gestão de " + "__/__/____ à __/__/____" + "\n", fonteRegularNegrito
            );
        }

        ultimaTable.addCell(paragraphGlobal);
        document.add(ultimaTable);

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
