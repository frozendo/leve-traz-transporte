package br.com.leveEtraz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidade que mapeia a tabela MAPA
 * @author frozendo
 */
@Entity
@Table(name = "ROTA")
public class Rota {
	
	/**
	 * Identificador da tabela
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROTA_ID")
	private Long id;
	
	/**
	 * Ponto de origem no mapa
	 */
	@NotNull
	@Size(max = 20)
	@Column(name = "ROTA_TX_PONTO_ORIGEM")
	private String origem;
	
	/**
	 * Ponto de destino no mapa
	 */
	@NotNull
	@Size(max = 20)
	@Column(name = "ROTA_TX_PONTO_DESTINO")
	private String destino;
	
	/**
	 * Distancia entre a origem e o destino
	 */
	@NotNull
	@Column(name = "ROTA_NR_DISTANCIA")
	private Float distancia;
	
	/**
	 * Mapa ao qual a rota se relaciona
	 */
	@ManyToOne
	@JoinColumn(name = "MAPA_ID")
	@NotNull
	private Mapa mapa;
	
	/* GETTERS e SETTERS */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Float getDistancia() {
		return distancia;
	}

	public void setDistancia(Float distancia) {
		this.distancia = distancia;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
}
