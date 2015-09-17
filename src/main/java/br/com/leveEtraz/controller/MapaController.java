package br.com.leveEtraz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.service.IMapaRotaFacade;
import br.com.leveEtraz.service.IMapaService;

@RestController
public class MapaController {

	@Autowired
	private IMapaRotaFacade mapaRotaFacade;

	@RequestMapping(value = "/novoMapa", method = RequestMethod.POST)
	public void novoMapa(@RequestBody Mapa mapa) {
		mapaRotaFacade.save(mapa);
	}

	@RequestMapping(value = "/melhorRotaMapa", method = RequestMethod.GET)
	public void melhorRotaMapa() {
		System.out.print("Teste WebService");
	}

}
