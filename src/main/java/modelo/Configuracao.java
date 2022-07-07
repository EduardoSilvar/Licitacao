/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Enum.GraficoEnum;
import Enum.StatusContrato;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author eduardo
 */
@Entity
public class Configuracao implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_configuracao", name = "seq_configuracao", allocationSize = 1)
    @GeneratedValue(generator = "seq_configuracao", strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private UnidadeOrganizacional unidadeOrganizacional;
    private Long diasPraExpirar;
    @Enumerated(EnumType.STRING)
    private StatusContrato primeiroStatus;
    @Enumerated(EnumType.STRING)
    private StatusContrato SegundoStatus;
    @Enumerated(EnumType.STRING)
    private StatusContrato terceiroStatus;
    @OneToMany
    private List<PedidoRenovacao> pedidosRenovacao;
    @Enumerated(EnumType.STRING)
    private GraficoEnum primeiroGrafico;
    @Enumerated(EnumType.STRING)
    private GraficoEnum segundoGrafico;
    @Enumerated(EnumType.STRING)
    private GraficoEnum terceiroGrafico;
    private boolean ativo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiasPraExpirar() {
        return diasPraExpirar;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

    public void setDiasPraExpirar(Long diasPraExpirar) {
        this.diasPraExpirar = diasPraExpirar;
    }

    public StatusContrato getPrimeiroStatus() {
        return primeiroStatus;
    }

    public void setPrimeiroStatus(StatusContrato primeiroStatus) {
        this.primeiroStatus = primeiroStatus;
    }

    public StatusContrato getSegundoStatus() {
        return SegundoStatus;
    }

    public void setSegundoStatus(StatusContrato SegundoStatus) {
        this.SegundoStatus = SegundoStatus;
    }

    public StatusContrato getTerceiroStatus() {
        return terceiroStatus;
    }

    public void setTerceiroStatus(StatusContrato terceiroStatus) {
        this.terceiroStatus = terceiroStatus;
    }

    public List<PedidoRenovacao> getPedidosRenovacao() {
        return pedidosRenovacao;
    }

    public void setPedidosRenovacao(List<PedidoRenovacao> pedidosRenovacao) {
        this.pedidosRenovacao = pedidosRenovacao;
    }

    public GraficoEnum getPrimeiroGrafico() {
        return primeiroGrafico;
    }

    public void setPrimeiroGrafico(GraficoEnum primeiroGrafico) {
        this.primeiroGrafico = primeiroGrafico;
    }

    public GraficoEnum getSegundoGrafico() {
        return segundoGrafico;
    }

    public void setSegundoGrafico(GraficoEnum segundoGrafico) {
        this.segundoGrafico = segundoGrafico;
    }

    public GraficoEnum getTerceiroGrafico() {
        return terceiroGrafico;
    }

    public void setTerceiroGrafico(GraficoEnum terceiroGrafico) {
        this.terceiroGrafico = terceiroGrafico;
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
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.unidadeOrganizacional);
        hash = 37 * hash + Objects.hashCode(this.diasPraExpirar);
        hash = 37 * hash + Objects.hashCode(this.primeiroStatus);
        hash = 37 * hash + Objects.hashCode(this.SegundoStatus);
        hash = 37 * hash + Objects.hashCode(this.terceiroStatus);
        hash = 37 * hash + Objects.hashCode(this.pedidosRenovacao);
        hash = 37 * hash + Objects.hashCode(this.primeiroGrafico);
        hash = 37 * hash + Objects.hashCode(this.segundoGrafico);
        hash = 37 * hash + Objects.hashCode(this.terceiroGrafico);
        hash = 37 * hash + (this.ativo ? 1 : 0);
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
        final Configuracao other = (Configuracao) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.unidadeOrganizacional, other.unidadeOrganizacional)) {
            return false;
        }
        if (!Objects.equals(this.diasPraExpirar, other.diasPraExpirar)) {
            return false;
        }
        if (this.primeiroStatus != other.primeiroStatus) {
            return false;
        }
        if (this.SegundoStatus != other.SegundoStatus) {
            return false;
        }
        if (this.terceiroStatus != other.terceiroStatus) {
            return false;
        }
        if (!Objects.equals(this.pedidosRenovacao, other.pedidosRenovacao)) {
            return false;
        }
        if (this.primeiroGrafico != other.primeiroGrafico) {
            return false;
        }
        if (this.segundoGrafico != other.segundoGrafico) {
            return false;
        }
        if (this.terceiroGrafico != other.terceiroGrafico) {
            return false;
        }
        return true;
    }

}
