/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Enum.NaturezaEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.*;

/**
 *
 * @author eduardo
 */
@Entity
public class Contratado implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "seq_contratado", name = "seq_contratado", allocationSize = 1)
    @GeneratedValue(generator = "seq_contratado", strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cpf;
    private String cnpj;
    private String rg;
    @OneToOne
    private UnidadeOrganizacional unidadeOrganizacional;
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    @OneToOne(cascade = CascadeType.ALL)
    private List<Anexo> anexos;
    private String especialidade;
    @Enumerated(EnumType.STRING)
    private NaturezaEnum natureza;
    private String email;
    private String telefone;
    private String whatsapp;
    private boolean ativo = true;
    private String inscricaoEstadual;
    private String banco;
    private Long numeroConta;
    private Long digito;
    private Long agencia;
    private Long operacao;
    private BigDecimal saldoInicial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Anexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexo> anexos) {
        this.anexos = anexos;
    }

    public NaturezaEnum getNatureza() {
        return natureza;
    }

    public void setNatureza(NaturezaEnum natureza) {
        this.natureza = natureza;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.nome);
        hash = 71 * hash + Objects.hashCode(this.cpf);
        hash = 71 * hash + Objects.hashCode(this.cnpj);
        hash = 71 * hash + Objects.hashCode(this.rg);
        hash = 71 * hash + Objects.hashCode(this.unidadeOrganizacional);
        hash = 71 * hash + Objects.hashCode(this.endereco);
        hash = 71 * hash + Objects.hashCode(this.anexos);
        hash = 71 * hash + Objects.hashCode(this.especialidade);
        hash = 71 * hash + Objects.hashCode(this.natureza);
        hash = 71 * hash + Objects.hashCode(this.email);
        hash = 71 * hash + Objects.hashCode(this.telefone);
        hash = 71 * hash + Objects.hashCode(this.whatsapp);
        hash = 71 * hash + (this.ativo ? 1 : 0);
        hash = 71 * hash + Objects.hashCode(this.inscricaoEstadual);
        hash = 71 * hash + Objects.hashCode(this.banco);
        hash = 71 * hash + Objects.hashCode(this.numeroConta);
        hash = 71 * hash + Objects.hashCode(this.digito);
        hash = 71 * hash + Objects.hashCode(this.agencia);
        hash = 71 * hash + Objects.hashCode(this.operacao);
        hash = 71 * hash + Objects.hashCode(this.saldoInicial);
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
        final Contratado other = (Contratado) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.rg, other.rg)) {
            return false;
        }
        if (!Objects.equals(this.especialidade, other.especialidade)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.whatsapp, other.whatsapp)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoEstadual, other.inscricaoEstadual)) {
            return false;
        }
        if (!Objects.equals(this.banco, other.banco)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.unidadeOrganizacional, other.unidadeOrganizacional)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        if (!Objects.equals(this.anexos, other.anexos)) {
            return false;
        }
        if (this.natureza != other.natureza) {
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
        return Objects.equals(this.saldoInicial, other.saldoInicial);
    }

    @Override
    public String toString() {
        return " " + nome + " ";
    }

}
