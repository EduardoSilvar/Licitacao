/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Enum.StatusContrato;
import Enum.TipoContratoEnum;
import Enum.TipoFiscalizacaoEnum;
import Enum.TipoLicitacaoEnum;
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
import javax.persistence.ManyToMany;
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
    private String NumeroContrato;
    private String NumeroProcesso;
    private String NumeroLicitacao;
    private BigDecimal valorRestante;
    @OneToOne
    private Setor setor;
    @Enumerated(EnumType.STRING)
    private TipoLicitacaoEnum tipoLicitacao;
    @Enumerated(EnumType.STRING)
    private TipoContratoEnum tipoContrato;
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
    private String objetoContrato;
    @OneToMany
    private List<Usuario> fiscalContrato;
    @Enumerated(EnumType.STRING)
    private TipoFiscalizacaoEnum tipoFiscalizacao;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private StatusContrato status;
    private String banco;
    @OneToOne
    private Usuario fiscal;
    private Long numeroConta;
    private Long digito;
    private Long agencia;
    private Long operacao;
    private BigDecimal saldoInicial;
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
    @OneToMany(cascade = CascadeType.ALL)
    private List<NotaFiscal> notasFiscais;

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

    public String getNumeroContrato() {
        return NumeroContrato;
    }

    public void setNumeroContrato(String NumeroContrato) {
        this.NumeroContrato = NumeroContrato;
    }


    public String getNumeroProcesso() {
        return NumeroProcesso;
    }

    public void setNumeroProcesso(String NumeroProcesso) {
        this.NumeroProcesso = NumeroProcesso;
    }

    public String getNumeroLicitacao() {
        return NumeroLicitacao;
    }

    public void setNumeroLicitacao(String NumeroLicitacao) {
        this.NumeroLicitacao = NumeroLicitacao;
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

    public String getObjetoContrato() {
        return objetoContrato;
    }

    public void setObjetoContrato(String objetoContrato) {
        this.objetoContrato = objetoContrato;
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

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
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

    public List<NotaFiscal> getNotasFiscais() {
        return notasFiscais;
    }

    public void setNotasFiscais(List<NotaFiscal> notasFiscais) {
        this.notasFiscais = notasFiscais;
    }

    public BigDecimal getValorRestante() {
        return valorRestante;
    }

    public void setValorRestante(BigDecimal valorRestante) {
        this.valorRestante = valorRestante;
    }

    public TipoLicitacaoEnum getTipoLicitacao() {
        return tipoLicitacao;
    }

    public void setTipoLicitacao(TipoLicitacaoEnum tipoLicitacao) {
        this.tipoLicitacao = tipoLicitacao;
    }

    public TipoContratoEnum getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContratoEnum tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public List<Usuario> getFiscaisContrato() {
        return fiscalContrato;
    }

    public void setFiscalContrato(List<Usuario> fiscalContrato) {
        this.fiscalContrato = fiscalContrato;
    }

    public TipoFiscalizacaoEnum getTipoFiscalizacao() {
        return tipoFiscalizacao;
    }

    public void setTipoFiscalizacao(TipoFiscalizacaoEnum tipoFiscalizacao) {
        this.tipoFiscalizacao = tipoFiscalizacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.unidadeOrganizacional);
        hash = 59 * hash + Objects.hashCode(this.contratado);
        hash = 59 * hash + Objects.hashCode(this.nome);
        hash = 59 * hash + Objects.hashCode(this.NumeroContrato);
        hash = 59 * hash + Objects.hashCode(this.NumeroProcesso);
        hash = 59 * hash + Objects.hashCode(this.NumeroLicitacao);
        hash = 59 * hash + Objects.hashCode(this.valorRestante);
        hash = 59 * hash + Objects.hashCode(this.setor);
        hash = 59 * hash + Objects.hashCode(this.tipoLicitacao);
        hash = 59 * hash + Objects.hashCode(this.tipoContrato);
        hash = 59 * hash + Objects.hashCode(this.anexos);
        hash = 59 * hash + Objects.hashCode(this.valor);
        hash = 59 * hash + (this.possuiTempoDeterminado ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.dataInicio);
        hash = 59 * hash + Objects.hashCode(this.dataFinal);
        hash = 59 * hash + Objects.hashCode(this.dataAssinatura);
        hash = 59 * hash + Objects.hashCode(this.dataRenovacao);
        hash = 59 * hash + Objects.hashCode(this.objetoContrato);
        hash = 59 * hash + Objects.hashCode(this.fiscalContrato);
        hash = 59 * hash + Objects.hashCode(this.tipoFiscalizacao);
        hash = 59 * hash + Objects.hashCode(this.descricao);
        hash = 59 * hash + Objects.hashCode(this.status);
        hash = 59 * hash + Objects.hashCode(this.banco);
        hash = 59 * hash + Objects.hashCode(this.numeroConta);
        hash = 59 * hash + Objects.hashCode(this.digito);
        hash = 59 * hash + Objects.hashCode(this.agencia);
        hash = 59 * hash + Objects.hashCode(this.operacao);
        hash = 59 * hash + Objects.hashCode(this.saldoInicial);
        hash = 59 * hash + (this.ativo ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.corStatus);
        hash = 59 * hash + Objects.hashCode(this.acrescimos);
        hash = 59 * hash + Objects.hashCode(this.supressoes);
        hash = 59 * hash + Objects.hashCode(this.renovacoes);
        hash = 59 * hash + Objects.hashCode(this.repactuacoes);
        hash = 59 * hash + Objects.hashCode(this.notasFiscais);
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
        if (!Objects.equals(this.nome, other.nome)) {
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
        if (!Objects.equals(this.valorRestante, other.valorRestante)) {
            return false;
        }
        if (!Objects.equals(this.setor, other.setor)) {
            return false;
        }
        if (this.tipoLicitacao != other.tipoLicitacao) {
            return false;
        }
        if (this.tipoContrato != other.tipoContrato) {
            return false;
        }
        if (!Objects.equals(this.anexos, other.anexos)) {
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
        if (this.tipoFiscalizacao != other.tipoFiscalizacao) {
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
        if (!Objects.equals(this.acrescimos, other.acrescimos)) {
            return false;
        }
        if (!Objects.equals(this.supressoes, other.supressoes)) {
            return false;
        }
        if (!Objects.equals(this.renovacoes, other.renovacoes)) {
            return false;
        }
        if (!Objects.equals(this.repactuacoes, other.repactuacoes)) {
            return false;
        }
        return Objects.equals(this.notasFiscais, other.notasFiscais);
    }

    public Usuario getFiscal() {
        return fiscal;
    }

    public void setFiscal(Usuario fiscal) {
        this.fiscal = fiscal;
    }

}
