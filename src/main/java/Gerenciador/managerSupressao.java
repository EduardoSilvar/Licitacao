/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import static Gerenciador.managerLogin.VerificarLogin;
import static Gerenciador.managerLogin.getObjectSession;
import Servico.ContratoServico;
import Servico.SupressaoServico;
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
import modelo.Supressao;
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
public class managerSupressao extends managerPrincipal implements Serializable{
    @EJB
    private SupressaoServico supressaoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;
    
    private Supressao supressao;
    private List<Supressao> supressoes;
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
        this.supressao = supressaoServico.find(Long.parseLong(param));
        this.supressoes = new ArrayList<>();
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
        return "pesquisarSupressao.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "supressao.xhtml?visualizar=" + this.supressao.getId();
    }

    private void instanciarAcrescimo() {
        supressao = new Supressao();
    }

    private void instanciarAcrescimos() {
        this.supressoes = new ArrayList<>();
    }

    private void instanciarSelect() {
        this.fiscais = userServico.FindAll();
        this.contratos = contratoServico.FindAll();
    }
    
    public void salvar() {
        if (Utils.isNotEmpty(this.supressao.getNumeroTermo())) {
            if (supressaoServico.existNumero(this.supressao.getNumeroTermo())) {
                Msg.messagemError("Número de termo aditivo já registrado !");
            } else {
                supressaoServico.Save(this.supressao);
                //Msg.messagemInfoRedirect("Operação realizada com sucesso !", "supressao.xhtml?visualizar=" + this.supressao.getId());
            }
        }
    }

    public SupressaoServico getSupressaoServico() {
        return supressaoServico;
    }

    public void setSupressaoServico(SupressaoServico supressaoServico) {
        this.supressaoServico = supressaoServico;
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

    public Supressao getSupressao() {
        return supressao;
    }

    public void setSupressao(Supressao supressao) {
        this.supressao = supressao;
    }

    public List<Supressao> getSupressoes() {
        return supressoes;
    }

    public void setSupressoes(List<Supressao> supressoes) {
        this.supressoes = supressoes;
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
