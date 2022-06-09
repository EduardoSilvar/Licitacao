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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

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
    @OneToOne
    private Contratado contratado;
    private BigInteger NumeroContrato;
    private BigInteger NumeroProcesso;
    private BigInteger NumeroLicitacao;
    //private Integer DOU;
    @OneToOne
    private TipoContrato tipoContrato;
    @OneToOne
    private TipoLicitacao tipoLicitacao;
    private Integer valor;
    private boolean possuiTempoDeterminado;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataFinal;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAssinatura;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataRenovacao;
    private Integer objetoContrato;
    @ManyToOne
    private Usuario fiscalContrato;
    private String descricao;
    @OneToOne
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

    public BigInteger getNumeroProcesso() {
        return NumeroProcesso;
    }

    public void setNumeroProcesso(BigInteger NumeroProcesso) {
        this.NumeroProcesso = NumeroProcesso;
    }

    public BigInteger getNumeroLicitacao() {
        return NumeroLicitacao;
    }

    public void setNumeroLicitacao(BigInteger NumeroLicitacao) {
        this.NumeroLicitacao = NumeroLicitacao;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public TipoLicitacao getTipoLicitacao() {
        return tipoLicitacao;
    }

    public void setTipoLicitacao(TipoLicitacao tipoLicitacao) {
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

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(Date dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public Date getDataRenovacao() {
        return dataRenovacao;
    }

    public void setDataRenovacao(Date dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    public Integer getObjetoContrato() {
        return objetoContrato;
    }

    public void setObjetoContrato(Integer objetoContrato) {
        this.objetoContrato = objetoContrato;
    }

    public Usuario getFiscalContrato() {
        return fiscalContrato;
    }

    public void setFiscalContrato(Usuario fiscalContrato) {
        this.fiscalContrato = fiscalContrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusContrato getStatus() {
        return status;
    }

    public void setStatus(StatusContrato status) {
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
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.contratado);
        hash = 23 * hash + Objects.hashCode(this.NumeroContrato);
        hash = 23 * hash + Objects.hashCode(this.NumeroProcesso);
        hash = 23 * hash + Objects.hashCode(this.NumeroLicitacao);
        hash = 23 * hash + Objects.hashCode(this.tipoContrato);
        hash = 23 * hash + Objects.hashCode(this.tipoLicitacao);
        hash = 23 * hash + Objects.hashCode(this.valor);
        hash = 23 * hash + (this.possuiTempoDeterminado ? 1 : 0);
        hash = 23 * hash + Objects.hashCode(this.dataInicio);
        hash = 23 * hash + Objects.hashCode(this.dataFinal);
        hash = 23 * hash + Objects.hashCode(this.dataAssinatura);
        hash = 23 * hash + Objects.hashCode(this.dataRenovacao);
        hash = 23 * hash + Objects.hashCode(this.objetoContrato);
        hash = 23 * hash + Objects.hashCode(this.fiscalContrato);
        hash = 23 * hash + Objects.hashCode(this.descricao);
        hash = 23 * hash + Objects.hashCode(this.status);
        hash = 23 * hash + Objects.hashCode(this.banco);
        hash = 23 * hash + Objects.hashCode(this.numeroConta);
        hash = 23 * hash + Objects.hashCode(this.digito);
        hash = 23 * hash + Objects.hashCode(this.agencia);
        hash = 23 * hash + Objects.hashCode(this.operacao);
        hash = 23 * hash + Objects.hashCode(this.saldoInicial);
        hash = 23 * hash + (this.ativo ? 1 : 0);
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
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.banco, other.banco)) {
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
        if (!Objects.equals(this.NumeroProcesso, other.NumeroProcesso)) {
            return false;
        }
        if (!Objects.equals(this.NumeroLicitacao, other.NumeroLicitacao)) {
            return false;
        }
        if (!Objects.equals(this.tipoContrato, other.tipoContrato)) {
            return false;
        }
        if (!Objects.equals(this.tipoLicitacao, other.tipoLicitacao)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.dataInicio, other.dataInicio)) {
            return false;
        }
        if (!Objects.equals(this.dataFinal, other.dataFinal)) {
            return false;
        }
        if (!Objects.equals(this.dataAssinatura, other.dataAssinatura)) {
            return false;
        }
        if (!Objects.equals(this.dataRenovacao, other.dataRenovacao)) {
            return false;
        }
        if (!Objects.equals(this.objetoContrato, other.objetoContrato)) {
            return false;
        }
        if (!Objects.equals(this.fiscalContrato, other.fiscalContrato)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.numeroConta, other.numeroConta)) {
            return false;
        }
        if (!Objects.equals(this.digito, other.digito)) {
            return false;
        }
        if (!Objects.equals(this.agencia, other.agencia)) {
            return false;
        }
        if (!Objects.equals(this.operacao, other.operacao)) {
            return false;
        }
        return Objects.equals(this.saldoInicial, other.saldoInicial);
    }



    

    

    
    

  

}
