package br.com.leveEtraz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.leveEtraz.dao.IMapaDao;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.service.IMapaService;

@Service
@Transactional
public class MapaService implements IMapaService {

	@Autowired
	private IMapaDao mapaDao;

	@Override
	public void save(Mapa mapa) {
		mapaDao.save(mapa);
	}

	@Override
	public boolean verificarNomeExiste(Mapa mapa) {
		return mapaDao.verificarNomeExiste(mapa);
	}

	@Override
	public Mapa recuperarRotas(String nomeMapa) {
		return mapaDao.buscarRotas(nomeMapa);
	}

}
