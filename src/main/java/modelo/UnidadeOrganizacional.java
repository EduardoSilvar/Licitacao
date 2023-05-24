/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
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
public class UnidadeOrganizacional implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_unidade", name = "seq_unidade", allocationSize = 1)
    @GeneratedValue(generator = "seq_unidade", strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String NomeRepresentante;
    private String telefone;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    @OneToOne(cascade = CascadeType.ALL)
    private Configuracao configuracao;
    private String whatsapp;
    private boolean ativo = true;
    private String cnpj;

    public UnidadeOrganizacional(Long id, String nome, String NomeRepresentante, String telefone, String email, Endereco endereco, Configuracao configuracao, String whatsapp, String cnpj) {
        this.id = id;
        this.nome = nome;
        this.NomeRepresentante = NomeRepresentante;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.configuracao = configuracao;
        this.whatsapp = whatsapp;
        this.cnpj = cnpj;
    }

    public UnidadeOrganizacional() {
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

    public String getNomeRepresentante() {
        return NomeRepresentante;
    }

    public void setNomeRepresentante(String NomeRepresentante) {
        this.NomeRepresentante = NomeRepresentante;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.nome);
        hash = 13 * hash + Objects.hashCode(this.NomeRepresentante);
        hash = 13 * hash + Objects.hashCode(this.telefone);
        hash = 13 * hash + Objects.hashCode(this.email);
        hash = 13 * hash + Objects.hashCode(this.endereco);
        hash = 13 * hash + Objects.hashCode(this.configuracao);
        hash = 13 * hash + Objects.hashCode(this.whatsapp);
        hash = 13 * hash + (this.ativo ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this.cnpj);
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
        final UnidadeOrganizacional other = (UnidadeOrganizacional) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.NomeRepresentante, other.NomeRepresentante)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.whatsapp, other.whatsapp)) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        return Objects.equals(this.configuracao, other.configuracao);
    }

    @Override
    public String toString() {
        return "UnidadeOrganizacional{" + "id=" + id + ", nome=" + nome + ", NomeRepresentante=" + NomeRepresentante + ", telefone=" + telefone + ", email=" + email + ", endereco=" + endereco + ", configuracao=" + configuracao + ", whatsapp=" + whatsapp + ", ativo=" + ativo + ", cnpj=" + cnpj + '}';
    }

}
