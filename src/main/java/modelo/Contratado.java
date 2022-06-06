/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Enum.NaturezaEnum;
import java.io.Serializable;
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
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    private String especialidade;
    @Enumerated
    private NaturezaEnum natureza;
    private String email;
    private String telefone;
    private String whatsapp;
    private boolean ativo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Contratado{" + "id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", cnpj=" + cnpj + ", endereco=" + endereco + ", especialidade=" + especialidade + ", natureza=" + natureza + ", email=" + email + ", telefone=" + telefone + ", whatsapp=" + whatsapp + '}';
    }

}
