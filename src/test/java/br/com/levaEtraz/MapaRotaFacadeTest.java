package br.com.levaEtraz;

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

import br.com.leveEtraz.dto.MapaDestinoDTO;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;
import br.com.leveEtraz.exception.BusinessException;
import br.com.leveEtraz.exception.CollectionBusinessException;
import br.com.leveEtraz.service.IMapaRotaFacade;
import br.com.leveEtraz.service.IMapaService;
import br.com.leveEtraz.service.IRotaService;
import br.com.leveEtraz.service.impl.MapaRotaFacade;
import br.com.leveEtraz.service.impl.MapaService;
import br.com.leveEtraz.service.impl.RotaService;

@RunWith(MockitoJUnitRunner.class)
public class MapaRotaFacadeTest {
	
	@Mock
	private IMapaService mapaService = new MapaService();
	
	@Mock
	private IRotaService rotaService = new RotaService();
	
	@InjectMocks
	private IMapaRotaFacade mapaRotaFacade = new MapaRotaFacade();
	
	/**
	 * Teste Metodo Save
	 */
	@Test
	public void testSave() {
		Mapa mapa = criarEntidadeMapa();		
		mapaRotaFacade.save(mapa);
		Mockito.verify(mapaService, Mockito.times(1)).save(mapa);
		Mockito.verify(rotaService, Mockito.times(1)).save(mapa);
	}
	
	/**
	 * Teste Metodo Save para um mapa com mais de 30 caracteres
	 */
	@Test(expected = BusinessException.class)
	public void testSaveNomeMapaInvalido() {
		Mapa mapa = criarEntidadeMapa();	
		mapa.setNome("Mapa que tem mais de 30 caracteres");
		mapaRotaFacade.save(mapa);
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(mapaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota
	 */
	@Test
	public void testBuscarMelhorRota() {
		String rotaEspera = "A, C, D";
		Float custoEsperado = 5.775F;
		
		Mapa mapa = criarEntidadeMapa();
		List<Rota> rotas = new ArrayList<Rota>(criarListaRota(mapa));
		MapaDestinoDTO dto = criarMapaDestino();
		
		Mockito.when(rotaService.recuperarRotasMapa(mapa.getNome())).thenReturn(rotas);
		mapaRotaFacade.buscarMelhorRota(dto);
		
		Mockito.verify(rotaService, Mockito.times(1)).recuperarRotasMapa(mapa.getNome());
		
		Assert.assertEquals(dto.getMelhorRota(), rotaEspera);
		Assert.assertEquals(dto.getCusto(), custoEsperado);
	}
	
	/**
	 * Teste metodo busca a melhor rota para uma rota inesxistente
	 */
	@Test(expected = BusinessException.class)
	public void testRotaInesxistente() {		
		Mapa mapa = criarEntidadeMapa();
		List<Rota> rotas = new ArrayList<Rota>(criarListaRota(mapa));
		MapaDestinoDTO dto = criarMapaDestino();
		dto.setOrigem("Z");
		dto.setOrigem("Y");
		
		Mockito.when(rotaService.recuperarRotasMapa(mapa.getNome())).thenReturn(rotas);
		mapaRotaFacade.buscarMelhorRota(dto);		
	}
	
	/**
	 * Teste Metodo save para um mapa que já existe
	 */
	@Test(expected = BusinessException.class)
	public void testSaveMapaDuplicado() {
		Mapa mapa = criarEntidadeMapa();	
		Mockito.when(mapaService.verificarNomeExiste(mapa)).thenReturn(true);
		mapaRotaFacade.save(mapa);
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste metodo save com o nome do mapa em branco
	 */
	@Test(expected = BusinessException.class)
	public void testSaveMapaNomeEmBranco() {
		Mapa mapa = criarEntidadeMapa();
		mapa.setNome("");
		mapaRotaFacade.save(mapa);		
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste metodo save com o nome do mapa nulo
	 */
	@Test(expected = BusinessException.class)
	public void testSaveMapaNomeNulo() {
		Mapa mapa = criarEntidadeMapa();
		mapa.setNome(null);
		mapaRotaFacade.save(mapa);	
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);	
	}
	
	/**
	 * Teste metodo save para rotas duplicadas
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDuplicada() {
		Mapa mapa = criarEntidadeMapa();
		mapa.setId(1L);
		
		Rota rota = criarRota("A", "E", 5F, mapa);
		mapa.getRotas().add(rota);
		Mockito.when(rotaService.rotaExiste(rota, 1L)).thenReturn(true);
		mapaRotaFacade.save(mapa);	
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);	
	}
	
	/**
	 * Teste metodo save para origem nula
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaOrigemNula() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota(null, "E", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);	
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);	
	}
	
	/**
	 * Teste metodo save para origem em branco
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaOrigemEmBranco() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("", "E", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);		
	}
	
	/**
	 * Teste metodo save com rotas que tem origem com mais de 30 caracteres
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaOrigemInvalida() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("Origem que tem mais de 30 caracteres", "E", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);	
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);	
	}
	
	/**
	 * Teste metodo save com rotas que tem o destino em branco
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDestinoEmBranco() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("D", "", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);	
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);		
	}
	
	/**
	 * Teste metodo save com rotas que tem o destino nulo
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDestinoNulo() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("D", null, 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);	
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);		
	}
	
	/**
	 * Teste metodo save com rotas que tem o destino invalido
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDestinoInvalido() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("A", "Destino que tem mais de 30 caracteres", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);	
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);		
	}
	
	/**
	 * Teste metodo save com rotas que tem a distancia em branco
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaSemDistancia() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("D", "E", 0F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);	
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);		
	}
	
	/**
	 * Teste metodo save com rotas que tem a distancia nula
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDistanciaNula() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("D", "E", null, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);	
		Mockito.verifyZeroInteractions(mapaService);
		Mockito.verifyZeroInteractions(rotaService);		
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com a origem em branco
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaOrigemBranco() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setOrigem("");
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com o destino em branco
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaDestinoEmBranco() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setDestino("");
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com o valor do combustivel igual a zero
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaValorCombustivelZerado() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setValorCombustivel(0F);
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com a autonomia do veiculo igual a 0
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaAutonomiaZerado() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setAutonomiaVeiculo(0F);
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com o nome do mapa em branco
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaNomeMapaEmBranco() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setNomeMapa("");
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com a origem nulo
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaOrigemNula() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setOrigem(null);
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com o destino nulo
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaDestinoNulo() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setDestino(null);
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com o valor do combustivel nulo
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaValorCombustivelNulo() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setValorCombustivel(null);
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com a autonomia do veiculo nula
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaAutonomiaNula() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setAutonomiaVeiculo(null);
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
	}
	
	/**
	 * Teste do metodo que busca a melhor rota com o nome do mapa nulo
	 */
	@Test(expected = CollectionBusinessException.class)
	public void testBuscarMelhorRotaNomeMapaNulo() {
		MapaDestinoDTO mapaDestinoDTO = criarMapaDestino();
		mapaDestinoDTO.setNomeMapa(null);
		mapaRotaFacade.buscarMelhorRota(mapaDestinoDTO);		
		Mockito.verifyZeroInteractions(rotaService);
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
		return dto;
	}

}
