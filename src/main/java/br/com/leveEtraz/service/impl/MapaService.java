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
		validarCamposObrigatorios(mapa);
		validarRegras(mapa);
		mapaDao.save(mapa);
	}
	
	private void validarCamposObrigatorios(Mapa mapa) {
		if (mapa.getNome().equals("")) {
			//retornar mensagem de aviso
		}
		
		if (mapa.getRotas() == null || mapa.getRotas().size() == 0) {
			//retornar mensagem de aviso
		}
	}
	
	private void validarRegras(Mapa mapa) {
		if (mapaDao.verificarNomeExiste(mapa)) {
			//retornar mensagem de aviso			
		}
	}

}
