/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.List;
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
public class Chat implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_chat", name = "seq_chat", allocationSize = 1)
    @GeneratedValue(generator = "seq_chat", strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Mensagem> mensagens;
    @OneToOne
    private Usuario receptor;
    @OneToOne
    private Usuario emissor;
    private String cor;
    private boolean ativo;

    public Chat() {
    }

    public Chat(Long id, List<Mensagem> mensagens, Usuario receptor, Usuario emissor, String cor, boolean ativo) {
        this.id = id;
        this.mensagens = mensagens;
        this.receptor = receptor;
        this.emissor = emissor;
        this.cor = cor;
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }

    public Usuario getEmissor() {
        return emissor;
    }

    public void setEmissor(Usuario emissor) {
        this.emissor = emissor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.receptor);
        hash = 11 * hash + Objects.hashCode(this.emissor);
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
        final Chat other = (Chat) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.receptor, other.receptor)) {
            return false;
        }
        return Objects.equals(this.emissor, other.emissor);
    }

    @Override
    public String toString() {
        return "Chat{" + "id=" + id + ", receptor=" + receptor + ", emissor=" + emissor + '}';
    }

}
