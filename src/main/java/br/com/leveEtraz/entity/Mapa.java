package br.com.leveEtraz.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MAPA")
public class Mapa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MAPA_ID")
	private Long id;

	@NotNull
	@Column(name = "MAPA_TX_NOME") 
	private String nome;

	@OneToMany(mappedBy = "mapa", fetch = FetchType.LAZY)
	private Set<Rota> rotas = new HashSet<Rota>();

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

	public Set<Rota> getRotas() {
		return rotas;
	}

	public void setRotas(Set<Rota> rotas) {
		this.rotas = rotas;
	}

}
