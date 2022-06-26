/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.NaturezaEnum;
import static Gerenciador.managerLogin.VerificarLogin;
import Servico.ContratadoServico;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contratado;
import modelo.Contrato_;
import modelo.Endereco;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerContratado extends managerPrincipal implements Serializable {

    @EJB
    private ContratadoServico contratadoServico;

    private Contratado contratado;
    private List<Contratado> contratados;
    private NaturezaEnum natureza;
    private String cpf;
    private String cnpj;

    @Override
    public void carregar(String param) {
         try {
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.contratado = contratadoServico.find(Long.parseLong(param));
        cpf = contratado.getCpf();
        cnpj = contratado.getCnpj();
        this.contratados = new ArrayList<>();
    }

    @Override
    public void instanciar() {
         try {
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if (contratado.getNatureza().equals(natureza.FISICA)) {
            contratado.setCpf(cpf);
            contratado.setCnpj("");
        } else {
            contratado.setCnpj(cnpj);
            contratado.setCpf("");
        }
        if (Utils.isNotEmpty(contratado.getCpf())) {
            if (contratadoServico.valida(contratado.getCpf())) {
                if (contratadoServico.existCpf(contratado.getCpf())) {
                    Msg.messagemError("CPF ja esta sendo usado !");
                } else {
                    contratadoServico.Save(contratado);
                    Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contratado.xhtml?visualizar=" + this.contratado.getId());

                }
            } else {
                Msg.messagemError("CPF invalido !");
            }
        } else {
            contratadoServico.Save(contratado);
            Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contratado.xhtml?visualizar=" + this.contratado.getId());

        }

    }

    public void atualizar() {
        if (contratado.getNatureza().equals(natureza.FISICA)) {
            contratado.setCpf(cpf);
            contratado.setCnpj("");
        } else {
            contratado.setCnpj(cnpj);
            contratado.setCpf("");
        }
        contratadoServico.Update(contratado);

        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contratado.xhtml?visualizar=" + this.contratado.getId());
    }

    public void deletar() {
        try {
            Contratado NovoContratado = contratadoServico.find(this.contratado.getId());
            NovoContratado.setAtivo(false);
            contratadoServico.Update(NovoContratado);
            contratados.remove(NovoContratado);
            if (Utils.isNotEmpty(contratado)) {
                this.contratados = contratadoServico.findPesquisa(this.contratado);
            }
            Msg.messagemInfo("Processo realizado com sucesso !");
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public Contratado buscarPorId(Long id) {
        return contratadoServico.find(id);
    }

    public List<Contratado> getAll() {
        return contratadoServico.FindAll();
    }

    public boolean renderedCpfPEssoa() {
        return contratado.getNatureza().equals(natureza.FISICA);
    }

    public boolean renderedCnpjPEssoa() {
        return contratado.getNatureza().equals(natureza.JURÍDICA);
    }

    public void pesquisar() {
        this.contratados = contratadoServico.findPesquisa(this.contratado);
        if (contratados.size() > 0) {
            Msg.messagemInfo("Pesquisa realizada com sucesso !");
        } else {
            Msg.messagemError("Nenhum contratado encontrado !");
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

    public List<Contratado> getContratados() {
        return contratados;
    }

    public void setContratados(List<Contratado> contratados) {
        this.contratados = contratados;
    }

}
