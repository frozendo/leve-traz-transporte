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
import javax.validation.constraints.Size;

/**
 * Entidade que mapeia a tabela MAPA
 * @author frozendo
 */
@Entity
@Table(name = "MAPA")
public class Mapa {

	/**
	 * Identificador da tabela
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MAPA_ID")
	private Long id;

	/**
	 * Nome do mapa
	 */
	@NotNull
	@Size(max = 30)
	@Column(name = "MAPA_TX_NOME") 
	private String nome;

	/**
	 * Lista de rotas relacionadas ao mapa
	 */
	@OneToMany(mappedBy = "mapa", fetch = FetchType.LAZY)
	private Set<Rota> rotas = new HashSet<Rota>();

	/* GETTERS e SETTERS */
	
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
