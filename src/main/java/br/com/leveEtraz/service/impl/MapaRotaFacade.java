package br.com.leveEtraz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.leveEtraz.dto.MapaDestinoDTO;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;
import br.com.leveEtraz.exception.BusinessException;
import br.com.leveEtraz.exception.CollectionBusinessException;
import br.com.leveEtraz.service.IMapaRotaFacade;
import br.com.leveEtraz.service.IMapaService;
import br.com.leveEtraz.service.IRotaService;

/**
 * Facade para acesso a dados {@link Mapa} e {@link Rota}
 * @author frozendo
 */
@Transactional
@Service
public class MapaRotaFacade implements IMapaRotaFacade {
	
	/**
	 * Service de acesso a Rota
	 */
	@Autowired
	private IRotaService rotaService;
	
	/**
	 * Service de acesso a Mapa
	 */
	@Autowired
	private IMapaService mapaService;
	
	/**
	 * Atributo que armazena o destino fianl
	 */
	private String destinoFinal = "";
	
	/**
	 * Atributo que armazena as rotas com o percurso e a distancia
	 */
	Map<String, Float> rotasCalculadas = null;
		
	public void save(Mapa mapa) {
		validarMapa(mapa);
		mapaService.save(mapa);
		rotaService.save(mapa);
	}
	
	public void buscarMelhorRota(MapaDestinoDTO mapaDestinoDTO) {
		List<Rota> rotas = rotaService.recuperarRotasMapa(mapaDestinoDTO.getNomeMapa());
		
		//Armazena o ponto final da rota
		destinoFinal = mapaDestinoDTO.getDestino();
		
		//Inicia o Map de rotasCalculadas
		rotasCalculadas = new HashMap<String, Float>();
				
		calcularRotas(rotas, mapaDestinoDTO.getOrigem(), "", 0F);
		definirMelhorRota(mapaDestinoDTO);
	}
	
	private void definirMelhorRota(MapaDestinoDTO mapaDestinoDTO) {
		Float menorDistancia = 0F;
		String rotaFinal = "";
		
		if (rotasCalculadas.size() == 0) {
			throw new BusinessException("Nenhuma rota encontrada para os valores informados");
		}
		
		//busca a menor distancia dentro das rotas calculadas
		for (String percurso : rotasCalculadas.keySet()) {
			if (menorDistancia == 0 || menorDistancia > rotasCalculadas.get(percurso)) {
				menorDistancia = rotasCalculadas.get(percurso);
				rotaFinal = percurso;
			}
		}
		
		//armazena as informações de menor rota no dto
		mapaDestinoDTO.setCusto(mapaDestinoDTO.getValorCombustivel() * menorDistancia);
		mapaDestinoDTO.setMelhorRota(rotaFinal);
	}
	
	/**
	 * Metodo recursivo que calcula os percursos e a distancia de cada 
	 * rota entre os pontos solicitados pelo usuário
	 * @param rotas rotas carregadas
	 * @param origemAtual ponto de origem considerado para o calculo atual
	 * @param percurso percorrido até agora
	 * @param distanciaPercorrida distancia acumulada no percurso
	 */
	private void calcularRotas(List<Rota> rotas, String origemAtual, String percurso, Float distanciaPercorrida) {
		Map<String, Float> mapearRotas = new HashMap<String, Float>();
		Rota rotaDestino = null;
		Float distanciaAtual = distanciaPercorrida;
		
		//caminha por todas as rotas encontradas
		for (Rota rota : rotas) {
			//se a origem da rota for igual a origemAtual verifica o destino
			if (rota.getOrigem().equals(origemAtual)) {
				
				//se o destino for o mesmo solicitado pelo usuário armazena a rota
				if (rota.getDestino().equals(destinoFinal)) {
					rotaDestino = rota;
					break;
				} else {
					mapearRotas.put(rota.getDestino(), rota.getDistancia());
				}
			} 
		}
		
		//se a rota de destino for encontrada, calcula o percurso e a distancia e armazena no map
		//se a rota não for encontrada, passa o proximo ponto de origem para o metodo calcularRotas
		if (rotaDestino == null) {	
			percurso += origemAtual + " ";		
			for (String novaOrigem : mapearRotas.keySet()) {
				Float novaDistancia = distanciaAtual + mapearRotas.get(novaOrigem);
				calcularRotas(rotas, novaOrigem, percurso, novaDistancia);
			}			
		} else {
			percurso += origemAtual + " " + rotaDestino.getDestino();
			rotasCalculadas.put(percurso, distanciaPercorrida + rotaDestino.getDistancia());
		}
	}
	
	/**
	 * Validar as regras antes de persistir os mapas
	 * @param mapa
	 */
	private void validarMapa(Mapa mapa) {
		validarCamposObrigatoriosMapa(mapa);
		validarRegrasMapa(mapa);
		
		for (Rota rota : mapa.getRotas()) {
			validarCamposObrigatoriosRota(rota);
			validarRegrasRota(rota, mapa.getId());
		}
	}
	
	/**
	 * Valida os campos obrigatorios do objeto mapa
	 * @param mapa
	 */
	private void validarCamposObrigatoriosMapa(Mapa mapa) {		
		if (mapa.getNome() == null || mapa.getNome().equals("")) {
			throw new BusinessException("O campo nome é obrigatório");
		}
	}
	
	/**
	 * Valida regras do objeto mapa
	 * @param mapa
	 */
	private void validarRegrasMapa(Mapa mapa) {
		if (mapaService.verificarNomeExiste(mapa)) {
			throw new BusinessException("Mapa já cadastrado. Por favor verifique!");
		}
		
		if (mapa.getNome().length() > 30) {
			throw new BusinessException("Por favor, informe no máximo trinta caracteres para o Nome do mapa. Quantida atual " + mapa.getNome().length());			
		}
	}
	
	/**
	 * Valida os campos obrigatorios do objeto rota
	 * @param mapa
	 */
	private void validarCamposObrigatoriosRota(Rota rota) {
		List<BusinessException> exceptions = new ArrayList<BusinessException>();
		if (rota.getOrigem() == null || rota.getOrigem().equals("")) {
			exceptions.add(new BusinessException("Rotas com a propriedade 'origem' vazia. Propriedade Origem é obrigatória"));
		}		
		if (rota.getDestino() == null || rota.getDestino().equals("")) {
			exceptions.add(new BusinessException("Rotas com a propriedade 'destino' vazia. Propriedade Destino é obrigatória"));
		}		
		if (rota.getDistancia() == null || rota.getDistancia().equals("")) {
			exceptions.add(new BusinessException("Rotas com a propriedade 'distancia' vazia. Propriedade Distancia é obrigatória"));
		}
		if (exceptions.size() > 0) {
			throw new CollectionBusinessException(exceptions);
		}
	}
	
	/**
	 * Valida regras do objeto rota
	 * @param mapa
	 */
	private void validarRegrasRota(Rota rota, Long mapaId) {
		if (mapaId != null && rotaService.rotaExiste(rota, mapaId)) {
			throw new BusinessException("Mapa já cadastrado. Por favor verifique!");
		}
		
		if (rota.getOrigem().length() > 20) {
			throw new BusinessException("Por favor, informe no máximo trinta caracteres para o Ponto de Origem. Quantida atual " + rota.getOrigem().length());	
		}
		
		if (rota.getDestino().length() > 20) {
			throw new BusinessException("Por favor, informe no máximo trinta caracteres para o Ponto de Destino. Quantida atual " + rota.getDestino().length());			
		}
	}

}
