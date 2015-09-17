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

@Entity
@Table(name = "ROTA")
public class Rota {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROTA_ID")
	private Long id;
	
	@NotNull
	@Column(name = "ROTA_NR_PONTO_ORIGEM")
	private Character origem;
	
	@NotNull
	@Column(name = "ROTA_NR_PONTO_DESTINO")
	private Character destino;
	
	@NotNull
	@Column(name = "ROTA_NR_DISTANCIA")
	private Integer distancia;
	
	@ManyToOne
	@JoinColumn(name = "MAPA_ID")
	@NotNull
	private Mapa mapa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Character getOrigem() {
		return origem;
	}

	public void setOrigem(Character origem) {
		this.origem = origem;
	}

	public Character getDestino() {
		return destino;
	}

	public void setDestino(Character destino) {
		this.destino = destino;
	}

	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
}
