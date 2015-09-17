package br.com.leveEtraz.dao;

import java.util.List;

import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;

public interface IRotaDao {
	
	void save(Rota rota);
	
	boolean rotaExiste(Rota rota, Long mapaId);

	List<Rota> buscarRotas(String nomeMapa);

}
