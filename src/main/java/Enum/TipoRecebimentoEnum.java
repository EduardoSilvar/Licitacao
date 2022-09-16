/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enum;

/**
 *
 * @author nikolaimikkeldylanmonteirosmith
 */
public enum TipoRecebimentoEnum {
    
    PROVISORIO("Provisório"),
    DEFINITIVO("Definitivo");
    
    String nome;
    
    private TipoRecebimentoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
