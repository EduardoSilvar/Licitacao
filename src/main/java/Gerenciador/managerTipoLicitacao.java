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
public class managerTipoLicitacao implements Serializable {

    @EJB
    private TipoLicitacaoServico tipoLicitacaoServico;

    private TipoLicitacao tipoLicitacao;
    private List<TipoLicitacao> tiposLicitacoes;

    @PostConstruct
    public void init() {
        instanciarTipoLicitacao();
        instanciarListaTipoLicitacao();
    }

    public void salvar() {
        tipoLicitacaoServico.Save(this.tipoLicitacao);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "pesquisarTipoLicitacao.xhtml");
        instanciarTipoLicitacao();
    }

    public void deletar(Long id) {
        TipoLicitacao novoTipoLicitacao = tipoLicitacaoServico.Find(id);
        novoTipoLicitacao.setAtivo(false);
        tipoLicitacaoServico.Update(novoTipoLicitacao);
    }

    public void pesquisar() {
        this.tiposLicitacoes = tipoLicitacaoServico.pesquisar(tipoLicitacao);

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

}
