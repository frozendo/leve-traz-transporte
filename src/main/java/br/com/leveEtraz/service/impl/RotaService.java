package br.com.leveEtraz.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leveEtraz.dao.IRotaDao;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;
import br.com.leveEtraz.service.IRotaService;

@Transactional
@Service
public class RotaService implements IRotaService {
	
	@Autowired
	private IRotaDao rotaDao;

	public void save(Mapa mapa) {
		for (Rota rota : mapa.getRotas()) {
			rota.setMapa(mapa);
			rotaDao.save(rota);
		}
	}

}
