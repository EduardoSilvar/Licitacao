/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.StatusContrato;
import static Gerenciador.managerLogin.VerificarLogin;
import static Gerenciador.managerLogin.getObjectSession;
import Servico.AnexoServico;
import Servico.ContratadoServico;
import Servico.ContratoServico;
import Servico.SetorServico;
import Servico.TipoLicitacaoServico;
import Servico.UsuarioServico;
import Servico.tipoContratoServico;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Anexo;
import modelo.Contratado;
import modelo.Contrato;
import modelo.Setor;
import modelo.TipoAnexo;
import modelo.TipoContrato;
import modelo.TipoLicitacao;
import modelo.Usuario;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import util.Msg;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class managerContrato extends managerPrincipal implements Serializable {

    @EJB
    private ContratoServico contratoServico;
    @EJB
    private ContratadoServico contratadoServico;
    @EJB
    private UsuarioServico userServico;
    @EJB
    private tipoContratoServico tipocontratoServico;
    @EJB
    private TipoLicitacaoServico tipoLicitacaoServico;
    @EJB
    private SetorServico setorServico;
    @EJB
    private AnexoServico anexoServico;

    private Anexo anexo;
    private TipoAnexo tipoAnexo;

    private Contrato contrato;
    private List<Usuario> responsaveis;
    private List<TipoLicitacao> tiposLicitacao;
    private List<Contrato> contratos;
    private List<Contratado> contratados;
    private List<TipoContrato> tiposContratos;
    private List<Setor> setores;
    private Usuario user;

    @Override
    public void carregar(String param) {
        try {
            user = (Usuario) getObjectSession("usuarioLogado");
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        instanciarSelect();
        InstanciarContrato();
        InstanciarContratos();
        this.anexo = new Anexo();
        this.contrato = contratoServico.find(Long.parseLong(param));
        this.contratos = new ArrayList<>();
        this.tiposContratos = tipocontratoServico.FindAll();
        this.responsaveis = userServico.FindAll();
        this.setores = setorServico.FindAll();

    }

    @Override
    public void instanciar() {
        try {
            user = (Usuario) getObjectSession("usuarioLogado");
            VerificarLogin();
        } catch (IOException ex) {
            Logger.getLogger(managerContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.anexo = new Anexo();
        instanciarSelect();
        InstanciarContrato();
        InstanciarContratos();

    }

    public void instanciarSelect() {
        this.setores = setorServico.FindAll();
        this.tiposContratos = tipocontratoServico.FindAll();
        this.contratados = contratadoServico.FindAll();
        this.responsaveis = userServico.FindAll();
        this.tiposLicitacao = tipoLicitacaoServico.FindAll();
    }

    @Override
    public String getUrlVisualizar() {
        return "contrato.xhtml?visualizar=" + this.contrato.getId();
    }

    @Override
    public String getUrlPesquisar() {
        return "pesquisarContrato.xhtml";
    }

    public void selecionarAnexo(FileUploadEvent event) {
        if (Utils.isNotEmpty(event)) {
            if (Utils.isNotEmpty(event.getFile())) {
                this.anexo.setArquivo(event.getFile());
            }
        }
        this.anexo.setTipoAnexo(TipoAnexo.CONTRATO);
//        PrimeFaces.current().executeScript("PF('dlgAnexo').show();");
        adicionarAnexo();
    }

    public void adicionarAnexo() {

        try {
            this.contrato.getAnexos().add(anexoServico.adicionarAnexo(this.anexo.getArquivo()));
            int posicao = contrato.getAnexos().size();
//            this.contrato.getAnexos().get(posicao - 1).setTipo(anexo.getTipo());
            this.contrato.getAnexos().get(posicao - 1).setArquivo(anexo.getArquivo());
            this.contrato.getAnexos().get(posicao - 1).setCaminho(TipoAnexo.CONTRATO);
            this.contrato.getAnexos().get(posicao - 1).setTipoAnexo(this.anexo.getTipoAnexo());
        } catch (SQLException | IOException ex) {
            Logger.getLogger(managerContrato.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        instanciarAnexo();
        Msg.messagemInfo(Msg.SuccessFull);

    }

    public void instanciarAnexo() {
        this.anexo = new Anexo();
    }

    public void salvar() {
        if (Utils.isNotEmpty(this.contrato.getNumeroContrato())) {
            if (contratoServico.existNumero(this.contrato.getNumeroContrato())) {
                Msg.messagemError("Número de contrato já registrado !");
            } else {
                this.contrato.setUnidadeOrganizacional(user.getUnidadeOrganizacional());
                this.contrato.setCorStatus(cores(this.contrato.getStatus()));
                contratoServico.Save(this.contrato);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contrato.xhtml?visualizar=" + this.contrato.getId());
            }
        }
    }

    public void pesquisar() {
        if (Utils.isNotEmpty(user.getUnidadeOrganizacional())) {
            this.contratos = contratoServico.findPesquisa(this.contrato, user.getUnidadeOrganizacional());
            if (this.contratos.size() > 0) {
                Msg.messagemInfo("Operação realizada com sucesso !!");
            } else {
                Msg.messagemError("Nenhum Contrato encontrato !");
            }
        } else {
            this.contratos = contratoServico.findPesquisa(this.contrato, null);
        }
    }

    public void deletar() {
        try {
            Contrato novoContrato = contratoServico.find(this.contrato.getId());
            novoContrato.setAtivo(false);
            contratoServico.Update(novoContrato);
            contratos.remove(novoContrato);
            Msg.messagemInfoRedirect("Processo realizado com sucesso !", "pesquisarContrato.xhtml");
            pesquisar();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void atualizar() {
        contratoServico.Update(this.contrato);
        Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contrato.xhtml?visualizar=" + this.contrato.getId());
    }

    public String abreviacao(StatusContrato stts) {
        String abreviacao = "teste";
        if (Utils.isNotEmpty(stts)) {
            switch (stts) {
                case APROVADO:
                    abreviacao = "AP";
                    break;
                case CANCELADO:
                    abreviacao = "CA";
                    break;
                case ESPERANDO_INICIO:
                    abreviacao = "EI";
                    break;
                case EXPIRADO:
                    abreviacao = "EP";
                    break;
                case FINALIZADO:
                    abreviacao = "FI";
                    break;
                case INICIADO:
                    abreviacao = "IN";
                    break;
                case PAGO:
                    abreviacao = "PG";
                    break;
                case PROXIMO_EXPIRAR:
                    abreviacao = "PE";
                    break;
                case VIGENCIA:
                    abreviacao = "VI";
                    break;
                default:
                    break;
            }
        }
        return abreviacao;

    }

    public String cores(StatusContrato stts) {
        String cor = "teste";
        if (Utils.isNotEmpty(stts)) {
            switch (stts) {
                case APROVADO:
                    cor = "005CFE";
                    break;
                case CANCELADO:
                    cor = "FF0100";
                    break;
                case ESPERANDO_INICIO:
                    cor = "7736FF";
                    break;
                case EXPIRADO:
                    cor = "FED800";
                    break;
                case FINALIZADO:
                    cor = "7E0EAC";
                    break;
                case INICIADO:
                    cor = "ced9db";
                    break;
                case PAGO:
                    cor = "89F629";
                    break;
                case PROXIMO_EXPIRAR:
                    cor = "3FF900";
                    break;
                case VIGENCIA:
                    cor = "88F629";
                    break;
                default:
                    break;
            }
        }
        return cor;

    }

    public boolean renderedTempoDeterminado() {
        return contrato.isPossuiTempoDeterminado();
    }

    public Contrato Pegar(Long id) {
        return contratoServico.find(id);
    }

    public void PegarTodos() {
        contratos = contratoServico.FindAll();
    }

    public void InstanciarContratos() {
        contratos = new ArrayList<>();
    }

    public void InstanciarContrato() {
        contrato = new Contrato();
        contrato.setAnexos(new ArrayList<Anexo>());
    }

    public Contrato getContrato() {
        return contrato;
    }

    public String getAnexo() {
        System.err.println(this.contrato.getAnexos().get(0).getUrl());
        return this.contrato.getAnexos().get(0).getUrl() + "2307202214590800581_logo.jpeg";
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<Contratado> getContratados() {
        return contratados;
    }

    public void setContratados(List<Contratado> contratados) {
        this.contratados = contratados;
    }

    public List<Usuario> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(List<Usuario> responsaveis) {
        this.responsaveis = responsaveis;
    }

    public ContratoServico getContratoServico() {
        return contratoServico;
    }

    public void setContratoServico(ContratoServico contratoServico) {
        this.contratoServico = contratoServico;
    }

    public ContratadoServico getContratadoServico() {
        return contratadoServico;
    }

    public void setContratadoServico(ContratadoServico contratadoServico) {
        this.contratadoServico = contratadoServico;
    }

    public UsuarioServico getUserServico() {
        return userServico;
    }

    public void setUserServico(UsuarioServico userServico) {
        this.userServico = userServico;
    }

    public tipoContratoServico getTipocontratoServico() {
        return tipocontratoServico;
    }

    public void setTipocontratoServico(tipoContratoServico tipocontratoServico) {
        this.tipocontratoServico = tipocontratoServico;
    }

    public List<TipoContrato> getTiposContratos() {
        return tiposContratos;
    }

    public void setTiposContratos(List<TipoContrato> tiposContratos) {
        this.tiposContratos = tiposContratos;
    }

    public TipoLicitacaoServico getTipoLicitacaoServico() {
        return tipoLicitacaoServico;
    }

    public void setTipoLicitacaoServico(TipoLicitacaoServico tipoLicitacaoServico) {
        this.tipoLicitacaoServico = tipoLicitacaoServico;
    }

    public List<TipoLicitacao> getTiposLicitacao() {
        return tiposLicitacao;
    }

    public void setTiposLicitacao(List<TipoLicitacao> tiposLicitacao) {
        this.tiposLicitacao = tiposLicitacao;
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public void setSetores(List<Setor> setores) {
        this.setores = setores;
    }

}
