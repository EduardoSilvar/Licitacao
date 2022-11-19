/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.ContratoServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.math.BigDecimal;
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
import util.Msg;
import util.Utils;

/**
 *
 * @author nikolaimikkeldylanmonteirosmith
 */
@ManagedBean
@ViewScoped
public class ManagerRecebimentos implements Serializable{
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;
    
    private List<Contrato> contratos;
    private Usuario userLogado;
    private BigDecimal valorTotal;
    
    @PostConstruct
    public void init() {
        userLogado = userServico.getCurrentUser();
        this.contratos = new ArrayList<>();
        valorTotal = BigDecimal.ZERO;
        carregarContratos();
        somarContratos();
    }
    
    public String dataFormatada(Date data) {
        if (Utils.isNotEmpty(data)) {
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
            String stringDate = DateFor.format(data);
            return stringDate;
        }
        return "";

    }
    
    public void somarContratos(){
        for(Contrato c : contratos){
            valorTotal = valorTotal.add(c.getValor());
        }
    }
    
    public void carregarContratos() {
            this.contratos = contratoServico.buscarContratosPagos(userLogado);
            if (!this.contratos.isEmpty()) {
            } else {
                Msg.messagemError("Nenhum Contrato encontrato !");
            }
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public Usuario getUserLogado() {
        return userLogado;
    }

    public void setUserLogado(Usuario userLogado) {
        this.userLogado = userLogado;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    
}
