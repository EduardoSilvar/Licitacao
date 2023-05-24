/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Macro;

/**
 *
 * @author eduardo
 */
public class ArquivoEstilizacaoPDF {

    public static String inicioHeaderBodyPDF() {
        return "<html><head><style>"
                + ".ql-align-left {text-align: left;}"
                + ".coordenadas {display: flex !important;flex-direction: row !important;}"
                + ".ql-align-center {text-align: center;}"
                + ".padding-top{padding-top: 10px !important;}"
                + ".fundoCinza{background: grey !important;width: 650px !important;}"
                + ".ql-align-right {text-align: right;}"
                + "p{margin: 0px !important;padding: 0px !important;}"
                + ".ql-align-justify {text-align: justify;}"
                + ".ql-size-large{font-size: 20px;font-weight: normal  !important;}"
                + ".ql-size-huge{font-size: 25px;font-weight: normal !important;}"
                + "</style>"
                + "</head><body>";
    }

    public static String corpoPDF(String texto) {
        String textoPdf = inicioHeaderBodyPDF();
        textoPdf += texto;
        textoPdf += fimHeaderBodyPDF();
        return textoPdf;
    }

    public static String fimHeaderBodyPDF() {
        return "</body></html>";
    }

}
