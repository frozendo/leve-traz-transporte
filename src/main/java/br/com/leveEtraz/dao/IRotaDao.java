package br.com.leveEtraz.dao;

import java.util.List;

import br.com.leveEtraz.entity.Rota;

/**
 * Interface de acesso a dados de {@link Rota}
 * @author frozendo
 */
public interface IRotaDao {
	
	/**
	 * Metodo responsável por persistir
	 * @param mapa a ser inserido
	 */
	void save(Rota rota);
	
	/**
	 * Metodo que verifica se a rota já existe para esse mapa
	 * @param rota
	 * @param mapaId
	 * @return true se já existir, false caso contrário
	 */
	boolean rotaExiste(Rota rota, Long mapaId);

	/**
	 * Método que busca as rotas disponíveis para o mapa solicitado
	 * @param nomeMapa
	 * @return list de rotas disponíveis
	 */
	List<Rota> buscarRotas(String nomeMapa);

}
