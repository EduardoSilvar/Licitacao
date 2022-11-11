/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author eduardo
 */
@Entity
public class ModeloDocumento implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_modelo_documento", name = "seq_modelo_documento", allocationSize = 1)
    @GeneratedValue(generator = "seq_modelo_documento", strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private int margemCima;
    private boolean possuiTimbrado;
    private int margemDireita;
    private int margemBaixo;
    private boolean poosuiCabecalhoRodape;
    private int margemEsquerda;

    @Column(name = "texto", columnDefinition = "text")
    private String texto;
    @OneToOne
    private UnidadeOrganizacional orgao;
    @OneToOne
    private Relatorio cabecalho;

    @OneToOne(cascade = CascadeType.ALL)
    private Anexo papelTimbrado;
    @OneToOne
    private Relatorio rodape;

    public ModeloDocumento() {
    }

    public ModeloDocumento(Long id, String nome, int margemCima, boolean possuiTimbrado, int margemDireita, int margemBaixo, boolean poosuiCabecalhoRodape, int margemEsquerda, String texto, UnidadeOrganizacional orgao, Relatorio cabecalho, Anexo papelTimbrado, Relatorio rodape) {
        this.id = id;
        this.nome = nome;
        this.margemCima = margemCima;
        this.possuiTimbrado = possuiTimbrado;
        this.margemDireita = margemDireita;
        this.margemBaixo = margemBaixo;
        this.poosuiCabecalhoRodape = poosuiCabecalhoRodape;
        this.margemEsquerda = margemEsquerda;
        this.texto = texto;
        this.orgao = orgao;
        this.cabecalho = cabecalho;
        this.papelTimbrado = papelTimbrado;
        this.rodape = rodape;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMargemCima() {
        return margemCima;
    }

    public void setMargemCima(int margemCima) {
        this.margemCima = margemCima;
    }

    public boolean isPossuiTimbrado() {
        return possuiTimbrado;
    }

    public void setPossuiTimbrado(boolean possuiTimbrado) {
        this.possuiTimbrado = possuiTimbrado;
    }

    public int getMargemDireita() {
        return margemDireita;
    }

    public void setMargemDireita(int margemDireita) {
        this.margemDireita = margemDireita;
    }

    public int getMargemBaixo() {
        return margemBaixo;
    }

    public void setMargemBaixo(int margemBaixo) {
        this.margemBaixo = margemBaixo;
    }

    public boolean isPoosuiCabecalhoRodape() {
        return poosuiCabecalhoRodape;
    }

    public void setPoosuiCabecalhoRodape(boolean poosuiCabecalhoRodape) {
        this.poosuiCabecalhoRodape = poosuiCabecalhoRodape;
    }

    public int getMargemEsquerda() {
        return margemEsquerda;
    }

    public void setMargemEsquerda(int margemEsquerda) {
        this.margemEsquerda = margemEsquerda;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public UnidadeOrganizacional getOrgao() {
        return orgao;
    }

    public void setOrgao(UnidadeOrganizacional orgao) {
        this.orgao = orgao;
    }

    public Relatorio getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(Relatorio cabecalho) {
        this.cabecalho = cabecalho;
    }

    public Anexo getPapelTimbrado() {
        return papelTimbrado;
    }

    public void setPapelTimbrado(Anexo papelTimbrado) {
        this.papelTimbrado = papelTimbrado;
    }

    public Relatorio getRodape() {
        return rodape;
    }

    public void setRodape(Relatorio rodape) {
        this.rodape = rodape;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.nome);
        hash = 29 * hash + this.margemCima;
        hash = 29 * hash + (this.possuiTimbrado ? 1 : 0);
        hash = 29 * hash + this.margemDireita;
        hash = 29 * hash + this.margemBaixo;
        hash = 29 * hash + (this.poosuiCabecalhoRodape ? 1 : 0);
        hash = 29 * hash + this.margemEsquerda;
        hash = 29 * hash + Objects.hashCode(this.texto);
        hash = 29 * hash + Objects.hashCode(this.orgao);
        hash = 29 * hash + Objects.hashCode(this.cabecalho);
        hash = 29 * hash + Objects.hashCode(this.papelTimbrado);
        hash = 29 * hash + Objects.hashCode(this.rodape);
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
        final ModeloDocumento other = (ModeloDocumento) obj;
        if (this.margemCima != other.margemCima) {
            return false;
        }
        if (this.possuiTimbrado != other.possuiTimbrado) {
            return false;
        }
        if (this.margemDireita != other.margemDireita) {
            return false;
        }
        if (this.margemBaixo != other.margemBaixo) {
            return false;
        }
        if (this.poosuiCabecalhoRodape != other.poosuiCabecalhoRodape) {
            return false;
        }
        if (this.margemEsquerda != other.margemEsquerda) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.texto, other.texto)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.orgao, other.orgao)) {
            return false;
        }
        if (!Objects.equals(this.cabecalho, other.cabecalho)) {
            return false;
        }
        if (!Objects.equals(this.papelTimbrado, other.papelTimbrado)) {
            return false;
        }
        return Objects.equals(this.rodape, other.rodape);
    }

    @Override
    public String toString() {
        return "ModeloDocumento{" + "id=" + id + ", nome=" + nome + ", margemCima=" + margemCima + ", possuiTimbrado=" + possuiTimbrado + ", margemDireita=" + margemDireita + ", margemBaixo=" + margemBaixo + ", poosuiCabecalhoRodape=" + poosuiCabecalhoRodape + ", margemEsquerda=" + margemEsquerda + ", texto=" + texto + ", orgao=" + orgao + ", cabecalho=" + cabecalho + ", papelTimbrado=" + papelTimbrado + ", rodape=" + rodape + '}';
    }

}