package br.com.leveEtraz.dao.impl;

import org.springframework.stereotype.Repository;

import br.com.leveEtraz.dao.IMapaDao;
import br.com.leveEtraz.entity.Mapa;

@Repository
public class MapaDao implements IMapaDao {

	@Override
	public void save(Mapa mapa) {
		System.out.println(mapa.getId());
		System.out.println(mapa.getNome());
	}

}
