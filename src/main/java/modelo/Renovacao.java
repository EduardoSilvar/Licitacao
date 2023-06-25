/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author dylan
 */
@Entity
public class Renovacao implements Serializable{
    @Id
    @SequenceGenerator(sequenceName = "seq_renovacao", name = "seq_renovacao", allocationSize = 1)
    @GeneratedValue(generator = "seq_renovacao", strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Contrato contrato;
    private Long numeroTermo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicial;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataFinal;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAssinatura;
    private Long proximaRenovacao;
    private Boolean valorMudou = false;
    private BigDecimal valor;
    private BigDecimal variacaoValor;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vigenciaAnterior;
    @ManyToOne
    private Usuario fiscal;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Anexo> anexos;
    private boolean ativo = true;

    public Renovacao() {
    }

    public Renovacao(Long id, Contrato contrato, Long numeroTermo, Date dataInicial, Date dataFinal, Date dataAssinatura, Long proximaRenovacao, BigDecimal valor, BigDecimal variacaoValor, Date vigenciaAnterior, Usuario fiscal, List<Anexo> anexos) {
        this.id = id;
        this.contrato = contrato;
        this.numeroTermo = numeroTermo;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.dataAssinatura = dataAssinatura;
        this.proximaRenovacao = proximaRenovacao;
        this.valor = valor;
        this.variacaoValor = variacaoValor;
        this.vigenciaAnterior = vigenciaAnterior;
        this.fiscal = fiscal;
        this.anexos = anexos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Long getNumeroTermo() {
        return numeroTermo;
    }

    public void setNumeroTermo(Long numeroTermo) {
        this.numeroTermo = numeroTermo;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
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

    public Long getProximaRenovacao() {
        return proximaRenovacao;
    }

    public void setProximaRenovacao(Long proximaRenovacao) {
        this.proximaRenovacao = proximaRenovacao;
    }

    public Boolean getValorMudou() {
        return valorMudou;
    }

    public void setValorMudou(Boolean valorMudou) {
        this.valorMudou = valorMudou;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getVigenciaAnterior() {
        return vigenciaAnterior;
    }

    public void setVigenciaAnterior(Date vigenciaAnterior) {
        this.vigenciaAnterior = vigenciaAnterior;
    }

    public Usuario getFiscal() {
        return fiscal;
    }

    public void setFiscal(Usuario fiscal) {
        this.fiscal = fiscal;
    }

    public List<Anexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexo> anexo) {
        this.anexos = anexo;
    }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getVariacaoValor() {
        return variacaoValor;
    }

    public void setVariacaoValor(BigDecimal variacaoValor) {
        this.variacaoValor = variacaoValor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.contrato);
        hash = 89 * hash + Objects.hashCode(this.numeroTermo);
        hash = 89 * hash + Objects.hashCode(this.dataInicial);
        hash = 89 * hash + Objects.hashCode(this.dataFinal);
        hash = 89 * hash + Objects.hashCode(this.dataAssinatura);
        hash = 89 * hash + Objects.hashCode(this.proximaRenovacao);
        hash = 89 * hash + Objects.hashCode(this.valorMudou);
        hash = 89 * hash + Objects.hashCode(this.valor);
        hash = 89 * hash + Objects.hashCode(this.variacaoValor);
        hash = 89 * hash + Objects.hashCode(this.vigenciaAnterior);
        hash = 89 * hash + Objects.hashCode(this.fiscal);
        hash = 89 * hash + Objects.hashCode(this.anexos);
        hash = 89 * hash + (this.ativo ? 1 : 0);
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
        final Renovacao other = (Renovacao) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.contrato, other.contrato)) {
            return false;
        }
        if (!Objects.equals(this.numeroTermo, other.numeroTermo)) {
            return false;
        }
        if (!Objects.equals(this.dataInicial, other.dataInicial)) {
            return false;
        }
        if (!Objects.equals(this.dataFinal, other.dataFinal)) {
            return false;
        }
        if (!Objects.equals(this.dataAssinatura, other.dataAssinatura)) {
            return false;
        }
        if (!Objects.equals(this.proximaRenovacao, other.proximaRenovacao)) {
            return false;
        }
        if (!Objects.equals(this.valorMudou, other.valorMudou)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.variacaoValor, other.variacaoValor)) {
            return false;
        }
        if (!Objects.equals(this.vigenciaAnterior, other.vigenciaAnterior)) {
            return false;
        }
        if (!Objects.equals(this.fiscal, other.fiscal)) {
            return false;
        }
        return Objects.equals(this.anexos, other.anexos);
    }

    @Override
    public String toString() {
        return "Renovacao{" + "id=" + id + ", contrato=" + contrato + ", numeroTermo=" + numeroTermo + ", dataInicial=" + dataInicial + ", dataFinal=" + dataFinal + ", dataAssinatura=" + dataAssinatura + ", proximaRenovacao=" + proximaRenovacao + ", valorMudou=" + valorMudou + ", valor=" + valor + ", variacaoValor=" + variacaoValor + ", vigenciaAnterior=" + vigenciaAnterior + ", fiscal=" + fiscal + ", anexos=" + anexos + ", ativo=" + ativo + '}';
    }
    
}
