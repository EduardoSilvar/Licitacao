/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import static Gerenciador.managerLogin.VerificarLogin;
import static Gerenciador.managerLogin.getObjectSession;
import Servico.ContratoServico;
import Servico.RenovacaoServico;
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
import modelo.Contrato;
import modelo.Renovacao;
import modelo.Usuario;
import util.Msg;
import util.Utils;

/**
 *
 * @author dylan
 */
@ManagedBean
@ViewScoped
public class managerRenovacao extends managerPrincipal implements Serializable{
    
    @EJB
    private RenovacaoServico renovacaoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;
    
    private Renovacao renovacao;
    private List<Renovacao> renovacoes;
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
        this.renovacao = renovacaoServico.find(Long.parseLong(param));
        this.renovacoes = new ArrayList<>();
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
        instanciarRenovacao();
        intanciarRenovacoes();
    }

    @Override
    public String getUrlPesquisar() {
        return "contratoForm.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "renovacao.xhtml?visualizar=" + this.renovacao.getId();
    }

    private void instanciarRenovacao() {
        this.renovacao = new Renovacao();
    }

    private void intanciarRenovacoes() {
        this.renovacoes = new ArrayList<>();
    }
    
    public boolean renderedValorMudou(){
        return renovacao.getValorMudou();
    }

    private void instanciarSelect() {
        this.fiscais = userServico.FindAll();
        this.contratos = contratoServico.FindAll();
    }
    
    public void salvar() {
        if (Utils.isNotEmpty(this.renovacao.getNumeroTermo())) {
            if (renovacaoServico.existNumero(this.renovacao.getNumeroTermo())) {
                Msg.messagemError("Número de termo aditivo já registrado !");
            } else {
                renovacaoServico.Save(this.renovacao);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "renovacao.xhtml?visualizar=" + this.renovacao.getId());
            }
        }
    }

    
    public void atualizar() {
        renovacaoServico.Update(renovacao);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "renovacao.xhtml?visualizar=" + this.renovacao.getId());
    }
    
    public void deletar() {
        try {
            Renovacao NovaRenovacao = renovacaoServico.find(this.renovacao.getId());
            NovaRenovacao.setAtivo(false);
            renovacaoServico.Update(NovaRenovacao);
            renovacoes.remove(NovaRenovacao);
            if (Utils.isNotEmpty(renovacao)) {
                this.renovacoes = renovacaoServico.findPesquisa(this.renovacao);
            }
            if (Utils.isNotEmpty(this.id)) {
                Msg.messagemInfoRedirect("operação realizada com sucesso !", "renovacao.xhtml");
            } else {
                Msg.messagemInfo("Processo realizado com sucesso !");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }
    public RenovacaoServico getRenovacaoServico() {
        return renovacaoServico;
    }

    public void setRenovacaoServico(RenovacaoServico renovacaoServico) {
        this.renovacaoServico = renovacaoServico;
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

    public Renovacao getRenovacao() {
        return renovacao;
    }

    public void setRenovacao(Renovacao renovacao) {
        this.renovacao = renovacao;
    }

    public List<Renovacao> getRenovacoes() {
        return renovacoes;
    }

    public void setRenovacoes(List<Renovacao> renovacoes) {
        this.renovacoes = renovacoes;
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
