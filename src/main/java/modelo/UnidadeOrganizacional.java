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
import javax.persistence.OneToMany;
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
    private String whatsapp;
    private boolean ativo = true;
    private String cnpj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.nome);
        hash = 19 * hash + Objects.hashCode(this.NomeRepresentante);
        hash = 19 * hash + Objects.hashCode(this.telefone);
        hash = 19 * hash + Objects.hashCode(this.email);
        hash = 19 * hash + Objects.hashCode(this.endereco);
        hash = 19 * hash + Objects.hashCode(this.whatsapp);
        hash = 19 * hash + (this.ativo ? 1 : 0);
        hash = 19 * hash + Objects.hashCode(this.cnpj);
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
        return Objects.equals(this.endereco, other.endereco);
    }

    

    @Override
    public String toString() {
        return "UnidadeOrganizacional{" + "nome=" + nome + ", NomeRepresentante=" + NomeRepresentante + ", telefone=" + telefone + ", email=" + email + ", endereco=" + endereco + ", ativo=" + ativo + '}';
    }

}
