package br.com.leveEtraz.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leveEtraz.dao.IRotaDao;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;
import br.com.leveEtraz.service.IRotaService;

/**
 * Classe de service para {@link Mapa}
 * @author frozendo
 *
 */
@Transactional
@Service
public class RotaService implements IRotaService {
	
	@Autowired
	private IRotaDao rotaDao;

	@Override
	public void save(Mapa mapa) {
		for (Rota rota : mapa.getRotas()) {
			rota.setMapa(mapa);
			rotaDao.save(rota);
		}
	}

	@Override
	public boolean rotaExiste(Rota rota, Long mapaId) {
		return rotaDao.rotaExiste(rota, mapaId);
	}

	@Override
	public List<Rota> recuperarRotasMapa(String nomeMapa) {
		return rotaDao.buscarRotas(nomeMapa);
	}

}
