/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enum;

/**
 *
 * @author eduardo
 */
public enum TipoCabecalhoRodape {
    CABECALHO("Cabeçalho"),
    RODAPE("Rodapé");

    private String tipo;

    private TipoCabecalhoRodape(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
