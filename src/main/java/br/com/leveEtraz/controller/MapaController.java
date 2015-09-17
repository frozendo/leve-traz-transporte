package br.com.leveEtraz.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.leveEtraz.dto.RecebeMapaDestinoDTO;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.exception.BusinessException;
import br.com.leveEtraz.exception.CollectionBusinessException;
import br.com.leveEtraz.service.IMapaRotaFacade;
import br.com.leveEtraz.service.IMapaService;

@RestController
public class MapaController {

	@Autowired
	private IMapaRotaFacade mapaRotaFacade;

	@RequestMapping(value = "/novoMapa", method = RequestMethod.POST)
	public String novoMapa(@RequestBody Mapa mapa) {
		StringBuilder retorno = new StringBuilder();
		try {
			mapaRotaFacade.save(mapa);
			retorno.append("Mapa: " + mapa.getNome() + " cadastrado com sucesso"); 
		} catch (BusinessException e) {
			retorno.append("Erro no cadastro do mapa: " + e.getMessage());
		} catch (CollectionBusinessException e) {
			retorno.append("Erro(s) no cadastro do mapa: ");
			retorno.append(System.getProperty("line.separator"));
			for (BusinessException businessException : e.getBussinessExceptionList()) {
				retorno.append(businessException.getMessage());
				retorno.append(System.getProperty("line.separator"));
			}
		}
		return retorno.toString();
	}

	@RequestMapping(value = "/melhorRotaMapa", method = RequestMethod.POST)
	public String melhorRotaMapa(@RequestBody RecebeMapaDestinoDTO mapaDestinoDTO) {
		
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);
		
		try {
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("rota", mapaDestinoDTO.getMelhorRota());
			jsonObject.put("custo", mapaDestinoDTO.getCusto());
			
			return jsonObject.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro";
		}
	}

}
