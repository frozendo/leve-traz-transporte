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

import br.com.leveEtraz.dao.IMapaDao;
import br.com.leveEtraz.dao.IRotaDao;
import br.com.leveEtraz.dao.impl.MapaDao;
import br.com.leveEtraz.dao.impl.RotaDao;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;
import br.com.leveEtraz.service.IMapaService;
import br.com.leveEtraz.service.IRotaService;
import br.com.leveEtraz.service.impl.MapaService;
import br.com.leveEtraz.service.impl.RotaService;

@RunWith(MockitoJUnitRunner.class)
public class RotaServiceTest {
	
	@Mock
	private IRotaDao rotaDao = new RotaDao();
	
	@InjectMocks
	private IRotaService rotaService = new RotaService();
	
	/**
	 * Teste Metodo Save
	 */
	@Test
	public void testSave() {
		Mapa mapa = criarEntidadeMapa();		
		rotaService.save(mapa);
	}
	
	/**
	 * Teste Metodo Save
	 */
	@Test
	public void testVerificarNomeMapa() {
		Mapa mapa = criarEntidadeMapa();
		Rota rota = criarRota("A", "D", 20F, mapa);
		Mockito.when(rotaDao.rotaExiste(rota, 1L)).thenReturn(true);
		boolean retorno = rotaService.rotaExiste(rota, 1L);
		
		Assert.assertEquals(retorno, true);
	}
	
	/**
	 * Teste Metodo Save
	 */
	@Test
	public void testRecuperarRotasMapa() {
		Mapa mapa = criarEntidadeMapa();
		List<Rota> rotas = new ArrayList<Rota>();
		Mockito.when(rotaDao.buscarRotas(mapa.getNome())).thenReturn(rotas);
		List<Rota> retornoList = rotaService.recuperarRotasMapa(mapa.getNome());
		
		Assert.assertEquals(retornoList, rotas);
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

}
