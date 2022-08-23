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
import util.Utils;

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
    private Usuario userLogado;
    private Chat chat;

    @Override
    public void carregar(String param) {
        userLogado = userServico.getCurrentUser();
        this.chat = chatServico.find(Long.parseLong(param));
        if (this.chat.getEmissor().equals(userLogado)) {
            this.chat.setLidoEmissor(true);
        } else {
            this.chat.setLidoReceptor(true);
        }
        chatServico.Update(this.chat);
        instanciarMensagem();
    }

    public void enviarMensagem() {
        userLogado = userServico.getCurrentUser();
        this.mensagem.setEscritor(userLogado);
        this.chat.getMensagens().add(mensagem);
        this.mensagem = new Mensagem();
        if (this.chat.getEmissor().equals(userLogado)) {
            this.chat.setLidoEmissor(true);
            this.chat.setLidoReceptor(false);
        } else {
            this.chat.setLidoReceptor(true);
            this.chat.setLidoEmissor(false);
        }
        chatServico.Update(this.chat);

    }

    public String posicaoMensagem(Mensagem msg) {
        if (Utils.isNotEmpty(msg.getEscritor())) {
            if (msg.getEscritor().equals(userLogado)) {
                return "margin-left:800px !important";
            } else {
                return "margin-right:100px !important";
            }
        } else {
            return "";
        }
    }

    @Override
    public void instanciar() {
        userLogado = userServico.getCurrentUser();
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
