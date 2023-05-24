/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.EnderecoServico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Endereco;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerEndereco implements Serializable {

    @EJB
    private EnderecoServico enderecoServico;
    private Endereco endereco;
    private List<Endereco> enderecos;

    public void Salvar() {
        enderecoServico.Save(endereco);
    }

    public Endereco Pegar(Long id) {
        return enderecoServico.find(id);
    }

    public void Atualizar(Endereco endereco) {
        enderecoServico.Update(endereco);
    }

    public void PegarTodos() {
        enderecos = enderecoServico.FindAll();
    }

    private void Deletar(Long id) {
        Endereco novoEndereco = enderecoServico.find(id);
        novoEndereco.setAtivo(false);
    }

}
