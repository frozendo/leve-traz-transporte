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
	}
	
	/**
	 * Teste Metodo Save
	 */
	@Test(expected = BusinessException.class)
	public void testSaveNomeMapaInvalido() {
		Mapa mapa = criarEntidadeMapa();	
		mapa.setNome("Mapa que tem mais de 30 caracteres");
		mapaRotaFacade.save(mapa);
	}
	
	@Test
	public void testBuscarMelhorRota() {
		String rotaEspera = "A C D";
		Float custoEsperado = 5.775F;
		
		Mapa mapa = criarEntidadeMapa();
		List<Rota> rotas = new ArrayList<Rota>(criarListaRota(mapa));
		MapaDestinoDTO dto = criarMapaDestino();
		
		Mockito.when(rotaService.recuperarRotasMapa(mapa.getNome())).thenReturn(rotas);
		mapaRotaFacade.buscarMelhorRota(dto);
		
		Assert.assertEquals(dto.getMelhorRota(), rotaEspera);
		Assert.assertEquals(dto.getCusto(), custoEsperado);
	}
	
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
	 * Teste Metodo Save
	 */
	@Test(expected = BusinessException.class)
	public void testSaveMapaDuplicado() {
		Mapa mapa = criarEntidadeMapa();	
		Mockito.when(mapaService.verificarNomeExiste(mapa)).thenReturn(true);
		mapaRotaFacade.save(mapa);
	}
	
	@Test(expected = BusinessException.class)
	public void testSaveMapaNomeEmBranco() {
		Mapa mapa = criarEntidadeMapa();
		mapa.setNome("");
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = BusinessException.class)
	public void testSaveMapaNomeNulo() {
		Mapa mapa = criarEntidadeMapa();
		mapa.setNome(null);
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDuplicada() {
		Mapa mapa = criarEntidadeMapa();
		mapa.setId(1L);
		
		Rota rota = criarRota("A", "E", 5F, mapa);
		mapa.getRotas().add(rota);
		Mockito.when(rotaService.rotaExiste(rota, 1L)).thenReturn(true);
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaOrigemNula() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota(null, "E", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaOrigemEmBranco() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("", "E", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaOrigemInvalida() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("Origem com mais de 20", "E", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDestinoEmBranco() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("D", "", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDestinoNulo() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("D", null, 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDestinoInvalido() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("A", "Destino com mais de 20", 3F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaSemDistancia() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("D", "E", 0F, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);		
	}
	
	@Test(expected = CollectionBusinessException.class)
	public void testSaveRotaDistanciaNula() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("D", "E", null, mapa);
		mapa.getRotas().add(rota);
		mapaRotaFacade.save(mapa);		
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
