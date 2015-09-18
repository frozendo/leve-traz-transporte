package br.com.levaEtraz;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;
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
	
	@Test
	public void testSave() {
		Mapa mapa = new Mapa();
		Set<Rota> rotas = new HashSet<Rota>();
		Rota rota = new Rota();
		
		mapa.setNome("Nome Teste");
		
		rota.setDestino("Franca");
		rota.setOrigem("Rio Preto");
		rota.setDistancia(10F);
		rota.setMapa(mapa);
		
		rotas.add(rota);
		mapa.setRotas(rotas);
		
		mapaRotaFacade.save(mapa);
	}

}
