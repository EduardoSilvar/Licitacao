/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import Enum.StatusContrato;
import java.math.BigDecimal;

/**
 *
 * @author eduardo
 */
public class ContratoVo {

    private final String nome;
    private final BigDecimal valor;
    private final Long quantidade;

    public ContratoVo(StatusContrato status, Long quantidade) {
        this.nome = status.getStatus();
        this.quantidade = quantidade;
        this.valor = new BigDecimal(0);
    }

    public ContratoVo(TipoContrato tipo, Long quantidade) {
        this.nome = tipo.getNome();
        this.quantidade = quantidade;
        this.valor = new BigDecimal(0);
    }

    public ContratoVo(StatusContrato status, Long quantidade, BigDecimal valor) {
        this.nome = status.getStatus();
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public ContratoVo(String status, BigDecimal valor) {
        this.nome = status;
        this.quantidade = 0l;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

}
