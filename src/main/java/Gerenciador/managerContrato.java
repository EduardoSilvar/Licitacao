/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gerenciador;

import Enum.*;
import Servico.AnexoServico;
import Servico.ContratadoServico;
import Servico.ContratoServico;
import Servico.SetorServico;
import Servico.TipoLicitacaoServico;
import Servico.UsuarioServico;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Anexo;
import modelo.Contratado;
import modelo.Contrato;
import modelo.FiscalVO;
import modelo.Setor;
import modelo.TipoAnexo;
import modelo.TipoLicitacao;
import modelo.Usuario;
import org.primefaces.event.FileUploadEvent;
import util.Msg;
import org.apache.commons.io.FileUtils;
import util.Base64j;
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
    private List<Setor> setores;
    private Usuario fiscal;
    private Usuario userLogado;
    private boolean renderedFiscalIndividual;
    private boolean renderedFiscalComissao;

    @Override
    public void carregar(String param) {
        userLogado = userServico.getCurrentUser();
        instanciarSelect();
        InstanciarContrato();
        InstanciarContratos();
        this.anexo = new Anexo();
        this.contrato = contratoServico.find(Long.parseLong(param));
        this.contratos = new ArrayList<>();
        this.responsaveis = userServico.FindAll();
        this.setores = setorServico.FindAll();
        verificarTipoFiscal();
    }

    @Override
    public void instanciar() {
        userLogado = userServico.getCurrentUser();
        this.anexo = new Anexo();
        instanciarSelect();
        InstanciarContrato();
        InstanciarContratos();
        instanciarVerificacaoRendered();
    }

    public void instanciarSelect() {
        this.setores = setorServico.FindAll();
        this.contratados = contratadoServico.FindAll();
        this.responsaveis = userServico.FindAll();
        this.tiposLicitacao = tipoLicitacaoServico.FindAll();
    }

    public void instanciarVerificacaoRendered() {
        this.renderedFiscalIndividual = false;
        this.renderedFiscalComissao = false;
    }

    public void adicionarFiscal(Usuario fiscal) {
        if (this.contrato.getFiscaisContrato().contains(fiscal)) {
            Msg.messagemError("O fiscal já foi adicionado !");
        } else {
            this.contrato.getFiscaisContrato().add(fiscal);
            Msg.messagemInfo("Operação realizada com sucesso !");

        }
    }

    public void removerFiscal() {
        this.contrato.getFiscaisContrato().remove(fiscal);
        Msg.messagemInfo("Operação realizada com sucesso !");
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

    public void verificarTipoFiscal() {
        if (Utils.isNotEmpty(this.contrato.getTipoFiscalizacao())) {
            if (this.contrato.getTipoFiscalizacao().equals(TipoFiscalizacaoEnum.INDIVIDUAL)) {
                this.renderedFiscalIndividual = true;
                this.renderedFiscalComissao = false;
            } else {
                this.renderedFiscalIndividual = false;
                this.renderedFiscalComissao = true;
            }
        } else {
            System.err.println("entrou aqui");
            this.renderedFiscalComissao = false;
            this.renderedFiscalIndividual = false;
        }
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
                if (Utils.isNotEmpty(userLogado)) {
                    if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
                        this.contrato.setUnidadeOrganizacional(userLogado.getUnidadeOrganizacional());
                    }
                }
                this.contrato.setCorStatus(cores(this.contrato.getStatus()));
                this.contrato.setValorRestante(this.contrato.getValor());
                contratoServico.Save(this.contrato);
                Msg.messagemInfoRedirect("Operação realizada com sucesso !", "contrato.xhtml?visualizar=" + this.contrato.getId());
            }
        }
    }

    public void pesquisar() {
        if (Utils.isNotEmpty(userLogado.getUnidadeOrganizacional())) {
            this.contratos = contratoServico.findPesquisa(this.contrato, userLogado.getUnidadeOrganizacional(), this.fiscal);
            if (this.contratos.size() > 0) {
                Msg.messagemInfo("Operação realizada com sucesso !!");
            } else {
                Msg.messagemError("Nenhum Contrato encontrato !");
            }
        } else {
            this.contratos = contratoServico.findPesquisa(this.contrato, null, this.fiscal);
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
        Contrato contratoBD = contratoServico.find(this.contrato.getId());
        BigDecimal valorBD = contratoBD.getValor();
        BigDecimal valorCampo = this.contrato.getValor();
        BigDecimal valorFinal = BigDecimal.ZERO;
        if (valorBD.compareTo(valorCampo) == 1) {
            BigDecimal valorDiferenca = BigDecimal.ZERO;
            valorDiferenca = valorBD.subtract(valorCampo);
            valorFinal = contratoBD.getValorRestante().subtract(valorDiferenca);
        } else if (valorCampo.compareTo(valorBD) == 1) {
            BigDecimal valorDiferenca = BigDecimal.ZERO;
            valorDiferenca = valorCampo.subtract(valorBD);
            valorFinal = contratoBD.getValorRestante().add(valorDiferenca);
        } else {
            valorFinal = contratoBD.getValorRestante();
        }
        this.contrato.setValorRestante(valorFinal);
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

    public List<Contrato> PegarTodos() {
        contratos = contratoServico.FindAll();
        return contratos;
    }

    public void InstanciarContratos() {
        contratos = new ArrayList<>();
    }

    public void InstanciarContrato() {
        contrato = new Contrato();
        contrato.setFiscalContrato(new ArrayList<Usuario>());
        contrato.setAnexos(new ArrayList<Anexo>());
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void deletarAnexo(Anexo a) {
        this.contrato.getAnexos().remove(a);
        Msg.messagemInfo("Anexo removido com sucesso !");
    }

    public String anexoUrl(Anexo anexo) throws IOException {
        String caminhoLogo = "";

        caminhoLogo = anexo.getUrl() + "/" + anexo.getNome();
        String conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File(caminhoLogo)));

        conteudo_base64 = Base64j.encodeBytes(FileUtils.readFileToByteArray(new File(caminhoLogo)));
        return "data:image/png;base64," + conteudo_base64;
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

    public Anexo getAnexo() {
        return anexo;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public TipoAnexo getTipoAnexo() {
        return tipoAnexo;
    }

    public void setTipoAnexo(TipoAnexo tipoAnexo) {
        this.tipoAnexo = tipoAnexo;
    }

    public Usuario getUserLogado() {
        return userLogado;
    }

    public void setUserLogado(Usuario userLogado) {
        this.userLogado = userLogado;
    }

    public boolean isRenderedFiscalIndividual() {
        return renderedFiscalIndividual;
    }

    public void setRenderedFiscalIndividual(boolean renderedFiscalIndividual) {
        this.renderedFiscalIndividual = renderedFiscalIndividual;
    }

    public boolean isRenderedFiscalComissao() {
        return renderedFiscalComissao;
    }

    public void setRenderedFiscalComissao(boolean renderedFiscalComissao) {
        this.renderedFiscalComissao = renderedFiscalComissao;
    }

    public Usuario getFiscal() {
        return fiscal;
    }

    public void setFiscal(Usuario fiscal) {
        this.fiscal = fiscal;
    }

}
