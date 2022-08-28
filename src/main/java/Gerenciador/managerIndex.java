/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.ContratoServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contrato;
import modelo.ContratoVo;
import modelo.Usuario;

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

    private Contrato contrato;

    public List<ContratoVo> TiposContrato() {
        this.user = userServico.getCurrentUser();
        return contratoServico.buscarTipoContrato(user);
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

}
