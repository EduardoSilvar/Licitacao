/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Chat;
import modelo.Usuario;
import util.Utils;

/**
 *
 * @author eduardo
 */
@Stateless
public class ChatServico extends ServicoGenerico<Chat> {

    public ChatServico() {
        super(Chat.class);
    }

    public List<Chat> chatsRecebido(Usuario user) {
        String sql = "select c from Chat c where c.ativo = true ";
        if (Utils.isNotEmpty(user)) {
            sql += "and c.receptor = :usuario and c.lidoReceptor = false ";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(user)) {
            query.setParameter("usuario", user);
        }
        return query.getResultList();
    }

    public List<Chat> chatsEnviado(Usuario user) {
        String sql = "select c from Chat c where c.ativo = true ";
        if (Utils.isNotEmpty(user)) {
            sql += "and c.emissor = :usuario and c.lidoEmissor = false";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(user)) {
            query.setParameter("usuario", user);
        }
        return query.getResultList();
    }

    public List<Chat> todosChats(Usuario user) {
        List<Chat> todosChats = chatsRecebido(user);
        for (Chat c : chatsEnviado(user)) {
            todosChats.add(c);
        }
        return todosChats;
    }

    public int totalChatNaoLido(Usuario user) {
        int naoLido = 0;
        for (Chat c : todosChats(user)) {
            if (c.isLidoEmissor() != true || c.isLidoReceptor() != true) {
                naoLido = naoLido + 1;
            }
        }
        return naoLido;
    }

}
