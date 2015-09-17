package br.com.leveEtraz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antlr.StringUtils;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;
import br.com.leveEtraz.service.IMapaRotaFacade;
import br.com.leveEtraz.service.IMapaService;
import br.com.leveEtraz.service.IRotaService;

@Transactional
@Service
public class MapaRotaFacade implements IMapaRotaFacade {
	
	@Autowired
	private IRotaService rotaService;
	
	@Autowired
	private IMapaService mapaService;
	
	public void save(Mapa mapa) {
		mapaService.save(mapa);
		rotaService.save(mapa);
	}
	
	private List<Rota> createListRotas(Set<Rota> listRotas) {
		List<Rota> rotas = new ArrayList<Rota>();
		for (Rota rota : listRotas) {
			rotas.add(rota);
		}
		return rotas;
	}

}
