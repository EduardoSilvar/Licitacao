/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
import javax.persistence.Transient;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author eduardo
 */
@Entity
public class Anexo implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_anexo", name = "seq_anexo", allocationSize = 1)
    @GeneratedValue(generator = "seq_anexo", strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String nomeExibicao;
    private String url;
    private boolean uploaded;
    @OneToOne
    private UnidadeOrganizacional unidadeOrganizacional;
    @Transient
    private UploadedFile arquivo;

    @Transient
    private Date dataArquivo;

    @Column(name = "ordem_insercao")
    private Integer ordemInsercao;

    @Enumerated(EnumType.STRING)
    private TipoAnexo caminho;

    @Enumerated(EnumType.STRING)
    private TipoAnexo tipoAnexo;

    @Transient
    private String base64;

    @ManyToOne
    private Contrato contrato;

    public Anexo() {
    }

    public Anexo(Long id, String nome, String nomeExibicao, String url, boolean uploaded, UnidadeOrganizacional unidadeOrganizacional, UploadedFile arquivo, Date dataArquivo, Integer ordemInsercao, TipoAnexo caminho, TipoAnexo tipoAnexo, String base64, Contrato contrato) {
        this.id = id;
        this.nome = nome;
        this.nomeExibicao = nomeExibicao;
        this.url = url;
        this.uploaded = uploaded;
        this.unidadeOrganizacional = unidadeOrganizacional;
        this.arquivo = arquivo;
        this.dataArquivo = dataArquivo;
        this.ordemInsercao = ordemInsercao;
        this.caminho = caminho;
        this.tipoAnexo = tipoAnexo;
        this.base64 = base64;
        this.contrato = contrato;
    }

    public TipoAnexo getTipoAnexo() {
        return tipoAnexo;
    }

    public void setTipoAnexo(TipoAnexo tipoAnexo) {
        this.tipoAnexo = tipoAnexo;
    }

   

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
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

    public String getNomeExibicao() {
        return nomeExibicao;
    }

    public void setNomeExibicao(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public Integer getOrdemInsercao() {
        return ordemInsercao;
    }

    public void setOrdemInsercao(Integer ordemInsercao) {
        this.ordemInsercao = ordemInsercao;
    }

    public TipoAnexo getCaminho() {
        return caminho;
    }

    public void setCaminho(TipoAnexo caminho) {
        this.caminho = caminho;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public UploadedFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(UploadedFile arquivo) {
        this.arquivo = arquivo;
    }

    public Date getDataArquivo() {
        return dataArquivo;
    }

    public void setDataArquivo(Date dataArquivo) {
        this.dataArquivo = dataArquivo;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.nome);
        hash = 79 * hash + Objects.hashCode(this.nomeExibicao);
        hash = 79 * hash + Objects.hashCode(this.url);
        hash = 79 * hash + (this.uploaded ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.unidadeOrganizacional);
        hash = 79 * hash + Objects.hashCode(this.arquivo);
        hash = 79 * hash + Objects.hashCode(this.dataArquivo);
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
        final Anexo other = (Anexo) obj;
        if (this.uploaded != other.uploaded) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.nomeExibicao, other.nomeExibicao)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.unidadeOrganizacional, other.unidadeOrganizacional)) {
            return false;
        }
        if (!Objects.equals(this.arquivo, other.arquivo)) {
            return false;
        }
        if (!Objects.equals(this.dataArquivo, other.dataArquivo)) {
            return false;
        }
        return true;
    }

}
