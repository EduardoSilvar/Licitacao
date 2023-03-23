/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Enum.StatusContrato;
import Servico.ContratoServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contrato;
import modelo.ContratoVo;
import modelo.Usuario;
import org.primefaces.PrimeFaces;
import util.Msg;

/**
 *
 * @author eduardo
 */
@ViewScoped
@ManagedBean
public class managerIndex implements Serializable {

    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;

    private Usuario user;
    private List<Contrato> contratosPertodeExpirar;
    private List<Contrato> contratosExpirados;
    private Contrato contrato;

    @PostConstruct
    public void init() {
        this.user = userServico.getCurrentUser();
        this.contratosPertodeExpirar = new ArrayList<>();
        this.contratosExpirados = new ArrayList<>();
        mostrarContratosPorVencer();
    }

    public List<ContratoVo> TiposContrato() {
        this.user = userServico.getCurrentUser();
        return contratoServico.buscarTipoContrato(user);
    }

    public void mostrarContratosPorVencer() {
        this.contratosPertodeExpirar = new ArrayList<>();
        List<Contrato> todosContratos = new ArrayList<>();
        todosContratos = contratoServico.findAllUnidade(user.getUnidadeOrganizacional());
        Calendar cal = GregorianCalendar.getInstance();
        final Date dataEvento = cal.getTime();
        Long agora = dataEvento.getTime() / (3600000 * 24);
//            Long dataContrato = new Date().getTime() / (3600000 * 24);
        for (Contrato c : todosContratos) {
            Long dataContrato = c.getDataFinal().getTime() / (3600000 * 24);
            Long output = (dataContrato - agora);
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
            System.out.println(" abc =" + output);
            if (output <= 0) {
                if (c.getStatus().equals(StatusContrato.CANCELADO) || c.getStatus().equals(StatusContrato.FINALIZADO)) {

                } else {
                    if (this.contratosExpirados.contains(c)) {

                    } else {
                        this.contratosExpirados.add(c);
                    }
                    if (!c.getStatus().equals(StatusContrato.EXPIRADO)) {
                        c.setStatus(StatusContrato.EXPIRADO);
                        contratoServico.Update(c);
                    }
                }

            }

        }
//        if (!this.contratosPertodeExpirar.isEmpty()) {
//            Msg.messagemInfo("Operação realizada com sucesso !");
//        } else {
//            Msg.messagemError("Nenhum Contrato encontrato !");
//        }
        PrimeFaces.current().executeScript("PF('statusContratosDialog').show();");
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public List<Contrato> getContratosPertodeExpirar() {
        return contratosPertodeExpirar;
    }

    public void setContratosPertodeExpirar(List<Contrato> contratosPertodeExpirar) {
        this.contratosPertodeExpirar = contratosPertodeExpirar;
    }

    public List<Contrato> getContratosExpirados() {
        return contratosExpirados;
    }

    public void setContratosExpirados(List<Contrato> contratosExpirados) {
        this.contratosExpirados = contratosExpirados;
    }

}
