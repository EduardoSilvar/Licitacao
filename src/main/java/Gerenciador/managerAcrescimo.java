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
public class managerAcrescimo extends managerPrincipal implements Serializable {

    @EJB
    private AcrescimoServico acrescimoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;

    private Acrescimo acrescimo;
    private String verificadorRendered;
    private List<Acrescimo> acrescimos;
    private List<Usuario> fiscais;
    private List<Contrato> contratos;
    private Usuario user;
    private Long id;

    @Override
    public void carregar(String param) {
        this.id = Long.parseLong(param);
        this.acrescimo = acrescimoServico.find(Long.parseLong(param));
        this.acrescimos = new ArrayList<>();
        this.fiscais = userServico.FindAll();
        this.contratos = contratoServico.FindAll();
    }

    @Override
    public void instanciar() {
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
        return "cadastrarAlteracoes.xhtml?visualizar=" + this.acrescimo.getId();
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
                Contrato contrato = this.acrescimo.getContrato();
                contrato.setValor(this.acrescimo.getValor().add(contrato.getValor()));
                contrato.setValorRestante(contrato.getValorRestante().add(this.acrescimo.getValor()));
                contratoServico.Update(contrato);
                acrescimoServico.Save(this.acrescimo);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "cadastrarAlteracoes.xhtml?visualizar=" + this.acrescimo.getId() + "&acrescimo=TRUE");
            }
        }
    }

    public void atualizar() {
        Contrato contrato = this.acrescimo.getContrato();
        Acrescimo acrescimoBD = acrescimoServico.find(this.acrescimo.getId());
        Contrato contratoBD = acrescimoBD.getContrato();
        BigDecimal valorBD = acrescimoBD.getValor();
        BigDecimal valorCampo = this.acrescimo.getValor();
        BigDecimal valorFinal = BigDecimal.ZERO;
        if (contratoBD.equals(this.acrescimo.getContrato())) {
            if (valorBD.compareTo(valorCampo) == 1) {
                BigDecimal valorDiferenca = BigDecimal.ZERO;
                valorDiferenca = valorBD.subtract(valorCampo);
                valorFinal = acrescimoBD.getContrato().getValorRestante().subtract(valorDiferenca);
            } else if (valorCampo.compareTo(valorBD) == 1) {
                BigDecimal valorDiferenca = BigDecimal.ZERO;
                valorDiferenca = valorCampo.subtract(valorBD);
                valorFinal = acrescimoBD.getContrato().getValorRestante().add(valorDiferenca);
            } else {
                valorFinal = acrescimoBD.getContrato().getValorRestante();
            }
        } else{
            valorFinal = this.acrescimo.getContrato().getValorRestante().add(this.acrescimo.getValor());
        }
        contrato.setValor(this.acrescimo.getValor().add(contrato.getValor()));
        contrato.setValorRestante(valorFinal);
        contratoServico.Update(contrato);
        acrescimoServico.Update(this.acrescimo);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "cadastrarAlteracoes.xhtml?visualizar=" + this.acrescimo.getId() + "&acrescimo=TRUE");
    }

    public void pesquisar() {
        this.acrescimos = acrescimoServico.findPesquisa(this.acrescimo);
    }

    public void deletar() {
        try {
            Acrescimo NovoAcrescimo = acrescimoServico.find(this.acrescimo.getId());
            NovoAcrescimo.setAtivo(false);
            acrescimoServico.Update(NovoAcrescimo);
            acrescimos.remove(NovoAcrescimo);
            if (Utils.isNotEmpty(acrescimo)) {
                this.acrescimos = acrescimoServico.findPesquisa(this.acrescimo);
            }
            if (Utils.isNotEmpty(this.id)) {
                Msg.messagemInfoRedirect("operação realizada com sucesso !", "acrescimo.xhtml");
            } else {
                Msg.messagemInfo("Processo realizado com sucesso !");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public String urlVisualizar(long id) {
        return "cadastrarAlteracoes.xhtml?visualizar=" + id + "&acrescimo=TRUE";
    }

    public String urlEditar(long id) {
        return "cadastrarAlteracoes.xhtml?editar=" + id + "&acrescimo=TRUE";
    }

    public boolean verificarAcrescimo() {
        boolean verificarMetodo = false;
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.verificadorRendered = params.get("acrescimo");
        if (isVisualizar() || isEditar()) {
            if (this.verificadorRendered != null && !this.verificadorRendered.isEmpty()) {
                verificarMetodo = true;
            }
        }
        if (isCadastrar()) {
            verificarMetodo = true;
        }
        return verificarMetodo;
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

    public String getVerificadorRendered() {
        return verificadorRendered;
    }

    public void setVerificadorRendered(String verificadorRendered) {
        this.verificadorRendered = verificadorRendered;
    }

}
