/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.NaturezaEnum;
import Servico.ContratadoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contratado;
import modelo.Endereco;
import util.Msg;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerContratado extends ManagerPrincipal implements Serializable {

    @EJB
    private ContratadoServico contratadoServico;

    private Contratado contratado;
    private List<Contratado> contratados;
    private NaturezaEnum natureza;

    @Override
    public void carregar(String param) {
        this.contratado = contratadoServico.find(Long.parseLong(param));
    }

    @Override
    public void instanciar() {
        instanciarContratado();
        intanciarContratados();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarContratado.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "contrartado.xhtml?visualizar=" + this.contratado.getId();
    }

    public void instanciarContratado() {
        contratado = new Contratado();
        contratado.setEndereco(new Endereco());
        contratado.setNatureza(NaturezaEnum.JURÍDICA);
    }

    public void intanciarContratados() {
        this.contratados = new ArrayList<>();
    }

    public void salvar() {
        contratadoServico.Save(contratado);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contratado.xhtml?visualizar=" + this.contratado.getId());
    }

    public void atualizar() {
        contratadoServico.Update(contratado);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contratado.xhtml?visualizar=" + this.contratado.getId());
    }

    public void deletar(Long id) {
        Contratado NovoContratado = contratadoServico.find(id);
        NovoContratado.setAtivo(false);
        contratadoServico.Update(NovoContratado);
    }

    public Contratado buscarPorId(Long id) {
        return contratadoServico.find(id);
    }

    public List<Contratado> buscarTodos() {
        return contratadoServico.FindAll();
    }

    public boolean renderedCpfPEssoa() {
        if (contratado.getNatureza().equals(natureza.FISICA)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean renderedCnpjPEssoa() {
        if (contratado.getNatureza().equals(natureza.JURÍDICA)) {
            return true;
        } else {
            return false;
        }
    }

    public void pesquisar() {
        this.contratados = contratadoServico.findPesquisa(this.contratado);
    }

    public Contratado getContratado() {
        return contratado;
    }

    public void setContratado(Contratado contratado) {
        this.contratado = contratado;
    }

    public void setNatureza(NaturezaEnum natureza) {
        this.natureza = natureza;
    }

    public NaturezaEnum getJuridica() {
        return NaturezaEnum.JURÍDICA;
    }

    public NaturezaEnum getFisica() {
        return NaturezaEnum.FISICA;
    }

    public List<Contratado> getContratados() {
        return contratados;
    }

    public void setContratados(List<Contratado> contratados) {
        this.contratados = contratados;
    }

}
