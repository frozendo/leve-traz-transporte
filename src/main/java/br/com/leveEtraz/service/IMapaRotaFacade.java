package br.com.leveEtraz.service;

import br.com.leveEtraz.dto.MapaDestinoDTO;
import br.com.leveEtraz.entity.Mapa;

/**
 * Interface para facade de {@link Mapa} e {@link Rota}
 * @author frozendo
 */
public interface IMapaRotaFacade {
	
	/**
	 * Metodo que realiza a chamada de metodo para salvar o mapa
	 * @param mapa
	 */
	void save(Mapa mapa);
	
	/**
	 * Metodo que realiza a busca da melhor rota para o mata solicitado
	 * @param mapaDestinoDTO
	 */
	void buscarMelhorRota(MapaDestinoDTO mapaDestinoDTO);

}
