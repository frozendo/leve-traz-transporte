package br.com.leveEtraz.service;

import java.util.List;

import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;

/**
 * Interface de service para {@link Rota}
 * @author frozendo
 */
public interface IRotaService {
	
	/**
	 * Metodo que realiza a chamada de metodo que salva os dados na base
	 * @param mapa
	 */
	void save(Mapa mapa);
	
	/**
	 * Metodo que verifica se a rota já existe
	 * @param rota
	 * @param mapaId
	 * @return true se a rota já existir e false caso contrário
	 */
	boolean rotaExiste(Rota rota, Long mapaId);
	
	/**
	 * Recupera as rotas relacionadas ao mapa passado no parametro
	 * @param nomeMapa
	 * @return lista de rotas
	 */
	List<Rota> recuperarRotasMapa(String nomeMapa);

}
