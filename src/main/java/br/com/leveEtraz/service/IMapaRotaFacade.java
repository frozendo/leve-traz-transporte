package br.com.leveEtraz.service;

import br.com.leveEtraz.dto.RecebeMapaDestinoDTO;
import br.com.leveEtraz.entity.Mapa;

public interface IMapaRotaFacade {
	
	void save(Mapa mapa);
	
	void buscarMelhorRota(RecebeMapaDestinoDTO mapaDestinoDTO);

}
