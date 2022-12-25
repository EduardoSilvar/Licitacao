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
public class Repactuacao implements Serializable{
    @Id
    @SequenceGenerator(sequenceName = "seq_repactuacao", name = "seq_repactuacao", allocationSize = 1)
    @GeneratedValue(generator = "seq_repactuacao", strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Contrato contrato;
    private Long numeroTermo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAssinatura;
    private Boolean valorMudou = false;
    private BigDecimal valor;
    @ManyToOne
    private Usuario fiscal;
    private String descricao;
    @OneToMany
    private List<Anexo> anexos;
    private boolean ativo = true;

    public Repactuacao() {
    }

    public Repactuacao(Long id, Contrato contrato, Long numeroTermo, Date dataAssinatura, BigDecimal valor, Usuario fiscal, String descricao, List<Anexo> anexos) {
        this.id = id;
        this.contrato = contrato;
        this.numeroTermo = numeroTermo;
        this.dataAssinatura = dataAssinatura;
        this.valor = valor;
        this.fiscal = fiscal;
        this.descricao = descricao;
        this.anexos = anexos;
    }

    public List<Anexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexo> anexos) {
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

    public Date getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(Date dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
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

    public Usuario getFiscal() {
        return fiscal;
    }

    public void setFiscal(Usuario fiscal) {
        this.fiscal = fiscal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

   

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.contrato);
        hash = 19 * hash + Objects.hashCode(this.numeroTermo);
        hash = 19 * hash + Objects.hashCode(this.dataAssinatura);
        hash = 19 * hash + Objects.hashCode(this.valorMudou);
        hash = 19 * hash + Objects.hashCode(this.valor);
        hash = 19 * hash + Objects.hashCode(this.fiscal);
        hash = 19 * hash + Objects.hashCode(this.descricao);
        hash = 19 * hash + Objects.hashCode(this.anexos);
        hash = 19 * hash + (this.ativo ? 1 : 0);
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
        final Repactuacao other = (Repactuacao) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
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
        if (!Objects.equals(this.dataAssinatura, other.dataAssinatura)) {
            return false;
        }
        if (!Objects.equals(this.valorMudou, other.valorMudou)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.fiscal, other.fiscal)) {
            return false;
        }
        return Objects.equals(this.anexos, other.anexos);
    }

    @Override
    public String toString() {
        return "Repactuacao{" + "id=" + id + ", contrato=" + contrato + ", numeroTermo=" + numeroTermo + ", dataAssinatura=" + dataAssinatura + ", valorMudou=" + valorMudou + ", valor=" + valor + ", fiscal=" + fiscal + ", descricao=" + descricao + ", anexos=" + anexos + ", ativo=" + ativo + '}';
    }

    
}
