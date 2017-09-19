package com.kazale.pontointeligente.api.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.kazale.pontointeligente.api.enums.PerfilEnum;
@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7821318533937256567L;
	
	public Long id;
	public String nome;
	public String email;
	public String senha;
	public String cpf;
	public BigDecimal valorHora;
	public Float qtdHorasTrabalhoDia;
	public Float qtdHorasAlmoco;
	public PerfilEnum perfil;
	public Date dataCriacao;
	public Date dataAtualizacao;
	private Empresa empresa;
	private List<Lancamento> lancamentos;

	
	
	
	public Funcionario() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="nome", nullable = true)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name="email", nullable = true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="senha", nullable = true)
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Column(name="cpf", nullable = true)
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Column(name="valor_hora", nullable = true)
	public BigDecimal getValorHora() {
		return valorHora;
	}
	@Transient
	public Optional<BigDecimal> getValorHoraOpt() {
		return Optional.ofNullable(valorHora);
	}	
	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}
	
	@Column(name="qtd_horas_trabalho_dia", nullable = true)
	public Float getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}
	@Transient
	public Optional<Float> getQtdHorasTrabalhoDiaOpt() {
		return Optional.ofNullable(qtdHorasTrabalhoDia);
	}
	public void setQtdHorasTrabalhoDia(Float qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}
	
	@Column(name="qtd_horas_almoco", nullable = true)
	public Float getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}
	@Transient
	public Optional<Float> getQtdHorasAlmocoOpt() {
		return Optional.ofNullable(qtdHorasAlmoco);
	}
	
	public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}
	@Enumerated(EnumType.STRING)
	@Column(name="perfil", nullable = true)
	public PerfilEnum getPerfil() {
		return perfil;
	}
	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}
	
	@Column(name="data_criacao", nullable = true)
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Column(name="data_atualizacao", nullable = true)
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	@PreUpdate
	public void preUpdate() {
		dataAtualizacao = new Date();
	}
	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		dataAtualizacao = atual;
		dataCriacao = atual;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + ", perfil=" + perfil + ", dataCriacao=" + dataCriacao + ", dataAtualizacao="
				+ dataAtualizacao + ", empresa=" + empresa + ", lancamentos=" + lancamentos + "]";
	}
	
	
	
	
	
}
