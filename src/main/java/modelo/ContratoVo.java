/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import Enum.StatusContrato;

/**
 *
 * @author eduardo
 */
public class ContratoVo {

    private final String nome;

    private final Long quantidade;

    public ContratoVo(StatusContrato status, Long quantidade) {
        this.nome = status.getStatus();
        this.quantidade = quantidade;
    }

    public ContratoVo(TipoContrato tipo, Long quantidade) {
        this.nome = tipo.getNome();
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public Long getQuantidade() {
        return quantidade;
    }

}
