/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Servico.TipoLicitacaoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceContext;
import modelo.TipoLicitacao;
import util.Msg;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerTipoLicitacao extends ManagerPrincipal implements Serializable {

    @EJB
    private TipoLicitacaoServico tipoLicitacaoServico;

    private TipoLicitacao tipoLicitacao;
    private List<TipoLicitacao> tiposLicitacoes;

    public void salvar() {
        tipoLicitacaoServico.Save(this.tipoLicitacao);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "pesquisarTipoLicitacao.xhtml");
        instanciarTipoLicitacao();
    }

    public void deletar(Long id) {
        TipoLicitacao novoTipoLicitacao = tipoLicitacaoServico.find(id);
        novoTipoLicitacao.setAtivo(false);
        tipoLicitacaoServico.Update(novoTipoLicitacao);
    }

    public void atualizar() {
        tipoLicitacaoServico.Update(this.tipoLicitacao);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "tipoLicitacao.xhtml?visualizar=" + this.tipoLicitacao.getId());
    }

    public void pesquisar() {
        this.tiposLicitacoes = tipoLicitacaoServico.pesquisar(tipoLicitacao);
        if (this.tiposLicitacoes.size() > 0) {
            Msg.messagemInfo("Pesquisa realizada com suceso !");
        } else {
            Msg.messagemError("Nenhum tipo de licitação encontrada !");
        }

    }

    public void instanciarTipoLicitacao() {
        this.tipoLicitacao = new TipoLicitacao();
    }

    public void instanciarListaTipoLicitacao() {
        this.tiposLicitacoes = tipoLicitacaoServico.FindAll();
    }

    public TipoLicitacao getTipoLicitacao() {
        return tipoLicitacao;
    }

    public void setTipoLicitacao(TipoLicitacao tipoLicitacao) {
        this.tipoLicitacao = tipoLicitacao;
    }

    public List<TipoLicitacao> getTiposLicitacoes() {
        return tiposLicitacoes;
    }

    public void setTiposLicitacoes(List<TipoLicitacao> tiposLicitacoes) {
        this.tiposLicitacoes = tiposLicitacoes;
    }

    @Override
    public void carregar(String param) {
        this.tipoLicitacao = tipoLicitacaoServico.find(Long.parseLong(param));
    }

    @Override
    public void instanciar() {
        instanciarTipoLicitacao();
        instanciarListaTipoLicitacao();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarTipoLicitacao.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "tipoLicitacao.xhtml?visualizar=" + this.tipoLicitacao.getId();
    }

}
