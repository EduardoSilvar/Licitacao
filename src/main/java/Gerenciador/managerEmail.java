/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.UsuarioServico;
import java.util.Properties;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import modelo.Usuario;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerEmail {

    @EJB
    UsuarioServico usuarioServico;
    String senha;
    private String email;

    @PostConstruct
    public void iniciar() {

    }

    public void enviarEmail() {
        Usuario usuario = usuarioServico.existEmail(email);
        if (Utils.isNotEmpty(usuario)) {
            if (verificaEmail(usuario.getLogin(), usuario.getEmail())) {
                usuarioServico.recuperarSenha(usuario, senha);
                Msg.messagemInfoRedirect("Novo acesso foi enviado ao email. Por favor verificar também no spam.", "login.xhtml");
            }
        } else {
            Msg.messagemError("Email não cadastrado em nossa base de dados");
        }

    }

    public boolean verificaEmail(String login, String email) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.hostinger.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("notificacao@planejacontratos.com.br", "@Adminplaneja1@");
            }
        });

        session.setDebug(false);
        int valor = 0;
        try {
            for (int i = 0; i <= 3; i++) {
                Random aleatorio = new Random();
                valor = valor + aleatorio.nextInt(100) + 1;
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("notificacao@planejacontratos.com.br"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Planeja Contratos - Nova senha de Acesso");
            senha = "Pl4N3" + valor + "j4Ment0";
            senha = senha.replaceAll("\n", "<br/>");
//            message.setText("A sua nova senha é : \n\n" +senha);
            message.setContent("<html><head></head><body> Login : " + login + "<br/> A sua nova senha é : <b>" + senha + "</b></body></html>", "text/html");
            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
