package br.com.leveEtraz.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.leveEtraz.dao.IMapaDao;
import br.com.leveEtraz.entity.Mapa;

/**
 * Classe de acesso a dados de {@link Mapa}
 * @author frozendo
 */
@Repository
public class MapaDao extends BaseDao implements IMapaDao {

	@Override
	public void save(Mapa mapa) {
		super.persist(mapa);
	}
	
	@Override
	public boolean verificarNomeExiste(Mapa mapa) {
		Criteria criteria = super.getCriteria(Mapa.class);
		criteria.add(Restrictions.eq("nome", mapa.getNome()));
		
		//se o retorno for nulo, o nome não existe na base de dados
		return criteria.uniqueResult() != null;
	}
}
