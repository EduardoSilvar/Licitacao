/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import static Gerenciador.managerLogin.VerificarLogin;
import static Gerenciador.managerLogin.getObjectSession;
import Servico.AcrescimoServico;
import Servico.ContratoServico;
import Servico.UsuarioServico;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Acrescimo;
import modelo.Contrato;
import modelo.Usuario;
import util.Msg;
import util.Utils;

/**
 *
 * @author dylan
 */
@ManagedBean
@ViewScoped
public class managerAcrescimo extends managerPrincipal implements Serializable{
    @EJB
    private AcrescimoServico acrescimoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;
    
    private Acrescimo acrescimo;
    private List<Acrescimo> acrescimos;
    private List<Usuario> fiscais;
    private List<Contrato> contratos;
    private Usuario user;
    private Long id;

    @Override
    public void carregar(String param) {
        try {
            user = (Usuario) getObjectSession("usuarioLogado");
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.id = Long.parseLong(param);
        this.acrescimo = acrescimoServico.find(Long.parseLong(param));
        this.acrescimos = new ArrayList<>();
        this.fiscais = userServico.FindAll();
        this.contratos = contratoServico.FindAll();
    }

    @Override
    public void instanciar() {
        try {
            user = (Usuario) getObjectSession("usuarioLogado");
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        instanciarSelect();
        instanciarAcrescimo();
        instanciarAcrescimos();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarAcrescimo.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "acrescimo.xhtml?visualizar=" + this.acrescimo.getId();
    }

    private void instanciarAcrescimo() {
        acrescimo = new Acrescimo();
    }

    private void instanciarAcrescimos() {
        this.acrescimos = new ArrayList<>();
    }

    private void instanciarSelect() {
        this.fiscais = userServico.FindAll();
        this.contratos = contratoServico.FindAll();
    }
    
    public void salvar() {
        if (Utils.isNotEmpty(this.acrescimo.getNumeroTermo())) {
            if (acrescimoServico.existNumero(this.acrescimo.getNumeroTermo())) {
                Msg.messagemError("Número de termo aditivo já registrado !");
            } else {
                acrescimoServico.Save(this.acrescimo);
                //Msg.messagemInfoRedirect("Operação realizada com sucesso !", "acrescimo.xhtml?visualizar=" + this.acrescimo.getId());
            }
        }
    }

    public AcrescimoServico getAcrescimoServico() {
        return acrescimoServico;
    }

    public void setAcrescimoServico(AcrescimoServico acrescimoServico) {
        this.acrescimoServico = acrescimoServico;
    }

    public ContratoServico getContratoServico() {
        return contratoServico;
    }

    public void setContratoServico(ContratoServico contratoServico) {
        this.contratoServico = contratoServico;
    }

    public UsuarioServico getUserServico() {
        return userServico;
    }

    public void setUserServico(UsuarioServico userServico) {
        this.userServico = userServico;
    }

    public Acrescimo getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(Acrescimo acrescimo) {
        this.acrescimo = acrescimo;
    }

    public List<Acrescimo> getAcrescimos() {
        return acrescimos;
    }

    public void setAcrescimos(List<Acrescimo> acrescimos) {
        this.acrescimos = acrescimos;
    }

    public List<Usuario> getFiscais() {
        return fiscais;
    }

    public void setFiscais(List<Usuario> fiscais) {
        this.fiscais = fiscais;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
}
