/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.ChatServico;
import Servico.MensagemServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Chat;
import modelo.Mensagem;
import modelo.Usuario;
import util.Msg;

/**
 *
 * @author eduardo
 */
@ViewScoped
@ManagedBean
public class managerChat extends managerPrincipal implements Serializable {
    
    @EJB
    private ChatServico chatServico;
    @EJB
    private MensagemServico mensagemServico;
    @EJB
    private UsuarioServico userServico;
    
    private Mensagem mesagem;
    private List<Chat> chats;
    private Chat chat;
    private boolean renderedChat = false;
    
    @Override
    public void carregar(String param) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void instanciar() {
        instanciarChat();
        instanciarMensagem();
        instanciarChats();
        mensagens();
    }
    
    @Override
    public String getUrlPesquisar() {
        return "pesquisarChat.xhtml";
    }
    
    @Override
    public String getUrlVisualizar() {
        return "chat.xhtml?visualizar=" + this.chat.getId();
    }
    
    public void salvar() {
        Usuario user = userServico.find(1l);
        this.mesagem.setEscritor(user);
        this.chat.getMensagens().add(mesagem);
        chatServico.Save(this.chat);
        Msg.messagemInfo("Operação realizada com sucesso !");
    }
    
    public void atualizar() {
        chatServico.Update(this.chat);
        Msg.messagemInfo("Operação realizada com sucesso !");
    }
    
    public void deletar() {
        Chat novoChat = chatServico.find(this.chat.getId());
        novoChat.setAtivo(false);
    }
    
    public void instanciarChat() {
        this.chat = new Chat();
        this.chat.setMensagens(new ArrayList<Mensagem>());
    }
    
    public void instanciarChats() {
        Usuario user = userServico.find(1l);
        this.chats = chatServico.chatsAtivos(user);
        System.err.println(this.chats.size());
    }
    
    public void mensagens() {
        if (this.chats.size() > 0) {
            this.renderedChat = true;
        } else {
            this.renderedChat = false;
        }
    }
    
    public void instanciarMensagem() {
        this.mesagem = new Mensagem();
    }
    
    public ChatServico getChatServico() {
        return chatServico;
    }
    
    public void setChatServico(ChatServico chatServico) {
        this.chatServico = chatServico;
    }
    
    public MensagemServico getMensagemServico() {
        return mensagemServico;
    }
    
    public void setMensagemServico(MensagemServico mensagemServico) {
        this.mensagemServico = mensagemServico;
    }
    
    public Mensagem getMesagem() {
        return mesagem;
    }
    
    public void setMesagem(Mensagem mesagem) {
        this.mesagem = mesagem;
    }
    
    public Chat getChat() {
        return chat;
    }
    
    public void setChat(Chat chat) {
        this.chat = chat;
    }
    
    public List<Chat> getChats() {
        return chats;
    }
    
    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
    
    public boolean isRenderedChat() {
        return renderedChat;
    }
    
    public void setRenderedChat(boolean renderedChat) {
        this.renderedChat = renderedChat;
    }
    
}
