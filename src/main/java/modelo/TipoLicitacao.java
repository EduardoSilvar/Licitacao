/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author eduardo
 */
@Entity
public class TipoLicitacao implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_tipoLicitacao", name = "seq_tipoLicitacao", allocationSize = 1)
    @GeneratedValue(generator = "seq_tipoLicitacao", strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;
    private String tipo;
    @OneToOne
    private UnidadeOrganizacional unidadeOrganizacional;
    private boolean ativo = true;

    public TipoLicitacao() {
    }

    public TipoLicitacao(Long id, String descricao, String tipo) {
        this.id = id;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.descricao);
        hash = 79 * hash + Objects.hashCode(this.tipo);
        hash = 79 * hash + Objects.hashCode(this.unidadeOrganizacional);
        hash = 79 * hash + (this.ativo ? 1 : 0);
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
        final TipoLicitacao other = (TipoLicitacao) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.unidadeOrganizacional, other.unidadeOrganizacional)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "TipoLicitacao{" + "id=" + id + ", descricao=" + descricao + ", tipo=" + tipo + ", ativo=" + ativo + '}';
    }

}
