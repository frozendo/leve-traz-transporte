package br.com.leveEtraz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapaController {

	@RequestMapping(value = "/teste", method = RequestMethod.POST)
	public void teste() {
		System.out.print("Teste WebService");
	}

	@RequestMapping(value = "/teste", method = RequestMethod.GET)
	public void testeGet() {
		System.out.print("Teste WebService");
	}

}
