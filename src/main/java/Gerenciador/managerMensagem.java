/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contrato;
import modelo.Usuario;

/**
 *
 * @author eduardo
 */
@ViewScoped
@ManagedBean
public class managerMensagem implements Serializable {

    private List<Contrato> contratosAprovados;
    private List<Contrato> contratosPertodeExpirar;
    private List<Contrato> contratosFinalizados;
    private List<Usuario> pedidoDeRedefinirSenha;
    
    
    

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

    public List<Usuario> getPedidoDeRedefinirSenha() {
        return pedidoDeRedefinirSenha;
    }

    public void setPedidoDeRedefinirSenha(List<Usuario> pedidoDeRedefinirSenha) {
        this.pedidoDeRedefinirSenha = pedidoDeRedefinirSenha;
    }
    
    

}
