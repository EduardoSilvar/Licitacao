/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

/**
 *
 * @author eduardo
 */
public enum GraficoEnum {
    Todos("Todos"),
    Vigencia_expirado("Vigência e Expirado");
    

    String nome;

    private GraficoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
