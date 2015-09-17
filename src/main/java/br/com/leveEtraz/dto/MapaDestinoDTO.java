package br.com.leveEtraz.dto;

public class RecebeMapaDestinoDTO {
	
	private String nomeMapa;
	
	private String origem;
	
	private String destino;
	
	private Double autonomiaVeiculo;
	
	private Integer valorCombustivel;
	
	private String melhorRota;
	
	private Integer custo;

	public String getNomeMapa() {
		return nomeMapa;
	}

	public void setNomeMapa(String nomeMapa) {
		this.nomeMapa = nomeMapa;
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

	public Double getAutonomiaVeiculo() {
		return autonomiaVeiculo;
	}

	public void setAutonomiaVeiculo(Double autonomiaVeiculo) {
		this.autonomiaVeiculo = autonomiaVeiculo;
	}

	public Integer getValorCombustivel() {
		return valorCombustivel;
	}

	public void setValorCombustivel(Integer valorCombustivel) {
		this.valorCombustivel = valorCombustivel;
	}

	public String getMelhorRota() {
		return melhorRota;
	}

	public void setMelhorRota(String melhorRota) {
		this.melhorRota = melhorRota;
	}

	public Integer getCusto() {
		return custo;
	}

	public void setCusto(Integer custo) {
		this.custo = custo;
	}

}
