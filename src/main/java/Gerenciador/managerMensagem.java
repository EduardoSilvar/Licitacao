/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.ChatServico;
import Servico.MensagemServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Entity;
import modelo.Chat;
import modelo.Mensagem;
import modelo.Usuario;

/**
 *
 * @author eduardo
 */
@ViewScoped
@ManagedBean
public class managerMensagem extends managerPrincipal implements Serializable {

    private Mensagem mensagem;
    @EJB
    private UsuarioServico userServico;
    @EJB
    private MensagemServico mesnagemServico;
    @EJB
    private ChatServico chatServico;
    private Chat chat;

    @Override
    public void carregar(String param) {
        this.chat = chatServico.find(Long.parseLong(param));
        instanciarMensagem();
    }

    public void enviarMensagem() {
         Usuario user = userServico.find(558l);
         this.mensagem.setEscritor(user);
        this.chat.getMensagens().add(mensagem);
        chatServico.Update(this.chat);
        this.mensagem = new Mensagem();
    }

    public String posicaoMensagem(Mensagem msg) {
        Usuario user = userServico.find(1l);
        if (msg.getEscritor().equals(user)) {
            return "margin-left:600px !important";
        } else {
            return "margin-right:100px !important";
        }
    }

    @Override
    public void instanciar() {
        instanciarMensagem();
    }

    public void instanciarMensagem() {
        this.mensagem = new Mensagem();
    }

    public void instanciarChat() {
        this.chat = new Chat();
    }

    @Override
    public String getUrlPesquisar() {
        return "mensagem.xhtml?visualizar=" + this.mensagem.getId();
    }

    @Override
    public String getUrlVisualizar() {
        return "pesquisarMensagem.xhtml";
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    public MensagemServico getMesnagemServico() {
        return mesnagemServico;
    }

    public void setMesnagemServico(MensagemServico mesnagemServico) {
        this.mesnagemServico = mesnagemServico;
    }

    public ChatServico getChatServico() {
        return chatServico;
    }

    public void setChatServico(ChatServico chatServico) {
        this.chatServico = chatServico;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}
