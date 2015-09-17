package br.com.leveEtraz.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import br.com.leveEtraz.dao.IMapaDao;
import br.com.leveEtraz.entity.Mapa;

@Repository
public class MapaDao extends BaseDao implements IMapaDao {

	public void save(Mapa mapa) {
		super.persist(mapa);
	}
	
	public boolean verificarNomeExiste(Mapa mapa) {
		Criteria criteria = super.getSession().createCriteria(Mapa.class);
		criteria.add(Restrictions.eq("nome", mapa.getNome()));
		return criteria.uniqueResult() != null;
	}

	@Override
	public Mapa buscarRotas(String nomeMapa) {
		Criteria criteria = super.getSession().createCriteria(Mapa.class);
        
        criteria.createAlias("rotas", "rota", JoinType.INNER_JOIN);
        
		criteria.add(Restrictions.eq("nome", nomeMapa));
		criteria.add(Restrictions.or(Restrictions.eq("rota.origem", 'A'), Restrictions.eq("rota.destino", 'D')));
		
		return (Mapa) criteria.uniqueResult();
	}

}
