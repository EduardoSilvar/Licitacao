/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.*;

/**
 *
 * @author eduardo
 */
@Entity
public class StatusContrato implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_statusContrato", name = "seq_statusContrato", allocationSize = 1)
    @GeneratedValue(generator = "seq_statusContrato", strategy = GenerationType.AUTO)
    private Long id;
    private String status;
    private String descricao;
    private String cor;
    private String abreviacao;
    private boolean ativo = true;

    public StatusContrato() {
    }

    public StatusContrato(Long id, String status, String descricao, String cor) {
        this.id = id;
        this.status = status;
        this.descricao = descricao;
        this.cor = cor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.status);
        hash = 53 * hash + Objects.hashCode(this.descricao);
        hash = 53 * hash + Objects.hashCode(this.cor);
        hash = 53 * hash + Objects.hashCode(this.abreviacao);
        hash = 53 * hash + (this.ativo ? 1 : 0);
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
        final StatusContrato other = (StatusContrato) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.cor, other.cor)) {
            return false;
        }
        if (!Objects.equals(this.abreviacao, other.abreviacao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "StatusContrato{" + "id=" + id + ", status=" + status + ", descricao=" + descricao + ", cor=" + cor + ", ativo=" + ativo + '}';
    }

}
