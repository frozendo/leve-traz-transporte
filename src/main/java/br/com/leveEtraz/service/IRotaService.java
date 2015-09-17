package br.com.leveEtraz.service;

import java.util.List;
import java.util.Set;

import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;

public interface IRotaService {
	
	void save(Mapa mapa);
	
	boolean rotaExiste(Rota rota, Long mapaId);
	
	List<Rota> recuperarRotasMapa(String nomeMapa);

}
