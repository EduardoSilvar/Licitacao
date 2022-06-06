/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author eduardo
 */
@Entity
public class Contrato implements Serializable {
    @Id
    @SequenceGenerator(sequenceName = "seq_contrato", name = "seq_contrato", allocationSize = 1)
    @GeneratedValue(generator = "seq_contrato", strategy = GenerationType.AUTO)
    private Long id;
    private Contratado contratado;
    private BigInteger NumeroContrato;
    private BigInteger NumeroProcesso;
    private BigInteger NumeroLicitacao;
    //private Integer DOU;
    private TipoContrato tipoContrato;
    private TipoLicitacao tipoLicitacao;
    private Integer valor;
    private boolean possuiTempoDeterminado;
    private Date dataInicio;
    private Date dataFinal;
    private Date dataAssinatura;
    private Date dataRenovacao;
    private Integer objetoContrato;
    private String fiscalContrato;
    private String descricao;
    private StatusContrato status;
    private String banco;
    private Integer numeroConta;
    private Integer digito;
    private Integer agencia;
    private Integer operacao;
    private Integer saldoInicial;
    private boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contratado getContratado() {
        return contratado;
    }

    public void setContratado(Contratado contratado) {
        this.contratado = contratado;
    }

    public BigInteger getNumeroContrato() {
        return NumeroContrato;
    }

    public void setNumeroContrato(BigInteger NumeroContrato) {
        this.NumeroContrato = NumeroContrato;
    }

    public BigInteger getNumeroProcesso(){
        return NumeroProcesso;
    }

    public void setNumeroProcesso(BigInteger NumeroProcesso){
        this.NumeroProcesso = NumeroProcesso;
    }

    public BigInteger getNumeroLicitacao(){
        return NumeroLicitacao;
    }

    public void NumeroLicitacao(BigInteger NumeroLicitacao){
        this.NumeroLicitacao = NumeroLicitacao;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    
    public TipoLicitacao getTipoLicitacao(){
        return tipoLicitacao;
    }

    public void setTipoLicitacao(TipoLicitacao tipoLicitacao){
        this.tipoLicitacao = tipoLicitacao;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
    public boolean isPossuiTempoDeterminado() {
        return possuiTempoDeterminado;
    }

    public void setPossuiTempoDeterminado(boolean possuiTempoDeterminado) {
        this.possuiTempoDeterminado = possuiTempoDeterminado;
    }
    
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFimado() {
        return dataFimado;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataAssinatura() {
        return dataFirmado;
    }
    
    public void setDataAssinatura(Date dataAssinatura) {
        this.dataFirmado = dataFirmado;
    }

    public Date getDataRenovacao() {
        return dataRenovacao;
    }
    
    public void setDataRenovacao(Date dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    public Integer getObjetoContrato() {
        return objetivo;
    }

    public void setObjetoContrato(Integer objetoContrato) {
        this.objetoContrato = objetoContrato;
    }

    public String getFiscalContrato() {
        return fiscalContrato;
    }

    public void setFiscalContrato(String fiscalContrato) {
        this.fiscalContrato = fiscalContrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusContrato getStatus(){
        return status;
    }

    public void setStatus(StatusContrato status){
        this.status = status;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Integer getDigito() {
        return digito;
    }

    public void setDigito(Integer digito) {
        this.digito = digito;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }
    
    public Integer getOperacao() {
        return operacao;
    }

    public void setOperacao(Integer operacao) {
        this.operacao = operacao;
    }

    public Integer getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Integer saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
    
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }


    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.contratado);
        hash = 83 * hash + Objects.hashCode(this.NumeroContrato);
        hash = 83 * hash + Objects.hashCode(this.DOU);
        hash = 83 * hash + Objects.hashCode(this.tipoContrato);
        hash = 83 * hash + Objects.hashCode(this.dataInicio);
        hash = 83 * hash + Objects.hashCode(this.dataFimado);
        hash = 83 * hash + Objects.hashCode(this.dataFirmado);
        hash = 83 * hash + Objects.hashCode(this.dataRenovacao);
        hash = 83 * hash + (this.possuiTempoDeterminado ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.objetivo);
        hash = 83 * hash + Objects.hashCode(this.anexo);
        hash = 83 * hash + Objects.hashCode(this.fiscal);
        hash = 83 * hash + Objects.hashCode(this.valor);
        hash = 83 * hash + (this.ativo ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contrato other = (Contrato) obj;
        if (this.possuiTempoDeterminado != other.possuiTempoDeterminado) {
            return false;
        }
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.objetivo, other.objetivo)) {
            return false;
        }
        if (!Objects.equals(this.anexo, other.anexo)) {
            return false;
        }
        if (!Objects.equals(this.fiscal, other.fiscal)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.contratado, other.contratado)) {
            return false;
        }
        if (!Objects.equals(this.NumeroContrato, other.NumeroContrato)) {
            return false;
        }
        if (!Objects.equals(this.DOU, other.DOU)) {
            return false;
        }
        if (!Objects.equals(this.tipoContrato, other.tipoContrato)) {
            return false;
        }
        if (!Objects.equals(this.dataInicio, other.dataInicio)) {
            return false;
        }
        if (!Objects.equals(this.dataFimado, other.dataFimado)) {
            return false;
        }
        if (!Objects.equals(this.dataFirmado, other.dataFirmado)) {
            return false;
        }
        if (!Objects.equals(this.dataRenovacao, other.dataRenovacao)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        return true;
    }

  

}
