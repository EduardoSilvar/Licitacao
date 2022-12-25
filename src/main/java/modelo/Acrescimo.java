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
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;


/**
 *
 * @author dylan
 *//**
 *
 * @author dylan
 */
import javax.persistence.OneToMany;
@Entity
public class Acrescimo implements Serializable{
    @Id
    @SequenceGenerator(sequenceName = "seq_acrescimo", name = "seq_acrescimo", allocationSize = 1)
    @GeneratedValue(generator = "seq_acrescimo", strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Contrato contrato;
    private Long numeroTermo;
    private BigDecimal valor;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAssinatura;
    @ManyToOne
    private Usuario fiscal;
    private String descricao;
    @OneToMany
    private List<Anexo> anexos;
    private boolean ativo = true;

    public Acrescimo() {
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(Date dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.contrato);
        hash = 31 * hash + Objects.hashCode(this.numeroTermo);
        hash = 31 * hash + Objects.hashCode(this.valor);
        hash = 31 * hash + Objects.hashCode(this.dataAssinatura);
        hash = 31 * hash + Objects.hashCode(this.fiscal);
        hash = 31 * hash + Objects.hashCode(this.descricao);
        hash = 31 * hash + Objects.hashCode(this.anexos);
        hash = 31 * hash + (this.ativo ? 1 : 0);
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
        final Acrescimo other = (Acrescimo) obj;
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
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.dataAssinatura, other.dataAssinatura)) {
            return false;
        }
        if (!Objects.equals(this.fiscal, other.fiscal)) {
            return false;
        }
        return Objects.equals(this.anexos, other.anexos);
    }

    @Override
    public String toString() {
        return "Acrescimo{" + "id=" + id + ", contrato=" + contrato + ", numeroTermo=" + numeroTermo + ", valor=" + valor + ", dataAssinatura=" + dataAssinatura + ", fiscal=" + fiscal + ", descricao=" + descricao + ", anexo=" + anexos + ", ativo=" + ativo + '}';
    }


}