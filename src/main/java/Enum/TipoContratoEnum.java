/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enum;

/**
 *
 * @author nikolaimikkeldylanmonteirosmith
 */
public enum TipoContratoEnum {
    
    SERVIÇOS_COMUM("Serviço"),
    SERVIÇOS_ENGENHARIA("Serviços de engenharia"),
    AQUISICAO_BENS("Aquisição de bens"),
    OBRAS("Obras"),
    DEMO_TERCERIZAÇÃO("Demo/Tercerização");
    
    
    String nome;
    
    private TipoContratoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
