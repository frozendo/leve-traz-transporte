package br.com.leveEtraz.dto;

/**
 * Classe responsavel por transferir os dados entre as camadas
 * quando o usuário solicita a menor rota para um destino
 * @author frozendo
 */
public class MapaDestinoDTO {
	
	/**
	 * Nome do mapa solicitado
	 */
	private String nomeMapa;
	
	/**
	 * Ponto de origem
	 */
	private String origem;
	
	/**
	 * Ponto de destino
	 */
	private String destino;
	
	/**
	 * Capacidade que o veiculo é capaz de rodar sem abastecer
	 */
	private Double autonomiaVeiculo;
	
	/**
	 * Valor do litro do combustivel
	 */
	private Float valorCombustivel;
	
	/**
	 * Melhor caminho encontrado entre os pontos de origem e destino
	 */
	private String melhorRota;
	
	/**
	 * Custo da execução da melhor rota
	 */
	private Float custo;

	/* GETTERS e SETTERS */
	
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

	public Float getValorCombustivel() {
		return valorCombustivel;
	}

	public void setValorCombustivel(Float valorCombustivel) {
		this.valorCombustivel = valorCombustivel;
	}

	public String getMelhorRota() {
		return melhorRota;
	}

	public void setMelhorRota(String melhorRota) {
		this.melhorRota = melhorRota;
	}

	public Float getCusto() {
		return custo;
	}

	public void setCusto(Float custo) {
		this.custo = custo;
	}

}
