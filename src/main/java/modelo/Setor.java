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
public class Setor implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_setor", name = "seq_setor", allocationSize = 1)
    @GeneratedValue(generator = "seq_setor", strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String nomeResponsavel;
    private String cpfResponsavel;
    private String descricao;
    private String telefone;
    private boolean ativo = true;

    public Setor() {
    }

    public Setor(Long id, String nome, String nomeResponsavel, String cpfResponsavel, String descricao, String telefone) {
        this.id = id;
        this.nome = nome;
        this.nomeResponsavel = nomeResponsavel;
        this.cpfResponsavel = cpfResponsavel;
        this.descricao = descricao;
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    public void setCpfResponsavel(String cpfResponsavel) {
        this.cpfResponsavel = cpfResponsavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.nome);
        hash = 23 * hash + Objects.hashCode(this.nomeResponsavel);
        hash = 23 * hash + Objects.hashCode(this.cpfResponsavel);
        hash = 23 * hash + Objects.hashCode(this.descricao);
        hash = 23 * hash + (this.ativo ? 1 : 0);
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
        final Setor other = (Setor) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.nomeResponsavel, other.nomeResponsavel)) {
            return false;
        }
        if (!Objects.equals(this.cpfResponsavel, other.cpfResponsavel)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Setor{" + "id=" + id + ", nome=" + nome + ", nomeResponsavel=" + nomeResponsavel + ", cpfResponsavel=" + cpfResponsavel + ", descricao=" + descricao + ", ativo=" + ativo + '}';
    }

}
