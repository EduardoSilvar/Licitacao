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
    private String verificadorRendered;
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
                Contrato contrato = this.supressao.getContrato();
                contrato.setValor(contrato.getValor().subtract(this.supressao.getValor()));
                contrato.setValorRestante(contrato.getValorRestante().subtract(this.supressao.getValor()));
                contratoServico.Update(contrato);
                supressaoServico.Save(this.supressao);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "cadastrarAlteracoes.xhtml?visualizar=" + this.supressao.getId() + "&supressao=TRUE");
            }
        }
    }
    
    public void atualizar() {
        Contrato contrato = this.supressao.getContrato();
        Supressao supressaoBD = supressaoServico.find(this.supressao.getId());
        Contrato contratoBD = supressaoBD.getContrato();
        BigDecimal valorBD = supressaoBD.getValor();
        BigDecimal valorCampo = this.supressao.getValor();
        BigDecimal valorFinal = BigDecimal.ZERO;
        if (contratoBD.equals(this.supressao.getContrato())) {
            if (valorBD.compareTo(valorCampo) == 1) {
                BigDecimal valorDiferenca = BigDecimal.ZERO;
                valorDiferenca = valorBD.subtract(valorCampo);
                valorFinal = supressaoBD.getContrato().getValorRestante().subtract(valorDiferenca);
            } else if (valorCampo.compareTo(valorBD) == 1) {
                BigDecimal valorDiferenca = BigDecimal.ZERO;
                valorDiferenca = valorCampo.subtract(valorBD);
                valorFinal = supressaoBD.getContrato().getValorRestante().add(valorDiferenca);
            } else {
                valorFinal = supressaoBD.getContrato().getValorRestante();
            }
        } else{
            valorFinal = this.supressao.getContrato().getValorRestante().subtract(this.supressao.getValor());
        }
        contrato.setValor(this.supressao.getContrato().getValor().subtract(this.supressao.getValor()));
        contrato.setValorRestante(valorFinal);
        contratoServico.Update(contrato);
        supressaoServico.Update(this.supressao);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "cadastrarAlteracoes.xhtml?visualizar=" + this.supressao.getId() + "&supressao=TRUE");
    }
    
    public void pesquisar() {
        this.supressoes = supressaoServico.findPesquisa(this.supressao);
    }
    
    public void deletar() {
        try {
            Supressao NovaSupressao = supressaoServico.find(this.supressao.getId());
            NovaSupressao.setAtivo(false);
            supressaoServico.Update(NovaSupressao);
            supressoes.remove(NovaSupressao);
            if (Utils.isNotEmpty(supressao)) {
                this.supressoes = supressaoServico.findPesquisa(this.supressao);
            }
            if (Utils.isNotEmpty(this.id)) {
                Msg.messagemInfoRedirect("operação realizada com sucesso !", "supressao.xhtml");
            } else {
                Msg.messagemInfo("Processo realizado com sucesso !");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }
    
    public String urlVisualizar(long id){
        return "cadastrarAlteracoes.xhtml?visualizar="+id+"&supressao=TRUE";
    }
    
    public String urlEditar(long id){
        return "cadastrarAlteracoes.xhtml?editar="+id+"&supressao=TRUE";
    }
    
    public boolean verificarSupressao(){
        boolean verificarMetodo = false;
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.verificadorRendered = params.get("supressao");
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

    public String getVerificadorRendered() {
        return verificadorRendered;
    }

    public void setVerificadorRendered(String verificadorRendered) {
        this.verificadorRendered = verificadorRendered;
    }

    
    

    
    
}
