package br.com.leveEtraz.dao.impl;

import org.springframework.stereotype.Repository;

import br.com.leveEtraz.dao.IMapaDao;
import br.com.leveEtraz.entity.Mapa;

@Repository
public class MapaDao extends BaseDao implements IMapaDao {

	@Override
	public void save(Mapa mapa) {
		super.persist(mapa);
	}

}
