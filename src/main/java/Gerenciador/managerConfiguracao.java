/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.StatusContrato;
import Servico.ConfiguracaoServico;
import Servico.ContratoServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Configuracao;
import modelo.Contrato;
import modelo.Usuario;
import org.primefaces.PrimeFaces;
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

    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;

    private Configuracao configuracao;

    private List<Contrato> contratosPertodeExpirar;
    private Contrato contrato;
    private Usuario fiscal;
    private Usuario userLogado;

    @PostConstruct
    public void init() {
        this.configuracao = new Configuracao();
        userLogado = userServico.getCurrentUser();
        this.contratosPertodeExpirar = new ArrayList<>();
        this.contrato = new Contrato();
        this.fiscal = userServico.getCurrentUser();
    }

    public void mostrarContratosExpirados() {
        this.contratosPertodeExpirar = new ArrayList<>();
        List<Contrato> todosContratos = new ArrayList<>();
        todosContratos = contratoServico.findAllUnidade(userLogado.getUnidadeOrganizacional());
        Calendar cal = GregorianCalendar.getInstance();
        final Date dataEvento = cal.getTime();
        Long agora = dataEvento.getTime() / (3600000 * 24);
//            Long dataContrato = new Date().getTime() / (3600000 * 24);
        System.out.println(agora + " " + todosContratos.size());
        for (Contrato c : todosContratos) {
            Long dataContrato = c.getDataFinal().getTime() / (3600000 * 24);
            Long output = (dataContrato - agora);
            if (output <= 0) {
                if (c.getStatus().equals(StatusContrato.CANCELADO) || c.getStatus().equals(StatusContrato.FINALIZADO)) {

                } else {
                    if (this.contratosPertodeExpirar.contains(c)) {

                    } else {
                        this.contratosPertodeExpirar.add(c);
                    }
                    if (!c.getStatus().equals(StatusContrato.EXPIRADO)) {
                        c.setStatus(StatusContrato.EXPIRADO);
                        contratoServico.Update(c);
                    }
                }

            }

        }
        if (!this.contratosPertodeExpirar.isEmpty()) {
            Msg.messagemInfo("Operação realizada com sucesso !");
        } else {
            Msg.messagemError("Nenhum Contrato encontrato !");
        }
        PrimeFaces.current().executeScript("PF('statusContratosDialog').show();");
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

    public String dataFormatada(Date data) {
        if (Utils.isNotEmpty(data)) {
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
            String stringDate = DateFor.format(data);
            return stringDate;
        }
        return "";

    }

    public void pesquisar() {
        this.contratosPertodeExpirar = new ArrayList<>();
        if(Utils.isEmpty(this.configuracao.getDiasPraExpirar())){
            Msg.messagemError("Preencha o campo para realizar a busca !");
            return;
        }
        if (this.configuracao.getDiasPraExpirar() != null && this.configuracao.getDiasPraExpirar() > 0) {
            List<Contrato> todosContratos = new ArrayList<>();
            todosContratos = contratoServico.findAllUnidade(userLogado.getUnidadeOrganizacional());
            Calendar cal = GregorianCalendar.getInstance();
            final Date dataEvento = cal.getTime();
            Long agora = dataEvento.getTime() / (3600000 * 24);
//            Long dataContrato = new Date().getTime() / (3600000 * 24);
            System.out.println(agora + " " + todosContratos.size());
            for (Contrato c : todosContratos) {
                Long dataContrato = c.getDataFinal().getTime() / (3600000 * 24);
                Long output = (dataContrato - agora);
                System.out.println(this.configuracao.getDiasPraExpirar());
                if (output <= 7 && output > 0) {
                    if (c.getStatus().equals(StatusContrato.CANCELADO) || c.getStatus().equals(StatusContrato.FINALIZADO)) {

                    } else {

                        if (this.contratosPertodeExpirar.contains(c)) {

                        } else {
                            this.contratosPertodeExpirar.add(c);
                        }
                        if (!c.getStatus().equals(StatusContrato.PROXIMO_EXPIRAR)) {
                            c.setStatus(StatusContrato.PROXIMO_EXPIRAR);
                            contratoServico.Update(c);
                        }
                    }
                }

            }
            if (!this.contratosPertodeExpirar.isEmpty()) {
                Msg.messagemInfo("Operação realizada com sucesso !");
            } else {
                Msg.messagemError("Nenhum Contrato encontrato !");
            }
        }
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public List<Contrato> getContratosPertodeExpirar() {
        return contratosPertodeExpirar;
    }

    public void setContratosPertodeExpirar(List<Contrato> contratosPertodeExpirar) {
        this.contratosPertodeExpirar = contratosPertodeExpirar;
    }

}
