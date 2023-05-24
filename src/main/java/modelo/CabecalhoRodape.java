/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import Enum.TipoCabecalhoRodape;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
public class CabecalhoRodape implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_cabecalhoRodape", name = "seq_cabecalhoRodape", allocationSize = 1)
    @GeneratedValue(generator = "seq_cabecalhoRodape", strategy = GenerationType.SEQUENCE)
    private Long id;
    private boolean ativo;
    private int margemTopo;
    private int margemBaixo;
    private int margemEsquerda;
    private int margemDireita;
    private int margemDireitaTexto;
    private int margemEsquerdaTexto;
    @Enumerated(EnumType.STRING)
    private TipoCabecalhoRodape tipo;
    private String texto;
    @OneToOne
    private Anexo imagem;
    @OneToOne
    private UnidadeOrganizacional unidadeOrganizacional;
    private int alturaImagem;
    private boolean imagemEsquerda;
    private boolean possuiImagem;
    private int margemDireitaImagem;
    private int margemEsquerdaImagem;
    private int fonSize;
    private String nome;

    public CabecalhoRodape() {
    }

    public CabecalhoRodape(Long id, boolean ativo, int margemTopo, int margemBaixo, int margemEsquerda, int margemDireita, int margemDireitaTexto, int margemEsquerdaTexto, TipoCabecalhoRodape tipo, String texto, Anexo imagem, UnidadeOrganizacional unidadeOrganizacional, int alturaImagem, boolean imagemEsquerda, boolean possuiImagem, int margemDireitaImagem, int margemEsquerdaImagem, int fonSize, String nome) {
        this.id = id;
        this.ativo = ativo;
        this.margemTopo = margemTopo;
        this.margemBaixo = margemBaixo;
        this.margemEsquerda = margemEsquerda;
        this.margemDireita = margemDireita;
        this.margemDireitaTexto = margemDireitaTexto;
        this.margemEsquerdaTexto = margemEsquerdaTexto;
        this.tipo = tipo;
        this.texto = texto;
        this.imagem = imagem;
        this.unidadeOrganizacional = unidadeOrganizacional;
        this.alturaImagem = alturaImagem;
        this.imagemEsquerda = imagemEsquerda;
        this.possuiImagem = possuiImagem;
        this.margemDireitaImagem = margemDireitaImagem;
        this.margemEsquerdaImagem = margemEsquerdaImagem;
        this.fonSize = fonSize;
        this.nome = nome;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

    public Long getId() {
        return id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMargemTopo() {
        return margemTopo;
    }

    public void setMargemTopo(int margemTopo) {
        this.margemTopo = margemTopo;
    }

    public int getMargemBaixo() {
        return margemBaixo;
    }

    public void setMargemBaixo(int margemBaixo) {
        this.margemBaixo = margemBaixo;
    }

    public int getMargemEsquerda() {
        return margemEsquerda;
    }

    public List<Anexo> getAnexos() {
        List<Anexo> anexos = new ArrayList<>();
        if (Utils.isNotEmpty(imagem)) {
            anexos.add(imagem);
        }
        return anexos;
    }

    public void setMargemEsquerda(int margemEsquerda) {
        this.margemEsquerda = margemEsquerda;
    }

    public int getMargemDireita() {
        return margemDireita;
    }

    public void setMargemDireita(int margemDireita) {
        this.margemDireita = margemDireita;
    }

    public int getMargemDireitaTexto() {
        return margemDireitaTexto;
    }

    public void setMargemDireitaTexto(int margemDireitaTexto) {
        this.margemDireitaTexto = margemDireitaTexto;
    }

    public int getMargemEsquerdaTexto() {
        return margemEsquerdaTexto;
    }

    public void setMargemEsquerdaTexto(int margemEsquerdaTexto) {
        this.margemEsquerdaTexto = margemEsquerdaTexto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Anexo getImagem() {
        return imagem;
    }

    public void setImagem(Anexo imagem) {
        this.imagem = imagem;
    }

    public int getAlturaImagem() {
        return alturaImagem;
    }

    public void setAlturaImagem(int alturaImagem) {
        this.alturaImagem = alturaImagem;
    }

    public boolean isImagemEsquerda() {
        return imagemEsquerda;
    }

    public void setImagemEsquerda(boolean imagemEsquerda) {
        this.imagemEsquerda = imagemEsquerda;
    }

    public boolean isPossuiImagem() {
        return possuiImagem;
    }

    public void setPossuiImagem(boolean possuiImagem) {
        this.possuiImagem = possuiImagem;
    }

    public int getMargemDireitaImagem() {
        return margemDireitaImagem;
    }

    public void setMargemDireitaImagem(int margemDireitaImagem) {
        this.margemDireitaImagem = margemDireitaImagem;
    }

    public int getMargemEsquerdaImagem() {
        return margemEsquerdaImagem;
    }

    public void setMargemEsquerdaImagem(int margemEsquerdaImagem) {
        this.margemEsquerdaImagem = margemEsquerdaImagem;
    }

    public int getFonSize() {
        return fonSize;
    }

    public void setFonSize(int fonSize) {
        this.fonSize = fonSize;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCabecalhoRodape getTipo() {
        return tipo;
    }

    public void setTipo(TipoCabecalhoRodape tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + (this.ativo ? 1 : 0);
        hash = 89 * hash + this.margemTopo;
        hash = 89 * hash + this.margemBaixo;
        hash = 89 * hash + this.margemEsquerda;
        hash = 89 * hash + this.margemDireita;
        hash = 89 * hash + this.margemDireitaTexto;
        hash = 89 * hash + this.margemEsquerdaTexto;
        hash = 89 * hash + Objects.hashCode(this.tipo);
        hash = 89 * hash + Objects.hashCode(this.texto);
        hash = 89 * hash + Objects.hashCode(this.imagem);
        hash = 89 * hash + this.alturaImagem;
        hash = 89 * hash + (this.imagemEsquerda ? 1 : 0);
        hash = 89 * hash + (this.possuiImagem ? 1 : 0);
        hash = 89 * hash + this.margemDireitaImagem;
        hash = 89 * hash + this.margemEsquerdaImagem;
        hash = 89 * hash + this.fonSize;
        hash = 89 * hash + Objects.hashCode(this.nome);
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
        final CabecalhoRodape other = (CabecalhoRodape) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (this.margemTopo != other.margemTopo) {
            return false;
        }
        if (this.margemBaixo != other.margemBaixo) {
            return false;
        }
        if (this.margemEsquerda != other.margemEsquerda) {
            return false;
        }
        if (this.margemDireita != other.margemDireita) {
            return false;
        }
        if (this.margemDireitaTexto != other.margemDireitaTexto) {
            return false;
        }
        if (this.margemEsquerdaTexto != other.margemEsquerdaTexto) {
            return false;
        }
        if (this.alturaImagem != other.alturaImagem) {
            return false;
        }
        if (this.imagemEsquerda != other.imagemEsquerda) {
            return false;
        }
        if (this.possuiImagem != other.possuiImagem) {
            return false;
        }
        if (this.margemDireitaImagem != other.margemDireitaImagem) {
            return false;
        }
        if (this.margemEsquerdaImagem != other.margemEsquerdaImagem) {
            return false;
        }
        if (this.fonSize != other.fonSize) {
            return false;
        }
        if (!Objects.equals(this.texto, other.texto)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        return Objects.equals(this.imagem, other.imagem);
    }

    @Override
    public String toString() {
        return "CabecalhoRodape{" + "id=" + id + ", ativo=" + ativo + ", margemTopo=" + margemTopo + ", margemBaixo=" + margemBaixo + ", margemEsquerda=" + margemEsquerda + ", margemDireita=" + margemDireita + ", margemDireitaTexto=" + margemDireitaTexto + ", margemEsquerdaTexto=" + margemEsquerdaTexto + ", tipo=" + tipo + ", texto=" + texto + ", imagem=" + imagem + ", alturaImagem=" + alturaImagem + ", imagemEsquerda=" + imagemEsquerda + ", possuiImagem=" + possuiImagem + ", margemDireitaImagem=" + margemDireitaImagem + ", margemEsquerdaImagem=" + margemEsquerdaImagem + ", fonSize=" + fonSize + ", nome=" + nome + '}';
    }

}
