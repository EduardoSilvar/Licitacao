/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import Enum.StatusCumprimento;
import Enum.TipoRecebimentoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author eduardo
 */
@Entity
public class NotaFiscal implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_nota_fiscal", name = "seq_nota_fiscal", allocationSize = 1)
    @GeneratedValue(generator = "seq_nota_fiscal", strategy = GenerationType.AUTO)
    private Long id;
    private Long numero;
    private String descricao;
    @OneToOne
    private Contratado contratado;
    private BigDecimal valor;
    private Date dataPagamento;
    @OneToOne
    private Usuario responsavel;
    @OneToOne
    private Contrato contrato;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Anexo> anexos;
    @Enumerated(EnumType.STRING)
    private TipoRecebimentoEnum tipoRecebimento;
    @OneToOne
    private UnidadeOrganizacional unidadeOrganizacional;
    private String lotacao;
    private String atoDesignacao;
    private Date apartir;
    private boolean ativo = true;
    private Date dataExtrato;
    private Date periodoFiscalizado;
    private Date inicioFiscalizado;
    private Date finalFiscalizado;
    @Enumerated(EnumType.STRING)
    private StatusCumprimento obrigacaoMensal;
    private boolean obrigacaoMensalSeAplica;
    @Enumerated(EnumType.STRING)
    private StatusCumprimento prazoEstabelecido;
    private boolean prazoEstabelecidoSeAplica;
    @Enumerated(EnumType.STRING)
    private StatusCumprimento documentoObrigatorio;
    private boolean documentoObrigatorioSeAplica;
    @Enumerated(EnumType.STRING)
    private StatusCumprimento relatorio;
    private boolean relatorioSeAplica;
    @Enumerated(EnumType.STRING)
    private StatusCumprimento qualidadeEsperada;
    private boolean qualidadeEsperadaSeAplica;
    @Enumerated(EnumType.STRING)
    private StatusCumprimento informouSituacao;
    private boolean informouSituacaoSeAplica;
    @Enumerated(EnumType.STRING)
    private StatusCumprimento diligenciaNecessarias;
    private boolean diligenciaNecessariasSeAplica;
    private String garantiaContratual;

    @Enumerated(EnumType.STRING)
    private StatusCumprimento notificacaoExtrajudicial;

    @Enumerated(EnumType.STRING)
    private StatusCumprimento procedimentoPenalizacao;

    @Enumerated(EnumType.STRING)
    private StatusCumprimento suspensaoParalizacao;

    @Enumerated(EnumType.STRING)
    private StatusCumprimento necessidadeRescisao;
    
    private String consideracoes;

    public NotaFiscal() {
    }

    public NotaFiscal(Long id, Long numero, String descricao, Contratado contratado, BigDecimal valor, Date dataPagamento, Usuario responsavel, Contrato contrato, List<Anexo> anexos, TipoRecebimentoEnum tipoRecebimento, UnidadeOrganizacional unidadeOrganizacional, String lotacao, String atoDesignacao, Date apartir, Date dataExtrato, Date periodoFiscalizado, Date inicioFiscalizado, Date finalFiscalizado, StatusCumprimento obrigacaoMensal, boolean obrigacaoMensalSeAplica, StatusCumprimento prazoEstabelecido, boolean prazoEstabelecidoSeAplica, StatusCumprimento documentoObrigatorio, boolean documentoObrigatorioSeAplica, StatusCumprimento relatorio, boolean relatorioSeAplica, StatusCumprimento qualidadeEsperada, boolean qualidadeEsperadaSeAplica, StatusCumprimento informouSituacao, boolean informouSituacaoSeAplica, StatusCumprimento diligenciaNecessarias, boolean diligenciaNecessariasSeAplica, String garantiaContratual, StatusCumprimento notificacaoExtrajudicial, StatusCumprimento procedimentoPenalizacao, StatusCumprimento suspensaoParalizacao, StatusCumprimento necessidadeRescisao, String observacoes) {
        this.id = id;
        this.numero = numero;
        this.descricao = descricao;
        this.contratado = contratado;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.responsavel = responsavel;
        this.contrato = contrato;
        this.anexos = anexos;
        this.tipoRecebimento = tipoRecebimento;
        this.unidadeOrganizacional = unidadeOrganizacional;
        this.lotacao = lotacao;
        this.atoDesignacao = atoDesignacao;
        this.apartir = apartir;
        this.dataExtrato = dataExtrato;
        this.periodoFiscalizado = periodoFiscalizado;
        this.inicioFiscalizado = inicioFiscalizado;
        this.finalFiscalizado = finalFiscalizado;
        this.obrigacaoMensal = obrigacaoMensal;
        this.obrigacaoMensalSeAplica = obrigacaoMensalSeAplica;
        this.prazoEstabelecido = prazoEstabelecido;
        this.prazoEstabelecidoSeAplica = prazoEstabelecidoSeAplica;
        this.documentoObrigatorio = documentoObrigatorio;
        this.documentoObrigatorioSeAplica = documentoObrigatorioSeAplica;
        this.relatorio = relatorio;
        this.relatorioSeAplica = relatorioSeAplica;
        this.qualidadeEsperada = qualidadeEsperada;
        this.qualidadeEsperadaSeAplica = qualidadeEsperadaSeAplica;
        this.informouSituacao = informouSituacao;
        this.informouSituacaoSeAplica = informouSituacaoSeAplica;
        this.diligenciaNecessarias = diligenciaNecessarias;
        this.diligenciaNecessariasSeAplica = diligenciaNecessariasSeAplica;
        this.garantiaContratual = garantiaContratual;
        this.notificacaoExtrajudicial = notificacaoExtrajudicial;
        this.procedimentoPenalizacao = procedimentoPenalizacao;
        this.suspensaoParalizacao = suspensaoParalizacao;
        this.necessidadeRescisao = necessidadeRescisao;
        this.consideracoes = observacoes;
    }

    public String getConsideracoes() {
        return consideracoes;
    }

    public void setConsideracoes(String consideracoes) {
        this.consideracoes = consideracoes;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Contratado getContratado() {
        return contratado;
    }

    public void setContratado(Contratado contratado) {
        this.contratado = contratado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public List<Anexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexo> anexos) {
        this.anexos = anexos;
    }

    public TipoRecebimentoEnum getTipoRecebimento() {
        return tipoRecebimento;
    }

    public void setTipoRecebimento(TipoRecebimentoEnum tipoRecebimento) {
        this.tipoRecebimento = tipoRecebimento;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

    public String getLotacao() {
        return lotacao;
    }

    public void setLotacao(String lotacao) {
        this.lotacao = lotacao;
    }

    public String getAtoDesignacao() {
        return atoDesignacao;
    }

    public void setAtoDesignacao(String atoDesignacao) {
        this.atoDesignacao = atoDesignacao;
    }

    public Date getApartir() {
        return apartir;
    }

    public void setApartir(Date apartir) {
        this.apartir = apartir;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataExtrato() {
        return dataExtrato;
    }

    public void setDataExtrato(Date dataExtrato) {
        this.dataExtrato = dataExtrato;
    }

    public Date getPeriodoFiscalizado() {
        return periodoFiscalizado;
    }

    public void setPeriodoFiscalizado(Date periodoFiscalizado) {
        this.periodoFiscalizado = periodoFiscalizado;
    }

    public Date getInicioFiscalizado() {
        return inicioFiscalizado;
    }

    public void setInicioFiscalizado(Date inicioFiscalizado) {
        this.inicioFiscalizado = inicioFiscalizado;
    }

    public Date getFinalFiscalizado() {
        return finalFiscalizado;
    }

    public void setFinalFiscalizado(Date finalFiscalizado) {
        this.finalFiscalizado = finalFiscalizado;
    }

    public StatusCumprimento getObrigacaoMensal() {
        return obrigacaoMensal;
    }

    public void setObrigacaoMensal(StatusCumprimento obrigacaoMensal) {
        this.obrigacaoMensal = obrigacaoMensal;
    }

    public boolean isObrigacaoMensalSeAplica() {
        return obrigacaoMensalSeAplica;
    }

    public void setObrigacaoMensalSeAplica(boolean obrigacaoMensalSeAplica) {
        this.obrigacaoMensalSeAplica = obrigacaoMensalSeAplica;
    }

    public StatusCumprimento getPrazoEstabelecido() {
        return prazoEstabelecido;
    }

    public void setPrazoEstabelecido(StatusCumprimento prazoEstabelecido) {
        this.prazoEstabelecido = prazoEstabelecido;
    }

    public boolean isPrazoEstabelecidoSeAplica() {
        return prazoEstabelecidoSeAplica;
    }

    public void setPrazoEstabelecidoSeAplica(boolean prazoEstabelecidoSeAplica) {
        this.prazoEstabelecidoSeAplica = prazoEstabelecidoSeAplica;
    }

    public StatusCumprimento getDocumentoObrigatorio() {
        return documentoObrigatorio;
    }

    public void setDocumentoObrigatorio(StatusCumprimento documentoObrigatorio) {
        this.documentoObrigatorio = documentoObrigatorio;
    }

    public boolean isDocumentoObrigatorioSeAplica() {
        return documentoObrigatorioSeAplica;
    }

    public void setDocumentoObrigatorioSeAplica(boolean documentoObrigatorioSeAplica) {
        this.documentoObrigatorioSeAplica = documentoObrigatorioSeAplica;
    }

    public StatusCumprimento getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(StatusCumprimento relatorio) {
        this.relatorio = relatorio;
    }

    public boolean isRelatorioSeAplica() {
        return relatorioSeAplica;
    }

    public void setRelatorioSeAplica(boolean relatorioSeAplica) {
        this.relatorioSeAplica = relatorioSeAplica;
    }

    public StatusCumprimento getQualidadeEsperada() {
        return qualidadeEsperada;
    }

    public void setQualidadeEsperada(StatusCumprimento qualidadeEsperada) {
        this.qualidadeEsperada = qualidadeEsperada;
    }

    public boolean isQualidadeEsperadaSeAplica() {
        return qualidadeEsperadaSeAplica;
    }

    public void setQualidadeEsperadaSeAplica(boolean qualidadeEsperadaSeAplica) {
        this.qualidadeEsperadaSeAplica = qualidadeEsperadaSeAplica;
    }

    public StatusCumprimento getInformouSituacao() {
        return informouSituacao;
    }

    public void setInformouSituacao(StatusCumprimento informouSituacao) {
        this.informouSituacao = informouSituacao;
    }

    public boolean isInformouSituacaoSeAplica() {
        return informouSituacaoSeAplica;
    }

    public void setInformouSituacaoSeAplica(boolean informouSituacaoSeAplica) {
        this.informouSituacaoSeAplica = informouSituacaoSeAplica;
    }

    public StatusCumprimento getDiligenciaNecessarias() {
        return diligenciaNecessarias;
    }

    public void setDiligenciaNecessarias(StatusCumprimento diligenciaNecessarias) {
        this.diligenciaNecessarias = diligenciaNecessarias;
    }

    public boolean isDiligenciaNecessariasSeAplica() {
        return diligenciaNecessariasSeAplica;
    }

    public void setDiligenciaNecessariasSeAplica(boolean diligenciaNecessariasSeAplica) {
        this.diligenciaNecessariasSeAplica = diligenciaNecessariasSeAplica;
    }

    public String getGarantiaContratual() {
        return garantiaContratual;
    }

    public void setGarantiaContratual(String garantiaContratual) {
        this.garantiaContratual = garantiaContratual;
    }

    public StatusCumprimento getNotificacaoExtrajudicial() {
        return notificacaoExtrajudicial;
    }

    public void setNotificacaoExtrajudicial(StatusCumprimento notificacaoExtrajudicial) {
        this.notificacaoExtrajudicial = notificacaoExtrajudicial;
    }

    public StatusCumprimento getProcedimentoPenalizacao() {
        return procedimentoPenalizacao;
    }

    public void setProcedimentoPenalizacao(StatusCumprimento procedimentoPenalizacao) {
        this.procedimentoPenalizacao = procedimentoPenalizacao;
    }

    public StatusCumprimento getSuspensaoParalizacao() {
        return suspensaoParalizacao;
    }

    public void setSuspensaoParalizacao(StatusCumprimento suspensaoParalizacao) {
        this.suspensaoParalizacao = suspensaoParalizacao;
    }

    public StatusCumprimento getNecessidadeRescisao() {
        return necessidadeRescisao;
    }

    public void setNecessidadeRescisao(StatusCumprimento necessidadeRescisao) {
        this.necessidadeRescisao = necessidadeRescisao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.numero);
        hash = 59 * hash + Objects.hashCode(this.descricao);
        hash = 59 * hash + Objects.hashCode(this.contratado);
        hash = 59 * hash + Objects.hashCode(this.valor);
        hash = 59 * hash + Objects.hashCode(this.dataPagamento);
        hash = 59 * hash + Objects.hashCode(this.responsavel);
        hash = 59 * hash + Objects.hashCode(this.contrato);
        hash = 59 * hash + Objects.hashCode(this.anexos);
        hash = 59 * hash + Objects.hashCode(this.tipoRecebimento);
        hash = 59 * hash + Objects.hashCode(this.unidadeOrganizacional);
        hash = 59 * hash + Objects.hashCode(this.lotacao);
        hash = 59 * hash + Objects.hashCode(this.atoDesignacao);
        hash = 59 * hash + Objects.hashCode(this.apartir);
        hash = 59 * hash + (this.ativo ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.dataExtrato);
        hash = 59 * hash + Objects.hashCode(this.periodoFiscalizado);
        hash = 59 * hash + Objects.hashCode(this.inicioFiscalizado);
        hash = 59 * hash + Objects.hashCode(this.finalFiscalizado);
        hash = 59 * hash + Objects.hashCode(this.obrigacaoMensal);
        hash = 59 * hash + (this.obrigacaoMensalSeAplica ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.prazoEstabelecido);
        hash = 59 * hash + (this.prazoEstabelecidoSeAplica ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.documentoObrigatorio);
        hash = 59 * hash + (this.documentoObrigatorioSeAplica ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.relatorio);
        hash = 59 * hash + (this.relatorioSeAplica ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.qualidadeEsperada);
        hash = 59 * hash + (this.qualidadeEsperadaSeAplica ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.informouSituacao);
        hash = 59 * hash + (this.informouSituacaoSeAplica ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.diligenciaNecessarias);
        hash = 59 * hash + (this.diligenciaNecessariasSeAplica ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.garantiaContratual);
        hash = 59 * hash + Objects.hashCode(this.notificacaoExtrajudicial);
        hash = 59 * hash + Objects.hashCode(this.procedimentoPenalizacao);
        hash = 59 * hash + Objects.hashCode(this.suspensaoParalizacao);
        hash = 59 * hash + Objects.hashCode(this.necessidadeRescisao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotaFiscal other = (NotaFiscal) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (this.obrigacaoMensalSeAplica != other.obrigacaoMensalSeAplica) {
            return false;
        }
        if (this.prazoEstabelecidoSeAplica != other.prazoEstabelecidoSeAplica) {
            return false;
        }
        if (this.documentoObrigatorioSeAplica != other.documentoObrigatorioSeAplica) {
            return false;
        }
        if (this.relatorioSeAplica != other.relatorioSeAplica) {
            return false;
        }
        if (this.qualidadeEsperadaSeAplica != other.qualidadeEsperadaSeAplica) {
            return false;
        }
        if (this.informouSituacaoSeAplica != other.informouSituacaoSeAplica) {
            return false;
        }
        if (this.diligenciaNecessariasSeAplica != other.diligenciaNecessariasSeAplica) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.lotacao, other.lotacao)) {
            return false;
        }
        if (!Objects.equals(this.atoDesignacao, other.atoDesignacao)) {
            return false;
        }
        if (!Objects.equals(this.garantiaContratual, other.garantiaContratual)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.contratado, other.contratado)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.dataPagamento, other.dataPagamento)) {
            return false;
        }
        if (!Objects.equals(this.responsavel, other.responsavel)) {
            return false;
        }
        if (!Objects.equals(this.contrato, other.contrato)) {
            return false;
        }
        if (!Objects.equals(this.anexos, other.anexos)) {
            return false;
        }
        if (this.tipoRecebimento != other.tipoRecebimento) {
            return false;
        }
        if (!Objects.equals(this.unidadeOrganizacional, other.unidadeOrganizacional)) {
            return false;
        }
        if (!Objects.equals(this.apartir, other.apartir)) {
            return false;
        }
        if (!Objects.equals(this.dataExtrato, other.dataExtrato)) {
            return false;
        }
        if (!Objects.equals(this.periodoFiscalizado, other.periodoFiscalizado)) {
            return false;
        }
        if (!Objects.equals(this.inicioFiscalizado, other.inicioFiscalizado)) {
            return false;
        }
        if (!Objects.equals(this.finalFiscalizado, other.finalFiscalizado)) {
            return false;
        }
        if (this.obrigacaoMensal != other.obrigacaoMensal) {
            return false;
        }
        if (this.prazoEstabelecido != other.prazoEstabelecido) {
            return false;
        }
        if (this.documentoObrigatorio != other.documentoObrigatorio) {
            return false;
        }
        if (this.relatorio != other.relatorio) {
            return false;
        }
        if (this.qualidadeEsperada != other.qualidadeEsperada) {
            return false;
        }
        if (this.informouSituacao != other.informouSituacao) {
            return false;
        }
        if (this.diligenciaNecessarias != other.diligenciaNecessarias) {
            return false;
        }
        if (this.notificacaoExtrajudicial != other.notificacaoExtrajudicial) {
            return false;
        }
        if (this.procedimentoPenalizacao != other.procedimentoPenalizacao) {
            return false;
        }
        if (this.suspensaoParalizacao != other.suspensaoParalizacao) {
            return false;
        }
        return this.necessidadeRescisao == other.necessidadeRescisao;
    }

    @Override
    public String toString() {
        return "NotaFiscal{" + "id=" + id + ", numero=" + numero + ", descricao=" + descricao + ", contratado=" + contratado + ", valor=" + valor + ", dataPagamento=" + dataPagamento + ", responsavel=" + responsavel + ", contrato=" + contrato + ", anexos=" + anexos + ", tipoRecebimento=" + tipoRecebimento + ", unidadeOrganizacional=" + unidadeOrganizacional + ", lotacao=" + lotacao + ", atoDesignacao=" + atoDesignacao + ", apartir=" + apartir + ", ativo=" + ativo + ", dataExtrato=" + dataExtrato + ", periodoFiscalizado=" + periodoFiscalizado + ", inicioFiscalizado=" + inicioFiscalizado + ", finalFiscalizado=" + finalFiscalizado + ", obrigacaoMensal=" + obrigacaoMensal + ", obrigacaoMensalSeAplica=" + obrigacaoMensalSeAplica + ", prazoEstabelecido=" + prazoEstabelecido + ", prazoEstabelecidoSeAplica=" + prazoEstabelecidoSeAplica + ", documentoObrigatorio=" + documentoObrigatorio + ", documentoObrigatorioSeAplica=" + documentoObrigatorioSeAplica + ", relatorio=" + relatorio + ", relatorioSeAplica=" + relatorioSeAplica + ", qualidadeEsperada=" + qualidadeEsperada + ", qualidadeEsperadaSeAplica=" + qualidadeEsperadaSeAplica + ", informouSituacao=" + informouSituacao + ", informouSituacaoSeAplica=" + informouSituacaoSeAplica + ", diligenciaNecessarias=" + diligenciaNecessarias + ", diligenciaNecessariasSeAplica=" + diligenciaNecessariasSeAplica + ", garantiaContratual=" + garantiaContratual + '}';
    }

}
