package br.com.leveEtraz.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.leveEtraz.dto.MapaDestinoDTO;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.exception.BusinessException;
import br.com.leveEtraz.exception.CollectionBusinessException;
import br.com.leveEtraz.property.MensagensProperty;
import br.com.leveEtraz.service.IMapaRotaFacade;

/**
 * Classe que recebe e processa as requisicições
 * @author frozendo|
 */
@RestController
public class MapaController {

	/**
	 * Facade para MapaRotaFacade
	 */
	@Autowired
	private IMapaRotaFacade mapaRotaFacade;

	@RequestMapping(value = "/novoMapa", method = RequestMethod.POST)
	public String novoMapa(@RequestBody Mapa mapa) {
		StringBuilder retorno = new StringBuilder();
		
		try {			
			mapaRotaFacade.save(mapa);
			retorno.append(MensagensProperty.MSG_CADASTRO_SUCESSO.getMensagem()); 
		} catch (BusinessException e) {
			retorno.append(MensagensProperty.MSG_ERRO_PROCESO_01.getMensagem() + e.getMessage());
		} catch (CollectionBusinessException e) {
			retorno.append(MensagensProperty.MSG_ERRO_PROCESO_02.getMensagem());
			retorno.append(System.getProperty("line.separator"));
			for (BusinessException businessException : e.getBussinessExceptionList()) {
				retorno.append(businessException.getMessage());
				retorno.append(System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			retorno.append(MensagensProperty.MSG_ERRO_PROCESO_03.getMensagem());
		}
		
		return retorno.toString();
	}

	@RequestMapping(value = "/melhorRotaMapa", method = RequestMethod.POST)
	public String melhorRotaMapa(@RequestBody MapaDestinoDTO mapaDestinoDTO) {
		StringBuilder retorno = new StringBuilder();
		
		try {			
			mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);
			
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("rota", mapaDestinoDTO.getMelhorRota());
			jsonObject.put("custo", mapaDestinoDTO.getCusto());
			
			retorno.append(jsonObject.toString());
			
		} catch (BusinessException e) {
			retorno.append(e.getMessage());
		} catch (CollectionBusinessException e) {
			retorno.append(MensagensProperty.MSG_ERRO_PROCESO_04.getMensagem());
			retorno.append(System.getProperty("line.separator"));
			for (BusinessException businessException : e.getBussinessExceptionList()) {
				retorno.append(businessException.getMessage());
				retorno.append(System.getProperty("line.separator"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			retorno.append(MensagensProperty.MSG_ERRO_PROCESO_05.getMensagem());
		} catch (Exception e) {
			e.printStackTrace();
			retorno.append(MensagensProperty.MSG_ERRO_PROCESO_03.getMensagem());
		}
		
		return retorno.toString();
	}

}
