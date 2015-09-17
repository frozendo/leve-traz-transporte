package br.com.leveEtraz.dao;

import br.com.leveEtraz.entity.Mapa;

public interface IMapaDao {

	void save(Mapa mapa);
	
	boolean verificarNomeExiste(Mapa mapa);

}
