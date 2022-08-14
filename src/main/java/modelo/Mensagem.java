/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class Mensagem implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_mensagem", name = "seq_mensagem", allocationSize = 1)
    @GeneratedValue(generator = "seq_mensagem", strategy = GenerationType.AUTO)
    private Long id;
    private String mensagem;
    private Usuario escritor;

    public Mensagem() {
    }

    public Mensagem(Long id, String mensagem, Usuario escritor) {
        this.id = id;
        this.mensagem = mensagem;
        this.escritor = escritor;
    }

    public Usuario getEscritor() {
        return escritor;
    }

    public void setEscritor(Usuario escritor) {
        this.escritor = escritor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.mensagem);
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
        final Mensagem other = (Mensagem) obj;
        if (!Objects.equals(this.mensagem, other.mensagem)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return mensagem;
    }

}
