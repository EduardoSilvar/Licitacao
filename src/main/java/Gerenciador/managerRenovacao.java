/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.ContratoServico;
import Servico.RenovacaoServico;
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
public class managerRenovacao extends managerPrincipal implements Serializable {

    @EJB
    private RenovacaoServico renovacaoServico;
    @EJB
    private ContratoServico contratoServico;
    @EJB
    private UsuarioServico userServico;

    private Renovacao renovacao;
    private String verificadorRendered;
    private List<Renovacao> renovacoes;
    private List<Usuario> fiscais;
    private List<Contrato> contratos;
    private Usuario user;
    private Long id;

    @Override
    public void carregar(String param) {
        this.id = Long.parseLong(param);
        this.renovacao = renovacaoServico.find(Long.parseLong(param));
        this.renovacoes = new ArrayList<>();
        this.fiscais = userServico.FindAll();
        this.contratos = contratoServico.FindAll();
    }

    @Override
    public void instanciar() {
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

    public boolean renderedValorMudou() {
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
                Contrato contrato = this.renovacao.getContrato();
                BigDecimal valorFinal = BigDecimal.ZERO;
                if (this.renovacao.getValorMudou()) {
                    contrato.setValor(this.renovacao.getValor());
                    if (this.renovacao.getContrato().getValor().compareTo(this.renovacao.getValor()) == 1) {
                        BigDecimal valorDiferenca = BigDecimal.ZERO;
                        valorDiferenca = this.renovacao.getContrato().getValor().subtract(this.renovacao.getValor());
                        valorFinal = contrato.getValorRestante().subtract(valorDiferenca);
                    } else if (this.renovacao.getValor().compareTo(this.renovacao.getContrato().getValor()) == 1) {
                        BigDecimal valorDiferenca = BigDecimal.ZERO;
                        valorDiferenca = this.renovacao.getValor().subtract(this.renovacao.getContrato().getValor());
                        valorFinal = contrato.getValorRestante().add(valorDiferenca);
                    } else {
                        valorFinal = contrato.getValorRestante();
                    }
                } else {
                    valorFinal = contrato.getValorRestante();
                }
                contrato.setValorRestante(valorFinal);
                contratoServico.Update(this.renovacao.getContrato());
                renovacaoServico.Save(this.renovacao);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "renovacao.xhtml?visualizar=" + this.renovacao.getId() + "&renovacao=TRUE");
            }
        }
    }

    public void atualizar() {
        Contrato contrato = this.renovacao.getContrato();
        Renovacao renovacaoBD = renovacaoServico.find(this.renovacao.getId());
        Contrato contratoBD = renovacaoBD.getContrato();
        BigDecimal valorBD = renovacaoBD.getValor();
        BigDecimal valorCampo = this.renovacao.getValor();
        BigDecimal valorFinal = BigDecimal.ZERO;
        if (contratoBD.equals(this.renovacao.getContrato())) {
            if (this.renovacao.getValorMudou()) {
                contrato.setValor(this.renovacao.getValor());
                if (valorBD.compareTo(valorCampo) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = valorBD.subtract(valorCampo);
                    valorFinal = renovacaoBD.getContrato().getValorRestante().subtract(valorDiferenca);
                } else if (valorCampo.compareTo(valorBD) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = valorCampo.subtract(valorBD);
                    valorFinal = renovacaoBD.getContrato().getValorRestante().add(valorDiferenca);
                } else {
                    valorFinal = renovacaoBD.getContrato().getValorRestante();
                }
            } else {
                valorFinal = renovacaoBD.getContrato().getValorRestante();
            }

        } else {
            if (this.renovacao.getValorMudou()) {
                contrato.setValor(this.renovacao.getValor());
                if (this.renovacao.getContrato().getValor().compareTo(this.renovacao.getValor()) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = this.renovacao.getContrato().getValor().subtract(this.renovacao.getValor());
                    valorFinal = contrato.getValorRestante().subtract(valorDiferenca);
                } else if (this.renovacao.getValor().compareTo(this.renovacao.getContrato().getValor()) == 1) {
                    BigDecimal valorDiferenca = BigDecimal.ZERO;
                    valorDiferenca = this.renovacao.getValor().subtract(this.renovacao.getContrato().getValor());
                    valorFinal = contrato.getValorRestante().add(valorDiferenca);
                } else {
                    valorFinal = contrato.getValorRestante();
                }
            } else {
                valorFinal = contrato.getValorRestante();
            }
        }
        contrato.setValorRestante(valorFinal);
        contratoServico.Update(contrato);
        renovacaoServico.Update(this.renovacao);
        System.out.println("qualquer coisa");
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "renovacao.xhtml?visualizar=" + this.renovacao.getId() + "&renovacao=TRUE");
    }

    public void pesquisar(Contrato contrato) {
        this.renovacoes = renovacaoServico.pesquisarRenovacaoPorContrato(contrato, this.renovacao);
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

//    public String urlVisualizar(long id) {
//        return "cadastrarAlteracoes.xhtml?visualizar=" + id + "&renovacao=TRUE";
//    }
//
//    public String urlEditar(long id) {
//        return "cadastrarAlteracoes.xhtml?editar=" + id + "&renovacao=TRUE";
//    }
//
//    public boolean verificarRenovacao() {
//        boolean verificarMetodo = false;
//        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        this.verificadorRendered = params.get("renovacao");
//        if (isVisualizar() || isEditar()) {
//            if (this.verificadorRendered != null && !this.verificadorRendered.isEmpty()) {
//                verificarMetodo = true;
//            }
//        }
//        if (isCadastrar()) {
//            verificarMetodo = true;
//        }
//        return verificarMetodo;
//    }
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

    public String getVerificadorRendered() {
        return verificadorRendered;
    }

    public void setVerificadorRendered(String verificadorRendered) {
        this.verificadorRendered = verificadorRendered;
    }

}
