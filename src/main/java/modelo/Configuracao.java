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
    private Long diasPraExpirar;
    private boolean finalizado;
    private boolean expirado;
    private boolean pertoExpira;
    private boolean vigencia;
    private boolean renovado;
    private boolean pago;
    private boolean iniciado;
    private boolean esperandoInicio;
    private boolean aprovado;
    private boolean cancelado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiasPraExpirar() {
        return diasPraExpirar;
    }

    public void setDiasPraExpirar(Long diasPraExpirar) {
        this.diasPraExpirar = diasPraExpirar;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public boolean isExpirado() {
        return expirado;
    }

    public void setExpirado(boolean expirado) {
        this.expirado = expirado;
    }

    public boolean isPertoExpira() {
        return pertoExpira;
    }

    public void setPertoExpira(boolean pertoExpira) {
        this.pertoExpira = pertoExpira;
    }

    public boolean isVigencia() {
        return vigencia;
    }

    public void setVigencia(boolean vigencia) {
        this.vigencia = vigencia;
    }

    public boolean isRenovado() {
        return renovado;
    }

    public void setRenovado(boolean renovado) {
        this.renovado = renovado;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }

    public boolean isEsperandoInicio() {
        return esperandoInicio;
    }

    public void setEsperandoInicio(boolean esperandoInicio) {
        this.esperandoInicio = esperandoInicio;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.diasPraExpirar);
        hash = 83 * hash + (this.finalizado ? 1 : 0);
        hash = 83 * hash + (this.expirado ? 1 : 0);
        hash = 83 * hash + (this.pertoExpira ? 1 : 0);
        hash = 83 * hash + (this.vigencia ? 1 : 0);
        hash = 83 * hash + (this.renovado ? 1 : 0);
        hash = 83 * hash + (this.pago ? 1 : 0);
        hash = 83 * hash + (this.iniciado ? 1 : 0);
        hash = 83 * hash + (this.esperandoInicio ? 1 : 0);
        hash = 83 * hash + (this.aprovado ? 1 : 0);
        hash = 83 * hash + (this.cancelado ? 1 : 0);
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
        if (this.finalizado != other.finalizado) {
            return false;
        }
        if (this.expirado != other.expirado) {
            return false;
        }
        if (this.pertoExpira != other.pertoExpira) {
            return false;
        }
        if (this.vigencia != other.vigencia) {
            return false;
        }
        if (this.renovado != other.renovado) {
            return false;
        }
        if (this.pago != other.pago) {
            return false;
        }
        if (this.iniciado != other.iniciado) {
            return false;
        }
        if (this.esperandoInicio != other.esperandoInicio) {
            return false;
        }
        if (this.aprovado != other.aprovado) {
            return false;
        }
        if (this.cancelado != other.cancelado) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.diasPraExpirar, other.diasPraExpirar)) {
            return false;
        }
        return true;
    }

    
}
