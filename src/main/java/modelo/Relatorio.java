/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Entity
public class Relatorio implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_relatorio", name = "seq_relatorio", allocationSize = 1)
    @GeneratedValue(generator = "seq_relatorio", strategy = GenerationType.SEQUENCE)
    private Long id;
    private boolean ativo;
    private String nome;
    @OneToOne(cascade = CascadeType.ALL)
    private Anexo logo;

    //Configuração do cabeçalho e rodape dos documentos
    private int margemDireitaCabecalho;
    private int margemEsquerdaCabecalho;

    private int margemDireitaRodape;
    private int margemEsquerdaRodape;

    private boolean posicaoImagemCabecalho;
    private boolean posicaoImagemRodape;
    private String urlImagem;
    private String urlImagemrodape;

    private String rodape;
    private String cabecalho;
    private int fontSizeRodape;
    private int fontSizeCabecalho;

    private String corRodape;
    private String corCabecalho;

    public Relatorio() {
    }

    public Relatorio(Long id, boolean ativo, String nome, Anexo logo, int margemDireitaCabecalho, int margemEsquerdaCabecalho, int margemDireitaRodape, int margemEsquerdaRodape, boolean posicaoImagemCabecalho, boolean posicaoImagemRodape, String urlImagem, String urlImagemrodape, String rodape, String cabecalho, int fontSizeRodape, int fontSizeCabecalho, String corRodape, String corCabecalho) {
        this.id = id;
        this.ativo = ativo;
        this.nome = nome;
        this.logo = logo;
        this.margemDireitaCabecalho = margemDireitaCabecalho;
        this.margemEsquerdaCabecalho = margemEsquerdaCabecalho;
        this.margemDireitaRodape = margemDireitaRodape;
        this.margemEsquerdaRodape = margemEsquerdaRodape;
        this.posicaoImagemCabecalho = posicaoImagemCabecalho;
        this.posicaoImagemRodape = posicaoImagemRodape;
        this.urlImagem = urlImagem;
        this.urlImagemrodape = urlImagemrodape;
        this.rodape = rodape;
        this.cabecalho = cabecalho;
        this.fontSizeRodape = fontSizeRodape;
        this.fontSizeCabecalho = fontSizeCabecalho;
        this.corRodape = corRodape;
        this.corCabecalho = corCabecalho;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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

    public List<Anexo> getAnexos() {
        List<Anexo> anexos = new ArrayList<>();
        if (Utils.isNotEmpty(logo)) {
            anexos.add(logo);
        }
        return anexos;
    }

    public Anexo getLogo() {
        return logo;
    }

    public void setLogo(Anexo logo) {
        this.logo = logo;
    }

    public int getMargemDireitaCabecalho() {
        return margemDireitaCabecalho;
    }

    public void setMargemDireitaCabecalho(int margemDireitaCabecalho) {
        this.margemDireitaCabecalho = margemDireitaCabecalho;
    }

    public int getMargemEsquerdaCabecalho() {
        return margemEsquerdaCabecalho;
    }

    public void setMargemEsquerdaCabecalho(int margemEsquerdaCabecalho) {
        this.margemEsquerdaCabecalho = margemEsquerdaCabecalho;
    }

    public int getMargemDireitaRodape() {
        return margemDireitaRodape;
    }

    public void setMargemDireitaRodape(int margemDireitaRodape) {
        this.margemDireitaRodape = margemDireitaRodape;
    }

    public int getMargemEsquerdaRodape() {
        return margemEsquerdaRodape;
    }

    public void setMargemEsquerdaRodape(int margemEsquerdaRodape) {
        this.margemEsquerdaRodape = margemEsquerdaRodape;
    }

    public boolean isPosicaoImagemCabecalho() {
        return posicaoImagemCabecalho;
    }

    public void setPosicaoImagemCabecalho(boolean posicaoImagemCabecalho) {
        this.posicaoImagemCabecalho = posicaoImagemCabecalho;
    }

    public boolean isPosicaoImagemRodape() {
        return posicaoImagemRodape;
    }

    public void setPosicaoImagemRodape(boolean posicaoImagemRodape) {
        this.posicaoImagemRodape = posicaoImagemRodape;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getUrlImagemrodape() {
        return urlImagemrodape;
    }

    public void setUrlImagemrodape(String urlImagemrodape) {
        this.urlImagemrodape = urlImagemrodape;
    }

    public String getRodape() {
        return rodape;
    }

    public void setRodape(String rodape) {
        this.rodape = rodape;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public int getFontSizeRodape() {
        return fontSizeRodape;
    }

    public void setFontSizeRodape(int fontSizeRodape) {
        this.fontSizeRodape = fontSizeRodape;
    }

    public int getFontSizeCabecalho() {
        return fontSizeCabecalho;
    }

    public void setFontSizeCabecalho(int fontSizeCabecalho) {
        this.fontSizeCabecalho = fontSizeCabecalho;
    }

    public String getCorRodape() {
        return corRodape;
    }

    public void setCorRodape(String corRodape) {
        this.corRodape = corRodape;
    }

    public String getCorCabecalho() {
        return corCabecalho;
    }

    public void setCorCabecalho(String corCabecalho) {
        this.corCabecalho = corCabecalho;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.nome);
        hash = 79 * hash + Objects.hashCode(this.logo);
        hash = 79 * hash + this.margemDireitaCabecalho;
        hash = 79 * hash + this.margemEsquerdaCabecalho;
        hash = 79 * hash + this.margemDireitaRodape;
        hash = 79 * hash + this.margemEsquerdaRodape;
        hash = 79 * hash + (this.posicaoImagemCabecalho ? 1 : 0);
        hash = 79 * hash + (this.posicaoImagemRodape ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.urlImagem);
        hash = 79 * hash + Objects.hashCode(this.urlImagemrodape);
        hash = 79 * hash + Objects.hashCode(this.rodape);
        hash = 79 * hash + Objects.hashCode(this.cabecalho);
        hash = 79 * hash + this.fontSizeRodape;
        hash = 79 * hash + this.fontSizeCabecalho;
        hash = 79 * hash + Objects.hashCode(this.corRodape);
        hash = 79 * hash + Objects.hashCode(this.corCabecalho);
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
        final Relatorio other = (Relatorio) obj;
        if (this.margemDireitaCabecalho != other.margemDireitaCabecalho) {
            return false;
        }
        if (this.margemEsquerdaCabecalho != other.margemEsquerdaCabecalho) {
            return false;
        }
        if (this.margemDireitaRodape != other.margemDireitaRodape) {
            return false;
        }
        if (this.margemEsquerdaRodape != other.margemEsquerdaRodape) {
            return false;
        }
        if (this.posicaoImagemCabecalho != other.posicaoImagemCabecalho) {
            return false;
        }
        if (this.posicaoImagemRodape != other.posicaoImagemRodape) {
            return false;
        }
        if (this.fontSizeRodape != other.fontSizeRodape) {
            return false;
        }
        if (this.fontSizeCabecalho != other.fontSizeCabecalho) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.urlImagem, other.urlImagem)) {
            return false;
        }
        if (!Objects.equals(this.urlImagemrodape, other.urlImagemrodape)) {
            return false;
        }
        if (!Objects.equals(this.rodape, other.rodape)) {
            return false;
        }
        if (!Objects.equals(this.cabecalho, other.cabecalho)) {
            return false;
        }
        if (!Objects.equals(this.corRodape, other.corRodape)) {
            return false;
        }
        if (!Objects.equals(this.corCabecalho, other.corCabecalho)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.logo, other.logo);
    }

    @Override
    public String toString() {
        return "Relatorio{" + "id=" + id + ", nome=" + nome + ", logo=" + logo + ", margemDireitaCabecalho=" + margemDireitaCabecalho + ", margemEsquerdaCabecalho=" + margemEsquerdaCabecalho + ", margemDireitaRodape=" + margemDireitaRodape + ", margemEsquerdaRodape=" + margemEsquerdaRodape + ", posicaoImagemCabecalho=" + posicaoImagemCabecalho + ", posicaoImagemRodape=" + posicaoImagemRodape + ", urlImagem=" + urlImagem + ", urlImagemrodape=" + urlImagemrodape + ", rodape=" + rodape + ", cabecalho=" + cabecalho + ", fontSizeRodape=" + fontSizeRodape + ", fontSizeCabecalho=" + fontSizeCabecalho + ", corRodape=" + corRodape + ", corCabecalho=" + corCabecalho + '}';
    }

}
