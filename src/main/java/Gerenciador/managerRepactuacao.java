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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    private String verificadorRendered;
    private List<Repactuacao> repactuacoes;
    private List<Usuario> fiscais;
    private List<Contrato> contratos;
    private Usuario user;
    private Long id;

    @Override
    public void carregar(String param) {
        this.id = Long.parseLong(param);
        this.repactuacao = repactuacaoServico.find(Long.parseLong(param));
        this.repactuacoes = new ArrayList<>();
        this.fiscais = userServico.FindAll();
        this.contratos = contratoServico.FindAll();
    }

    @Override
    public void instanciar() {
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
                this.repactuacao.getContrato().setValor(this.repactuacao.getValor());
                BigDecimal valorFinal = BigDecimal.ZERO;
                if (this.repactuacao.getValor().compareTo(this.repactuacao.getContrato().getValor()) < 0) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = this.repactuacao.getContrato().getValor().subtract(this.repactuacao.getValor());
                    valorFinal = this.repactuacao.getContrato().getValorRestante().subtract(valorDiferenca);
                } else if (this.repactuacao.getValor().compareTo(this.repactuacao.getContrato().getValor()) > 0) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = this.repactuacao.getValor().subtract(this.repactuacao.getContrato().getValor());
                    valorFinal = this.repactuacao.getContrato().getValorRestante().add(valorDiferenca);
                } else {
                    valorFinal = this.repactuacao.getContrato().getValorRestante();
                }
                this.repactuacao.getContrato().setValorRestante(valorFinal);
                contratoServico.Update(this.repactuacao.getContrato());
                repactuacaoServico.Save(this.repactuacao);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "cadastrarAlteracoes.xhtml?visualizar=" + this.repactuacao.getId() + "&repactuacao=TRUE");
            }
        }
    }
    
    public void atualizar() {
        repactuacaoServico.Update(repactuacao);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "cadastrarAlteracoes.xhtml?visualizar=" + this.repactuacao.getId() + "&repactuacao=TRUE");
    }
    
    public void pesquisar() {
        this.repactuacoes = repactuacaoServico.findPesquisa(this.repactuacao);
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
    
    public String urlVisualizar(long id){
        return "cadastrarAlteracoes.xhtml?visualizar="+id+"&repactuacao=TRUE";
    }
    
    public String urlEditar(long id){
        return "cadastrarAlteracoes.xhtml?editar="+id+"&repactuacao=TRUE";
    }
    
    public boolean verificarRepactuacao(){
        boolean verificarMetodo = false;
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.verificadorRendered = params.get("repactuacao");
        if(isVisualizar() || isEditar()){
            if(this.verificadorRendered != null && !this.verificadorRendered.isEmpty()){
                verificarMetodo = true;
            }
        }
        if(isCadastrar()) {
            verificarMetodo = true;
        } 
        return verificarMetodo;
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

    public String getVerificadorRendered() {
        return verificadorRendered;
    }

    public void setVerificadorRendered(String verificadorRendered) {
        this.verificadorRendered = verificadorRendered;
    }
    
    
}
