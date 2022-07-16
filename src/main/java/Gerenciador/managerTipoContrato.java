/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import static Gerenciador.managerLogin.VerificarLogin;
import static Gerenciador.managerLogin.getObjectSession;
import Servico.tipoContratoServico;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import modelo.TipoContrato;
import modelo.Usuario;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerTipoContrato extends managerPrincipal implements Serializable {

    @EJB
    private tipoContratoServico tipoContratoServico;

    private TipoContrato tipoContrato;
    private List<TipoContrato> TipoContratos;
    private Long id;
    private Usuario user;

    @Override
    public void carregar(String param) {
        try {
            user = (Usuario) getObjectSession("usuarioLogado");
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.id = Long.parseLong(param);
        this.tipoContrato = tipoContratoServico.find(Long.parseLong(param));
        this.TipoContratos = new ArrayList<>();
    }

    @Override
    public void instanciar() {
        try {
            user = (Usuario) getObjectSession("usuarioLogado");
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        InstanciarTipoContrato();
        instanciarLista();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarTipoContrato.xhtml";
    }

    @Override
    public String getUrlVisualizar() {
        return "tipoContrato.xhtml?visualizar=" + this.tipoContrato.getId();
    }

    public void salvar() {
        tipoContrato.setUnidadeOrganizacional(user.getUnidadeOrganizacional());
        tipoContratoServico.Save(tipoContrato);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "tipoContrato.xhtml?visualizar=" + this.tipoContrato.getId());
    }

    public void atualizar() {
        tipoContratoServico.Update(this.tipoContrato);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "tipoContrato.xhtml?visualizar=" + this.tipoContrato.getId());
    }

    public List<TipoContrato> findall() {
        return tipoContratoServico.FindAll();
    }

    public void deletar() {
        try {
            TipoContrato novoTipoContrato = tipoContratoServico.find(this.tipoContrato.getId());
            novoTipoContrato.setAtivo(false);
            tipoContratoServico.Update(novoTipoContrato);
            TipoContratos.remove(novoTipoContrato);
           
            if (Utils.isNotEmpty(this.id)) {
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "tipoContrato.xhtml");
            } else {
                 TipoContratos = tipoContratoServico.pesquisar(this.tipoContrato);
                Msg.messagemInfo("Operação realizada com sucesso !");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void pesquisar() {
        TipoContratos = tipoContratoServico.pesquisar(this.tipoContrato);
        if (TipoContratos.size() > 0) {
            Msg.messagemInfo("Pesquisa feita com sucesso !");
        } else {
            Msg.messagemError("Nenhum tipo de contrato foi encontrato !");
        }
    }

    public void instanciarLista() {
        TipoContratos = new ArrayList<>();
    }

    public void InstanciarTipoContrato() {
        this.tipoContrato = new TipoContrato();
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public List<TipoContrato> getTipoContratos() {
        return TipoContratos;
    }

    public void setTipoContratos(List<TipoContrato> TipoContratos) {
        this.TipoContratos = TipoContratos;
    }

}
