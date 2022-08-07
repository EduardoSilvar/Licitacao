/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.ContratadoServico;
import Servico.ContratoServico;
import Servico.SetorServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contratado;
import modelo.Contrato;
import modelo.Setor;
import modelo.Usuario;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerAutoComplete implements Serializable {

    @EJB
    UsuarioServico usuarioServico;
    @EJB
    ContratadoServico contratadoServico;
    @EJB
    ContratoServico contratoServico;
    @EJB
    SetorServico setorServico;

    public List<Usuario> autocompletaUsuario(String nome) {
        return usuarioServico.findUsuario(nome);
    }

    public List<Contratado> autocompletaContratado(String nome) {
        return contratadoServico.findContratado(nome);
    }

    public List<Contrato> autocompletaContrato(String nome) {
        return contratoServico.findContrato(nome);
    }

    public List<Setor> autocompleteSetor(String nome) {
        return setorServico.findSetor(nome);
    }
}
