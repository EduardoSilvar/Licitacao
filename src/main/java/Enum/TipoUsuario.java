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
public enum TipoUsuario {
    ADMINISTRADOR("Administrador"),
    CFAP("Cfpa"),
    CPAC("Cpac"),
    CPAF("Cpaf"),
    DORC("Dorc");

    String nome;

    private TipoUsuario(String nome) {
        this.nome = nome;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
