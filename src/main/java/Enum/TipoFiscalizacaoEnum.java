/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enum;

/**
 *
 * @author nikolaimikkeldylanmonteirosmith
 */
public enum TipoFiscalizacaoEnum {
    
    INDIVIDUAL("Individual"),
    COMISSAO("Comissão");
    
    String nome;
    
    private TipoFiscalizacaoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
