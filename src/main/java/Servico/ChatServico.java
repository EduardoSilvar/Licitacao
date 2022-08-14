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

    public List<Chat> chatsAtivos(Usuario user) {
        String sql = "select c from Chat c where c.ativo = true ";
        if (Utils.isNotEmpty(user)) {
            sql += "and c.receptor = :usuario ";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(user)) {
            query.setParameter("usuario", user);
        }
        return query.getResultList();
    }

}
