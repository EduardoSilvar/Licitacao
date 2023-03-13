/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

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
    private boolean obrigacaoMensal;
    private boolean prazoEstabelecido;
    private boolean documentoObrigatorio;
    private boolean relatorio;
    private boolean qualidadeEsperada;
    private boolean informouSituacao;
    private boolean diligenciaNecessarias;
    private String garantiaContratual;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public NotaFiscal() {
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

    public Date getDataExtrato() {
        return dataExtrato;
    }

    public void setDataExtrato(Date dataExtrato) {
        this.dataExtrato = dataExtrato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

    public TipoRecebimentoEnum getTipoRecebimento() {
        return tipoRecebimento;
    }

    public void setTipoRecebimento(TipoRecebimentoEnum tipoRecebimento) {
        this.tipoRecebimento = tipoRecebimento;
    }

    public Date getPeriodoFiscalizado() {
        return periodoFiscalizado;
    }

    public void setPeriodoFiscalizado(Date periodoFiscalizado) {
        this.periodoFiscalizado = periodoFiscalizado;
    }

    public boolean isObrigacaoMensal() {
        return obrigacaoMensal;
    }

    public void setObrigacaoMensal(boolean obrigacaoMensal) {
        this.obrigacaoMensal = obrigacaoMensal;
    }

    public boolean isPrazoEstabelecido() {
        return prazoEstabelecido;
    }

    public void setPrazoEstabelecido(boolean prazoEstabelecido) {
        this.prazoEstabelecido = prazoEstabelecido;
    }

    public boolean isDocumentoObrigatorio() {
        return documentoObrigatorio;
    }

    public void setDocumentoObrigatorio(boolean documentoObrigatorio) {
        this.documentoObrigatorio = documentoObrigatorio;
    }

    public boolean isRelatorio() {
        return relatorio;
    }

    public void setRelatorio(boolean relatorio) {
        this.relatorio = relatorio;
    }

    public boolean isQualidadeEsperada() {
        return qualidadeEsperada;
    }

    public void setQualidadeEsperada(boolean qualidadeEsperada) {
        this.qualidadeEsperada = qualidadeEsperada;
    }

    public boolean isInformouSituacao() {
        return informouSituacao;
    }

    public void setInformouSituacao(boolean informouSituacao) {
        this.informouSituacao = informouSituacao;
    }

    public boolean isDiligenciaNecessarias() {
        return diligenciaNecessarias;
    }

    public void setDiligenciaNecessarias(boolean diligenciaNecessarias) {
        this.diligenciaNecessarias = diligenciaNecessarias;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getGarantiaContratual() {
        return garantiaContratual;
    }

    public void setGarantiaContratual(String garantiaContratual) {
        this.garantiaContratual = garantiaContratual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.descricao);
        hash = 83 * hash + Objects.hashCode(this.contratado);
        hash = 83 * hash + Objects.hashCode(this.valor);
        hash = 83 * hash + Objects.hashCode(this.dataPagamento);
        hash = 83 * hash + Objects.hashCode(this.responsavel);
        hash = 83 * hash + Objects.hashCode(this.contrato);
        hash = 83 * hash + Objects.hashCode(this.anexos);
        hash = 83 * hash + Objects.hashCode(this.tipoRecebimento);
        hash = 83 * hash + Objects.hashCode(this.unidadeOrganizacional);
        hash = 83 * hash + (this.ativo ? 1 : 0);
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
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
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
        return Objects.equals(this.unidadeOrganizacional, other.unidadeOrganizacional);
    }

    @Override
    public String toString() {
        return "NotaFiscal{" + "id=" + id + ", descricao=" + descricao + ", contratado=" + contratado + ", valor=" + valor + ", dataPagamento=" + dataPagamento + ", responsavel=" + responsavel + ", contrato=" + contrato + ", anexos=" + anexos + ", unidadeOrganizacional=" + unidadeOrganizacional + ", ativo=" + ativo + '}';
    }

}
