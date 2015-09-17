package br.com.leveEtraz.service;

import br.com.leveEtraz.entity.Mapa;

/**
 * Interface de service para {@link Mapa}
 * @author frozendo
 */
public interface IMapaService {

	/**
	 * Metodo que realiza a persistencia dos dados 
	 * @param mapa
	 */
	void save(Mapa mapa);
	
	/**
	 * Metodo que verifica se o nome cadastrado já existe
	 * @param mapa
	 * @return true se o nome existe e false caso contrário
	 */
	boolean verificarNomeExiste(Mapa mapa);

}
