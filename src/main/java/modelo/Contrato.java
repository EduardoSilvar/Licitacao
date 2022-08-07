/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Enum.StatusContrato;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author eduardo
 */
@Entity
public class Contrato implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_contrato", name = "seq_contrato", allocationSize = 1)
    @GeneratedValue(generator = "seq_contrato", strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private UnidadeOrganizacional unidadeOrganizacional;
    @OneToOne
    private Contratado contratado;
    private String nome;
    private Long NumeroContrato;
    private Long NumeroProcesso;
    private Long NumeroLicitacao;
    @OneToOne
    private TipoContrato tipoContrato;
    @OneToOne
    private Setor setor;
    @OneToOne
    private TipoLicitacao tipoLicitacao;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Anexo> anexos;
    private BigDecimal valor;
    private boolean possuiTempoDeterminado;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataFinal;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAssinatura;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataRenovacao;
    private Long objetoContrato;
    @ManyToOne
    private Usuario fiscalContrato;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private StatusContrato status;
    private String banco;
    private Long numeroConta;
    private Long digito;
    private Long agencia;
    private Long operacao;
    private Long saldoInicial;
    private boolean ativo = true;
    private String corStatus;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Acrescimo> acrescimos;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Supressao> supressoes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Renovacao> renovacoes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Repactuacao> repactuacoes;

    public Contrato() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Acrescimo> getAcrescimos() {
        return acrescimos;
    }

    public void setAcrescimos(List<Acrescimo> acrescimos) {
        this.acrescimos = acrescimos;
    }

    public List<Supressao> getSupressoes() {
        return supressoes;
    }

    public void setSupressoes(List<Supressao> supressoes) {
        this.supressoes = supressoes;
    }

    public List<Renovacao> getRenovacoes() {
        return renovacoes;
    }

    public void setRenovacoes(List<Renovacao> renovacoes) {
        this.renovacoes = renovacoes;
    }

    public List<Repactuacao> getRepactuacoes() {
        return repactuacoes;
    }

    public void setRepactuacoes(List<Repactuacao> repactuacoes) {
        this.repactuacoes = repactuacoes;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

    public Contratado getContratado() {
        return contratado;
    }

    public void setContratado(Contratado contratado) {
        this.contratado = contratado;
    }

    public Long getNumeroContrato() {
        return NumeroContrato;
    }

    public void setNumeroContrato(Long NumeroContrato) {
        this.NumeroContrato = NumeroContrato;
    }

    public Long getNumeroProcesso() {
        return NumeroProcesso;
    }

    public void setNumeroProcesso(Long NumeroProcesso) {
        this.NumeroProcesso = NumeroProcesso;
    }

    public Long getNumeroLicitacao() {
        return NumeroLicitacao;
    }

    public void setNumeroLicitacao(Long NumeroLicitacao) {
        this.NumeroLicitacao = NumeroLicitacao;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public List<Anexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexo> anexos) {
        this.anexos = anexos;
    }

    public TipoLicitacao getTipoLicitacao() {
        return tipoLicitacao;
    }

    public void setTipoLicitacao(TipoLicitacao tipoLicitacao) {
        this.tipoLicitacao = tipoLicitacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isPossuiTempoDeterminado() {
        return possuiTempoDeterminado;
    }

    public void setPossuiTempoDeterminado(boolean possuiTempoDeterminado) {
        this.possuiTempoDeterminado = possuiTempoDeterminado;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(Date dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public Date getDataRenovacao() {
        return dataRenovacao;
    }

    public void setDataRenovacao(Date dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    public Long getObjetoContrato() {
        return objetoContrato;
    }

    public void setObjetoContrato(Long objetoContrato) {
        this.objetoContrato = objetoContrato;
    }

    public Usuario getFiscalContrato() {
        return fiscalContrato;
    }

    public void setFiscalContrato(Usuario fiscalContrato) {
        this.fiscalContrato = fiscalContrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusContrato getStatus() {
        return status;
    }

    public void setStatus(StatusContrato status) {
        this.status = status;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Long getDigito() {
        return digito;
    }

    public void setDigito(Long digito) {
        this.digito = digito;
    }

    public Long getAgencia() {
        return agencia;
    }

    public void setAgencia(Long agencia) {
        this.agencia = agencia;
    }

    public Long getOperacao() {
        return operacao;
    }

    public void setOperacao(Long operacao) {
        this.operacao = operacao;
    }

    public Long getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Long saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getCorStatus() {
        return corStatus;
    }

    public void setCorStatus(String corStatus) {
        this.corStatus = corStatus;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.unidadeOrganizacional);
        hash = 29 * hash + Objects.hashCode(this.contratado);
        hash = 29 * hash + Objects.hashCode(this.NumeroContrato);
        hash = 29 * hash + Objects.hashCode(this.NumeroProcesso);
        hash = 29 * hash + Objects.hashCode(this.NumeroLicitacao);
        hash = 29 * hash + Objects.hashCode(this.tipoContrato);
        hash = 29 * hash + Objects.hashCode(this.setor);
        hash = 29 * hash + Objects.hashCode(this.tipoLicitacao);
        hash = 29 * hash + Objects.hashCode(this.valor);
        hash = 29 * hash + (this.possuiTempoDeterminado ? 1 : 0);
        hash = 29 * hash + Objects.hashCode(this.dataInicio);
        hash = 29 * hash + Objects.hashCode(this.dataFinal);
        hash = 29 * hash + Objects.hashCode(this.dataAssinatura);
        hash = 29 * hash + Objects.hashCode(this.dataRenovacao);
        hash = 29 * hash + Objects.hashCode(this.objetoContrato);
        hash = 29 * hash + Objects.hashCode(this.fiscalContrato);
        hash = 29 * hash + Objects.hashCode(this.descricao);
        hash = 29 * hash + Objects.hashCode(this.status);
        hash = 29 * hash + Objects.hashCode(this.banco);
        hash = 29 * hash + Objects.hashCode(this.numeroConta);
        hash = 29 * hash + Objects.hashCode(this.digito);
        hash = 29 * hash + Objects.hashCode(this.agencia);
        hash = 29 * hash + Objects.hashCode(this.operacao);
        hash = 29 * hash + Objects.hashCode(this.saldoInicial);
        hash = 29 * hash + (this.ativo ? 1 : 0);
        hash = 29 * hash + Objects.hashCode(this.corStatus);
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
        final Contrato other = (Contrato) obj;
        if (this.possuiTempoDeterminado != other.possuiTempoDeterminado) {
            return false;
        }
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.banco, other.banco)) {
            return false;
        }
        if (!Objects.equals(this.corStatus, other.corStatus)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.unidadeOrganizacional, other.unidadeOrganizacional)) {
            return false;
        }
        if (!Objects.equals(this.contratado, other.contratado)) {
            return false;
        }
        if (!Objects.equals(this.NumeroContrato, other.NumeroContrato)) {
            return false;
        }
        if (!Objects.equals(this.NumeroProcesso, other.NumeroProcesso)) {
            return false;
        }
        if (!Objects.equals(this.NumeroLicitacao, other.NumeroLicitacao)) {
            return false;
        }
        if (!Objects.equals(this.tipoContrato, other.tipoContrato)) {
            return false;
        }
        if (!Objects.equals(this.setor, other.setor)) {
            return false;
        }
        if (!Objects.equals(this.tipoLicitacao, other.tipoLicitacao)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.dataInicio, other.dataInicio)) {
            return false;
        }
        if (!Objects.equals(this.dataFinal, other.dataFinal)) {
            return false;
        }
        if (!Objects.equals(this.dataAssinatura, other.dataAssinatura)) {
            return false;
        }
        if (!Objects.equals(this.dataRenovacao, other.dataRenovacao)) {
            return false;
        }
        if (!Objects.equals(this.objetoContrato, other.objetoContrato)) {
            return false;
        }
        if (!Objects.equals(this.fiscalContrato, other.fiscalContrato)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.numeroConta, other.numeroConta)) {
            return false;
        }
        if (!Objects.equals(this.digito, other.digito)) {
            return false;
        }
        if (!Objects.equals(this.agencia, other.agencia)) {
            return false;
        }
        if (!Objects.equals(this.operacao, other.operacao)) {
            return false;
        }
        if (!Objects.equals(this.saldoInicial, other.saldoInicial)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Contrato{" + "id=" + id + ", unidadeOrganizacional=" + unidadeOrganizacional + ", contratado=" + contratado + ", NumeroContrato=" + NumeroContrato + ", NumeroProcesso=" + NumeroProcesso + ", NumeroLicitacao=" + NumeroLicitacao + ", tipoContrato=" + tipoContrato + ", setor=" + setor + ", tipoLicitacao=" + tipoLicitacao + ", valor=" + valor + ", possuiTempoDeterminado=" + possuiTempoDeterminado + ", dataInicio=" + dataInicio + ", dataFinal=" + dataFinal + ", dataAssinatura=" + dataAssinatura + ", dataRenovacao=" + dataRenovacao + ", objetoContrato=" + objetoContrato + ", fiscalContrato=" + fiscalContrato + ", descricao=" + descricao + ", status=" + status + ", banco=" + banco + ", numeroConta=" + numeroConta + ", digito=" + digito + ", agencia=" + agencia + ", operacao=" + operacao + ", saldoInicial=" + saldoInicial + ", ativo=" + ativo + ", corStatus=" + corStatus + '}';
    }

}
