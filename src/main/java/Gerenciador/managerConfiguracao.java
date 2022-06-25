/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.ConfiguracaoServico;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Configuracao;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ViewScoped
@ManagedBean
public class managerConfiguracao implements Serializable {

    @EJB
    private ConfiguracaoServico configuracaoServico;

    private Configuracao configuracao;

    @PostConstruct
    public void init() {
        if (configuracaoServico.FindAll().size() > 0) {
            System.err.println("sla");
        } else {
            this.configuracao = new Configuracao();
        }
    }

    public void salvar() {
        if (Utils.isEmpty(this.configuracao.getId())) {
            configuracaoServico.Save(this.configuracao);
            Msg.messagemInfo("Operação realizada com sucesso !");
        } else {
            configuracaoServico.Update(this.configuracao);
            Msg.messagemInfo("Operação realizada com sucesso !");
        }

    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

}
