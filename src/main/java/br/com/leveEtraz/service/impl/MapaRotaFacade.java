package br.com.leveEtraz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import antlr.StringUtils;
import br.com.leveEtraz.dto.RecebeMapaDestinoDTO;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;
import br.com.leveEtraz.exception.BusinessException;
import br.com.leveEtraz.exception.CollectionBusinessException;
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
	
	private String destinoFinal = "";
	Map<String, Integer> rotasCalculadas = null;
	
	public void save(Mapa mapa) {
		validarMapa(mapa);
		mapaService.save(mapa);
		rotaService.save(mapa);
	}

	@Override
	public void buscarMelhorRota(RecebeMapaDestinoDTO mapaDestinoDTO) {
		List<Rota> rotas = rotaService.recuperarRotasMapa(mapaDestinoDTO.getNomeMapa());
		destinoFinal = mapaDestinoDTO.getDestino().toString();
		rotasCalculadas = new HashMap<String, Integer>();
		calcularRotas(rotas, mapaDestinoDTO.getOrigem().toString(), "", 0);
		definirMelhorRota(mapaDestinoDTO);
	}
	
	private void definirMelhorRota(RecebeMapaDestinoDTO mapaDestinoDTO) {
		Integer menorDistancia = 0;
		String rotaFinal = "";
		for (String percurso : rotasCalculadas.keySet()) {
			if (menorDistancia == 0 || menorDistancia > rotasCalculadas.get(percurso)) {
				menorDistancia = rotasCalculadas.get(percurso);
				rotaFinal = percurso;
			}
		}
		mapaDestinoDTO.setCusto(mapaDestinoDTO.getValorCombustivel() * menorDistancia);
		mapaDestinoDTO.setMelhorRota(rotaFinal);
	}
	
	private void calcularRotas(List<Rota> rotas, String origemAtual, String percurso, Integer distanciaPercorrida) {
		Map<String, Integer> mapearRotas = new HashMap<String, Integer>();
		Rota rotaDestino = null;
		Integer distanciaAtual = distanciaPercorrida;
		
		for (Rota rota : rotas) {
			if (rota.getOrigem().toString().equals(origemAtual)) {
				if (rota.getDestino().toString().equals(destinoFinal)) {
					rotaDestino = rota;
					break;
				} else {
					mapearRotas.put(rota.getDestino().toString(), rota.getDistancia());
				}
			} 
		}
		
		if (rotaDestino == null) {	
			percurso += origemAtual + " ";		
			for (String novaOrigem : mapearRotas.keySet()) {
				Integer novaDistancia = distanciaAtual + mapearRotas.get(novaOrigem);
				calcularRotas(rotas, novaOrigem, percurso, novaDistancia);
			}			
		} else {
			percurso += origemAtual + " " + rotaDestino.getDestino();
			rotasCalculadas.put(percurso, distanciaPercorrida + rotaDestino.getDistancia());
		}
	}
	
	private void validarMapa(Mapa mapa) {
		validarCamposObrigatoriosMapa(mapa);
		validarRegrasMapa(mapa);
		
		for (Rota rota : mapa.getRotas()) {
			validarCamposObrigatoriosRota(rota);
			validarRegrasRota(rota, mapa.getId());
		}
	}
	
	private void validarCamposObrigatoriosMapa(Mapa mapa) {		
		if (mapa.getNome() == null || mapa.getNome().equals("")) {
			throw new BusinessException("O campo nome é obrigatório");
		}
	}
	
	private void validarRegrasMapa(Mapa mapa) {
		if (mapaService.verificarNomeExiste(mapa)) {
			throw new BusinessException("Mapa já cadastrado. Por favor verifique!");
		}
	}
	
	private void validarCamposObrigatoriosRota(Rota rota) {
		List<BusinessException> exceptions = new ArrayList<BusinessException>();
		if (rota.getOrigem() == null || rota.getOrigem().equals("")) {
			exceptions.add(new BusinessException("Rotas com a propriedade 'origem' vazia. Propriedade origem é obrigatória"));
		}		
		if (rota.getDestino() == null || rota.getDestino().equals("")) {
			exceptions.add(new BusinessException("Rotas com a propriedade 'destino' vazia. Propriedade destino é obrigatória"));
		}		
		if (rota.getDistancia() == null || rota.getDistancia().equals("")) {
			exceptions.add(new BusinessException("Rotas com a propriedade 'distancia' vazia. Propriedade distancia é obrigatória"));
		}
		if (exceptions.size() > 0) {
			throw new CollectionBusinessException(exceptions);
		}
	}
	
	private void validarRegrasRota(Rota rota, Long mapaId) {
		if (mapaId != null && rotaService.rotaExiste(rota, mapaId)) {
			throw new BusinessException("Mapa já cadastrado. Por favor verifique!");
		}
	}

}
