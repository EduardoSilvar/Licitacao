/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enum;

/**
 *
 * @author nikolaimikkeldylanmonteirosmith
 */
public enum TipoLicitacaoEnum {
    
    TOMADA_DE_PRECOS_LEI_8666("Tomada de preços - Lei 8.666"),
//    TOMADA_DE_PRECOS_LEI_14133("Tomada de preços - Lei 14.133"),
    CONVITE_LEI_8666("Convite - Lei 8.666"),
    DISPENSA_LEI_8666("Dispensa - Lei 8.666"),
    DISPENSA_LEI_14133("Dispensa - Lei 14.133"),
    DISPENSA_LEI_13019("Dispensa - Lei 13.019"),
    PREGÃO_LEI_8666("Pregão - Lei 8.666"),
    PREGÃO_LEI_14133("Pregão - Lei 14.133"),
    CONCORRÊNCIA_LEI_8666("Concorrência - Lei 8.666"),
    CONCORRÊNCIA_LEI_14133("Concorrência - Lei 14.133"),
     CONCORRÊNCIA_LEI_13019("Chamado público"),
//    CONCORRÊNCIA_LEI_13019("Concorrência - Lei 13.019"),
    INEXIGIBILIDADE_LEI_8666("Inexigibilidade - Lei 8.666"),
    INEXIGIBILIDADE_LEI_14133("Inexigibilidade - Lei 14.133"),
    INEXIGIBILIDADE_LEI_13019("Inexigibilidade - Lei 13.019");
    
    String nome;
    
    private TipoLicitacaoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
