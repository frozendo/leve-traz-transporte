package br.com.leveEtraz.dao;

import br.com.leveEtraz.entity.Mapa;

/**
 * Interface de acesso a dados de {@link Mapa}
 * @author frozendo
 */
public interface IMapaDao {

	/**
	 * Metodo responsável por persistir
	 * @param mapa a ser inserido
	 */
	void save(Mapa mapa);
	
	/**
	 * Metodo que realiza a busca por um mapa com o nome que está sendo cadastrado
	 * Não são permitidos dois mapas com o mesmo nome
	 * @param mapa
	 * @return true se o mapa já existir no banco, false caso contrário
	 */
	boolean verificarNomeExiste(Mapa mapa);

}
