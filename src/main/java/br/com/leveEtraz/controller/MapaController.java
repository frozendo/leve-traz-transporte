package br.com.leveEtraz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.service.IMapaService;

@RestController
public class MapaController {

	@Autowired
	private IMapaService mapaService;

	@RequestMapping(value = "/teste", method = RequestMethod.POST)
	public void teste(@RequestBody Mapa mapa) {
		mapaService.save(mapa);
	}

	@RequestMapping(value = "/teste", method = RequestMethod.GET)
	public void testeGet() {
		System.out.print("Teste WebService");
	}

}
