/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.MensagemServico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contrato;
import modelo.PedidoRenovacao;
import modelo.Usuario;

/**
 *
 * @author eduardo
 */
@ViewScoped
@ManagedBean
public class managerMensagem implements Serializable {

    @EJB
    private MensagemServico msgServico;

    private List<Contrato> contratosAprovados;
    private List<Contrato> contratosPertodeExpirar;
    private List<Contrato> contratosFinalizados;
    private List<PedidoRenovacao> pedidoDeRedefinirSenha;

    public void aprovados() {
        contratosAprovados = msgServico.aprovado();
    }

    public void proxExpirar() {
        contratosPertodeExpirar = msgServico.expirar();
    }

    public void pedidos() {
        pedidoDeRedefinirSenha = msgServico.pedidoRenovacao();
    }

    public List<Contrato> getContratosAprovados() {
        return contratosAprovados;
    }

    public void setContratosAprovados(List<Contrato> contratosAprovados) {
        this.contratosAprovados = contratosAprovados;
    }

    public List<Contrato> getContratosPertodeExpirar() {
        return contratosPertodeExpirar;
    }

    public void setContratosPertodeExpirar(List<Contrato> contratosPertodeExpirar) {
        this.contratosPertodeExpirar = contratosPertodeExpirar;
    }

    public List<Contrato> getContratosFinalizados() {
        return contratosFinalizados;
    }

    public void setContratosFinalizados(List<Contrato> contratosFinalizados) {
        this.contratosFinalizados = contratosFinalizados;
    }

    public List<PedidoRenovacao> getPedidoDeRedefinirSenha() {
        return pedidoDeRedefinirSenha;
    }

    public void setPedidoDeRedefinirSenha(List<PedidoRenovacao> pedidoDeRedefinirSenha) {
        this.pedidoDeRedefinirSenha = pedidoDeRedefinirSenha;
    }

}
