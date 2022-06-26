/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public Anexo() {
    }

    public Anexo(Long id, String nome, String nomeExibicao, String url, boolean uploaded, UploadedFile arquivo, Date dataArquivo) {
        this.id = id;
        this.nome = nome;
        this.nomeExibicao = nomeExibicao;
        this.url = url;
        this.uploaded = uploaded;
        this.arquivo = arquivo;
        this.dataArquivo = dataArquivo;
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
