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
    private Integer NumeroContrato;
    private Integer DOU;
    private TipoContrato tipoContrato;
    private Date dataInicio;
    private Date dataFimado;
    private Date dataFirmado;
    private Date dataRenovacao;
    private boolean possuiTempoDeterminado;
    private String objetivo;
    private String anexo;
    private String fiscal;
    private BigInteger valor;
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

    public Integer getNumeroContrato() {
        return NumeroContrato;
    }

    public void setNumeroContrato(Integer NumeroContrato) {
        this.NumeroContrato = NumeroContrato;
    }

    public Integer getDOU() {
        return DOU;
    }

    public void setDOU(Integer DOU) {
        this.DOU = DOU;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
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

    public void setDataFimado(Date dataFimado) {
        this.dataFimado = dataFimado;
    }

    public Date getDataFirmado() {
        return dataFirmado;
    }

    public void setDataFirmado(Date dataFirmado) {
        this.dataFirmado = dataFirmado;
    }

    public boolean isPossuiTempoDeterminado() {
        return possuiTempoDeterminado;
    }

    public void setPossuiTempoDeterminado(boolean possuiTempoDeterminado) {
        this.possuiTempoDeterminado = possuiTempoDeterminado;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataRenovacao() {
        return dataRenovacao;
    }

    public void setDataRenovacao(Date dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
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
