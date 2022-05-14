/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.NaturezaEnum;
import Servico.ContratadoServico;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contratado;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerContratado implements Serializable {

    @EJB
    private ContratadoServico contratadoServico;

    private Contratado contratado;
    private NaturezaEnum natureza;

    @PostConstruct
    public void init() {
        instanciarContratado();
    }

    public void instanciarContratado() {
        contratado = new Contratado();
        contratado.setNatureza(NaturezaEnum.JURÍDICA);
    }

    public void salvar() {
        contratadoServico.Save(contratado);
    }

    public void atualizar(Contratado contratado) {
        contratadoServico.Update(contratado);
    }

    public void deletar(Long id) {
        Contratado NovoContratado = contratadoServico.Find(id);
        NovoContratado.setAtivo(false);
        contratadoServico.Update(NovoContratado);
    }

    public Contratado buscarPorId(Long id) {
        return contratadoServico.Find(id);
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

}
