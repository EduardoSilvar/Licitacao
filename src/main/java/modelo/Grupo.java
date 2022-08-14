/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author eduardo
 */
@Entity
public class Grupo implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_groups", sequenceName = "seq_groups", allocationSize = 1)
    @GeneratedValue(generator = "seq_groups", strategy = GenerationType.SEQUENCE)
    private Long id;
    /**
     * Nome do grupo contendo epenas letras(a-z) e underlines(_), separando as
     * palavras
     */
    private String nome;
    /**
     * Descrição o grupo contendo letras (A-Z, a-z), espaços e acentos.
     * <br/>Este campo deve ser usado para exibição do grupo
     */
    private String descricao;
    @ManyToMany(mappedBy = "grupos")
    private List<Usuario> usuarios;

    public Grupo(Long id, String nome, String descricao, List<Usuario> usuarios) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuarios = usuarios;
    }

    public Grupo() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
