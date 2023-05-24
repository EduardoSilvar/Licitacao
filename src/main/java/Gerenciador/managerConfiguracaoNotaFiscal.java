/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gerenciador;

import Servico.CabecalhoRodapeServico;
import Servico.ConfiguracaoServico;
import Servico.UnidadeOrganizacionalServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.CabecalhoRodape;
import modelo.Configuracao;
import modelo.UnidadeOrganizacional;
import modelo.Usuario;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerConfiguracaoNotaFiscal implements Serializable {

    @EJB
    private CabecalhoRodapeServico cabecalhoRodapeServico;
    @EJB
    private ConfiguracaoServico configuracaoServico;
    @EJB
    private UsuarioServico userServico;
    @EJB
    private UnidadeOrganizacionalServico unidadeOrganizacionalServico;

    private Usuario userLogado;
    private CabecalhoRodape cabecalho;
    private Configuracao configuracao;
    private CabecalhoRodape rodape;
    private List<CabecalhoRodape> listcabecalhoRodape;
    private UnidadeOrganizacional unidadeOrganizacional;

    @PostConstruct
    public void init() {
        userLogado = userServico.getCurrentUser();
        if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
            unidadeOrganizacional = unidadeOrganizacionalServico.find(userLogado.getUnidadeOrganizacional().getId());
            if (Utils.isNotEmpty(unidadeOrganizacional.getConfiguracao())) {
                if (Utils.isNotEmpty(unidadeOrganizacional.getConfiguracao().getRodapeNotaFiscal())) {
                    rodape = unidadeOrganizacional.getConfiguracao().getRodapeNotaFiscal();
                }
                 if (Utils.isNotEmpty(unidadeOrganizacional.getConfiguracao().getCabecalhoNotaFiscal())) {
                    cabecalho = unidadeOrganizacional.getConfiguracao().getCabecalhoNotaFiscal();
                }
            }
        }
    }

    public List<CabecalhoRodape> autocompletaCabecalho(String nome) {

        return cabecalhoRodapeServico.autoCompleteCabecalho(nome, userLogado.getUnidadeOrganizacional());
    }

    public List<CabecalhoRodape> autocompletaRodape(String nome) {
        return cabecalhoRodapeServico.autoCompleteRodape(nome, userLogado.getUnidadeOrganizacional());
    }

    public CabecalhoRodapeServico getCabecalhoRodapeServico() {
        return cabecalhoRodapeServico;
    }

    public void salvarConfiguracao() {
        if (Utils.isNotEmpty(unidadeOrganizacional.getConfiguracao())) {
            Configuracao config = configuracaoServico.find(unidadeOrganizacional.getConfiguracao().getId());
            if (Utils.isNotEmpty(cabecalho)) {
                config.setCabecalhoNotaFiscal(cabecalho);
            }
            if (Utils.isNotEmpty(rodape)) {
                config.setRodapeNotaFiscal(rodape);
            }
            configuracaoServico.Update(config);
        } else {
            Configuracao config = new Configuracao();
            if (Utils.isNotEmpty(cabecalho)) {
                config.setCabecalhoNotaFiscal(cabecalho);
            }
            if (Utils.isNotEmpty(rodape)) {
                config.setRodapeNotaFiscal(rodape);
            }
            configuracaoServico.Save(config);
            unidadeOrganizacional.setConfiguracao(config);
            unidadeOrganizacionalServico.Update(unidadeOrganizacional);
        }
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "notaFiscal.xhtml");
    }

    public void setCabecalhoRodapeServico(CabecalhoRodapeServico cabecalhoRodapeServico) {
        this.cabecalhoRodapeServico = cabecalhoRodapeServico;
    }

    public ConfiguracaoServico getConfiguracaoServico() {
        return configuracaoServico;
    }

    public void setConfiguracaoServico(ConfiguracaoServico configuracaoServico) {
        this.configuracaoServico = configuracaoServico;
    }

    public UsuarioServico getUserServico() {
        return userServico;
    }

    public void setUserServico(UsuarioServico userServico) {
        this.userServico = userServico;
    }

    public UnidadeOrganizacionalServico getUnidadeOrganizacionalServico() {
        return unidadeOrganizacionalServico;
    }

    public void setUnidadeOrganizacionalServico(UnidadeOrganizacionalServico unidadeOrganizacionalServico) {
        this.unidadeOrganizacionalServico = unidadeOrganizacionalServico;
    }

    public Usuario getUserLogado() {
        return userLogado;
    }

    public void setUserLogado(Usuario userLogado) {
        this.userLogado = userLogado;
    }

    public CabecalhoRodape getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(CabecalhoRodape cabecalho) {
        this.cabecalho = cabecalho;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public CabecalhoRodape getRodape() {
        return rodape;
    }

    public void setRodape(CabecalhoRodape rodape) {
        this.rodape = rodape;
    }

    public List<CabecalhoRodape> getListcabecalhoRodape() {
        return listcabecalhoRodape;
    }

    public void setListcabecalhoRodape(List<CabecalhoRodape> listcabecalhoRodape) {
        this.listcabecalhoRodape = listcabecalhoRodape;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

}
