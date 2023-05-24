/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modelo;

/**
 *
 * @author eduardo
 */
public enum TipoAnexo {
    CONTRATO("Contrato", "Contrato/"),
    NOTAFISCAL("Nota fiscal", "Nota_fiscal/"),
    ACRESCIMO("Acrescimo", "Acrescimo/"),
    RENOVACAO("Renovacao", "Renovacao/"),
    REPACTUACAO("Repactuacao", "repactuacao/"),
    CABECALHO_RODAPE("Cabecalho_Rodape", "cabecalho_rodape/"),
    SUPRESSAO("Supressao", "Supressao/");

    private String nome;
    private String url;

    private TipoAnexo(String nome, String url) {
        this.nome = nome;
        this.url = url;
    }

    public static TipoAnexo valueString(String valor) {
        for (TipoAnexo o : values()) {
            if (o.getNome().equals(valor)) {
                return o;
            }
        }
        return null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
