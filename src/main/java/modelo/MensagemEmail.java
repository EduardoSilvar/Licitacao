/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import util.DateUtils;

/**
 *
 * @author eduardo
 */
@Entity
public class MensagemEmail implements Serializable{
     @Id
    @SequenceGenerator(sequenceName = "seq_mensagemEmail", name = "seq_mensagemEmail", allocationSize = 1)
    @GeneratedValue(generator = "seq_mensagemEmail", strategy = GenerationType.AUTO)
    private Long id;
    private String mensagem;
    private String assunto;
    private Date data;
    @OneToOne
    private Contrato contrato;
    private String emailDestinatario;
    private Boolean ativo;
    @OneToOne
    private Usuario escritor;
    @OneToOne
    private Contratado destinatario;

    public MensagemEmail() {
        this.ativo = true;
    }

    public MensagemEmail(Long id, String mensagem, String assunto, Date data, Contrato contrato, String emailDestinatario, Boolean ativo, Usuario escritor, Contratado destinatario) {
        this.id = id;
        this.mensagem = mensagem;
        this.assunto = assunto;
        this.data = data;
        this.contrato = contrato;
        this.emailDestinatario = emailDestinatario;
        this.ativo = ativo;
        this.escritor = escritor;
        this.destinatario = destinatario;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

   
    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public Contratado getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Contratado destinatario) {
        this.destinatario = destinatario;
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

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getData() {
        return data;
    }
    public String getDataFormatada(){
    return DateUtils.format(DateUtils.DD_MM_YYYY, getData());
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Usuario getEscritor() {
        return escritor;
    }

    public void setEscritor(Usuario escritor) {
        this.escritor = escritor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.mensagem);
        hash = 11 * hash + Objects.hashCode(this.assunto);
        hash = 11 * hash + Objects.hashCode(this.data);
        hash = 11 * hash + Objects.hashCode(this.emailDestinatario);
        hash = 11 * hash + Objects.hashCode(this.ativo);
        hash = 11 * hash + Objects.hashCode(this.escritor);
        hash = 11 * hash + Objects.hashCode(this.destinatario);
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
        final MensagemEmail other = (MensagemEmail) obj;
        if (!Objects.equals(this.mensagem, other.mensagem)) {
            return false;
        }
        if (!Objects.equals(this.assunto, other.assunto)) {
            return false;
        }
        if (!Objects.equals(this.emailDestinatario, other.emailDestinatario)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.ativo, other.ativo)) {
            return false;
        }
        if (!Objects.equals(this.escritor, other.escritor)) {
            return false;
        }
        return Objects.equals(this.destinatario, other.destinatario);
    }

    @Override
    public String toString() {
        return "MensagemEmail{" + "id=" + id + ", mensagem=" + mensagem + ", assunto=" + assunto + ", data=" + data + ", emailDestinatario=" + emailDestinatario + ", ativo=" + ativo + ", escritor=" + escritor + ", destinatario=" + destinatario + '}';
    }

    
    
    
}
