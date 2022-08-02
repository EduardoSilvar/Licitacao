/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import static Gerenciador.managerLogin.VerificarLogin;
import static Gerenciador.managerLogin.getObjectSession;
import Servico.ContratoServico;
import Servico.RepactuacaoServico;
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
import modelo.Repactuacao;
import modelo.Usuario;
import util.Msg;
import util.Utils;

/**
 *
 * @author dylan
 */
@ManagedBean
@ViewScoped
public class managerRepactuacao extends managerPrincipal implements Serializable{
    @EJB
    private RepactuacaoServico repactuacaoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;
    
    private Repactuacao repactuacao;
    private List<Repactuacao> repactuacoes;
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
        this.repactuacao = repactuacaoServico.find(Long.parseLong(param));
        this.repactuacoes = new ArrayList<>();
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
        instanciarRepactuacao();
        intanciarRepactuacoes();
    }

    @Override
    public String getUrlPesquisar() {
        return "contratoForm.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "repactuacao.xhtml?visualizar=" + this.repactuacao.getId();
    }
    
    private void instanciarSelect() {
        this.fiscais = userServico.FindAll();
        this.contratos = contratoServico.FindAll();
    }

    private void instanciarRepactuacao() {
        this.repactuacao = new Repactuacao();
    }

    private void intanciarRepactuacoes() {
        this.repactuacoes = new ArrayList<>();
    }
    
    public boolean renderedValorMudou(){
        return repactuacao.getValorMudou();
    }
    
    public void salvar() {
        if (Utils.isNotEmpty(this.repactuacao.getNumeroTermo())) {
            if (repactuacaoServico.existNumero(this.repactuacao.getNumeroTermo())) {
                Msg.messagemError("Número de termo aditivo já registrado !");
            } else {
                repactuacaoServico.Save(this.repactuacao);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "repactuacao.xhtml?visualizar=" + this.repactuacao.getId());
            }
        }
    }
    
    public void atualizar() {
        repactuacaoServico.Update(repactuacao);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "repactuacao.xhtml?visualizar=" + this.repactuacao.getId());
    }
    
    public void deletar() {
        try {
            Repactuacao NovaRepactuacao = repactuacaoServico.find(this.repactuacao.getId());
            NovaRepactuacao.setAtivo(false);
            repactuacaoServico.Update(NovaRepactuacao);
            repactuacoes.remove(NovaRepactuacao);
            if (Utils.isNotEmpty(repactuacao)) {
                this.repactuacoes = repactuacaoServico.findPesquisa(this.repactuacao);
            }
            if (Utils.isNotEmpty(this.id)) {
                Msg.messagemInfoRedirect("operação realizada com sucesso !", "repactuacao.xhtml");
            } else {
                Msg.messagemInfo("Processo realizado com sucesso !");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public RepactuacaoServico getRepactuacaoServico() {
        return repactuacaoServico;
    }

    public void setRepactuacaoServico(RepactuacaoServico repactuacaoServico) {
        this.repactuacaoServico = repactuacaoServico;
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

    public Repactuacao getRepactuacao() {
        return repactuacao;
    }

    public void setRepactuacao(Repactuacao repactuacao) {
        this.repactuacao = repactuacao;
    }

    public List<Repactuacao> getRepactuacoes() {
        return repactuacoes;
    }

    public void setRepactuacoes(List<Repactuacao> repactuacoes) {
        this.repactuacoes = repactuacoes;
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
