/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

/**
 *
 * @author eduardo
 */
public abstract class ManagerPrincipal implements Serializable {

    private String visualizar;
    private String editar;

    @PostConstruct
    public void init() {
        carregarParametros();
        if (isVisualizar()) {
            carregar(this.visualizar);
            return;
        }

        if (isEditar()) {
            carregar(this.editar);
            return;
        }
        instanciar();
    }

    public abstract void carregar(String param);

    public abstract void instanciar();

    public String getTitulo() {

        if (isEditar()) {
            return "Editar";
        }

        if (isCadastrar()) {
            return "Cadastrar";
        }

        if (isVisualizar()) {
            return "Visualizar";
        }

        return "";

    }

    public String getIcon() {

        if (isEditar()) {
            return "fa fa-pencil";
        }

        if (isCadastrar()) {
            return "fa fa-plus";
        }

        if (isVisualizar()) {
            return "mdi mdi-eye";
        }

        return "";

    }

    public boolean isRenderizarBotaoAcoes() {
        return isVisualizar() || isEditar();
    }

    public String getUrlAposCancelar() {

        if (isCadastrar()) {
            return "index.xhtml";
        } else if (isVisualizar()) {
            return getUrlPesquisar();
        } else if (isEditar()) {
            return getUrlVisualizar();
        } else {
            return "index.xhtml";
        }

    }

    public abstract String getUrlPesquisar();

    public abstract String getUrlVisualizar();

    private void carregarParametros() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.visualizar = params.get("visualizar");
        this.editar = params.get("editar");
    }

    public boolean isVisualizar() {
        return this.visualizar != null && !this.visualizar.isEmpty();
    }

    public boolean isEditar() {
        return this.editar != null && !this.editar.isEmpty();
    }

    public boolean isCadastrar() {
        return !this.isVisualizar() && !this.isEditar();
    }
}
