package br.com.leveEtraz.service;

import br.com.leveEtraz.entity.Mapa;

public interface IMapaService {

	void save(Mapa mapa);
	
	boolean verificarNomeExiste(Mapa mapa);
	
	Mapa recuperarRotas(String nomeMapa);

}
