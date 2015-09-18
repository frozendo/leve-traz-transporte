package br.com.levaEtraz;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.leveEtraz.controller.MapaController;
import br.com.leveEtraz.dao.IMapaDao;
import br.com.leveEtraz.dao.impl.MapaDao;
import br.com.leveEtraz.dto.MapaDestinoDTO;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;
import br.com.leveEtraz.exception.BusinessException;
import br.com.leveEtraz.exception.CollectionBusinessException;
import br.com.leveEtraz.property.MensagensProperty;
import br.com.leveEtraz.service.IMapaRotaFacade;
import br.com.leveEtraz.service.IMapaService;
import br.com.leveEtraz.service.IRotaService;
import br.com.leveEtraz.service.impl.MapaRotaFacade;
import br.com.leveEtraz.service.impl.MapaService;
import br.com.leveEtraz.service.impl.RotaService;

@RunWith(MockitoJUnitRunner.class)
public class MapaControllerTest {
	
	@Mock
	private IMapaRotaFacade mapaRotaFacade = new MapaRotaFacade();
		
	@InjectMocks
	private MapaController mapaController = new MapaController();

	@Test
	public void testNovoMapa() {
		Mapa mapa = criarEntidadeMapa();
		String retorno = mapaController.novoMapa(mapa);
		Mockito.verify(mapaRotaFacade, Mockito.times(1)).save(mapa);
		Assert.assertEquals(retorno, MensagensProperty.MSG_CADASTRO_SUCESSO.getMensagem());
	}

	@Test
	public void testNovoMapaBusinessException() {
		Mapa mapa = criarEntidadeMapa();
		String msgRetorno = MensagensProperty.MSG_ERRO_PROCESO_01.getMensagem() + MensagensProperty.MSG_ERRO_01.getMensagem();
		
		Mockito.doThrow(new BusinessException(MensagensProperty.MSG_ERRO_01.getMensagem())).when(mapaRotaFacade).save(mapa);
		String retorno = mapaController.novoMapa(mapa);
		Mockito.verify(mapaRotaFacade, Mockito.times(1)).save(mapa);
		Assert.assertEquals(msgRetorno, retorno);
	}

	@Test
	public void testNovoMapaCollectionException() {
		Mapa mapa = criarEntidadeMapa();
		
		String msgRetorno = MensagensProperty.MSG_ERRO_PROCESO_02.getMensagem();
		msgRetorno += System.getProperty("line.separator");
		msgRetorno += MensagensProperty.MSG_ERRO_06.getMensagem();
		msgRetorno += System.getProperty("line.separator");
		
		Mockito.doThrow(new CollectionBusinessException(new BusinessException(MensagensProperty.MSG_ERRO_06.getMensagem())))
			.when(mapaRotaFacade).save(mapa);
		String retorno = mapaController.novoMapa(mapa);
		Mockito.verify(mapaRotaFacade, Mockito.times(1)).save(mapa);
		Assert.assertEquals(msgRetorno, retorno);
	}

	@Test
	public void testBuscarMelhorRota() {
		MapaDestinoDTO dto = criarMapaDestino();
		String jsonEsperado = "{\"custo\":5.775,\"rota\":\"A C D\"}";
		String retorno = mapaController.melhorRotaMapa(dto);
		Mockito.verify(mapaRotaFacade, Mockito.times(1)).buscarMelhorRota(dto);
		Assert.assertEquals(jsonEsperado, retorno);
	}

	@Test
	public void testBuscarMelhorRotaBusinessException() {
		MapaDestinoDTO dto = criarMapaDestino();
		String msgRetorno = MensagensProperty.MSG_ERRO_10.getMensagem();
		
		Mockito.doThrow(new BusinessException(MensagensProperty.MSG_ERRO_10.getMensagem())).when(mapaRotaFacade).buscarMelhorRota(dto);
		String retorno = mapaController.melhorRotaMapa(dto);
		Mockito.verify(mapaRotaFacade, Mockito.times(1)).buscarMelhorRota(dto);
		Assert.assertEquals(msgRetorno, retorno);
	}

	@Test
	public void testBuscarMelhorRotaCollectionException() {
		MapaDestinoDTO dto = criarMapaDestino();
		
		String msgRetorno = MensagensProperty.MSG_ERRO_PROCESO_04.getMensagem();
		msgRetorno += System.getProperty("line.separator");
		msgRetorno += MensagensProperty.MSG_ERRO_10.getMensagem();
		msgRetorno += System.getProperty("line.separator");
		
		Mockito.doThrow(new CollectionBusinessException(new BusinessException(MensagensProperty.MSG_ERRO_10.getMensagem())))
			.when(mapaRotaFacade).buscarMelhorRota(dto);
		
		String retorno = mapaController.melhorRotaMapa(dto);
		Mockito.verify(mapaRotaFacade, Mockito.times(1)).buscarMelhorRota(dto);
		Assert.assertEquals(msgRetorno, retorno);
	}
	
	private Mapa criarEntidadeMapa() {
		Mapa mapa = new Mapa();		
		mapa.setNome("Nome Teste");		
		mapa.setRotas(criarListaRota(mapa));
		
		return mapa;
	}
	
	private Set<Rota> criarListaRota(Mapa mapa) {
		Set<Rota> rotas = new HashSet<Rota>();
		
		rotas.add(criarRota("A", "B", 10F, mapa));
		rotas.add(criarRota("A", "C", 8F, mapa));
		rotas.add(criarRota("B", "D", 5F, mapa));
		rotas.add(criarRota("C", "D", 6F, mapa));
		
		return rotas;		
	}
	
	private Rota criarRota(String origem, String destino, Float distancia, Mapa mapa) {
		Rota rota = new Rota();
		
		rota.setDestino(destino);
		rota.setOrigem(origem);
		rota.setDistancia(distancia);
		rota.setMapa(mapa);
		
		return rota;
	}
	
	private MapaDestinoDTO criarMapaDestino() {
		MapaDestinoDTO dto = new MapaDestinoDTO();
		dto.setNomeMapa("Nome Teste");
		dto.setOrigem("A");
		dto.setDestino("D");
		dto.setAutonomiaVeiculo(8F);
		dto.setValorCombustivel(3.3F);
		dto.setCusto(5.775F);
		dto.setMelhorRota("A C D");
		return dto;
	}

}
