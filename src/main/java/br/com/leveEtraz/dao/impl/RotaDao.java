package br.com.leveEtraz.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import br.com.leveEtraz.dao.IRotaDao;
import br.com.leveEtraz.entity.Mapa;
import br.com.leveEtraz.entity.Rota;

@Repository
public class RotaDao extends BaseDao implements IRotaDao {

	public void save(Rota rota) {
		super.persist(rota);
	}

	@Override
	public boolean rotaExiste(Rota rota, Long mapaId) {
		Criteria criteria = super.getSession().createCriteria(Rota.class);
		criteria.add(Restrictions.eq("origem", rota.getOrigem()));
		criteria.add(Restrictions.eq("destino", rota.getDestino()));
		criteria.add(Restrictions.eq("mapa.id", mapaId));
		return criteria.uniqueResult() != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rota> buscarRotas(String nomeMapa) {
		Criteria criteria = super.getSession().createCriteria(Rota.class);
		
		criteria.createAlias("mapa", "mapa", JoinType.INNER_JOIN);
        
		criteria.add(Restrictions.eq("mapa.nome", nomeMapa));
		
		criteria.addOrder(Order.asc("origem"));
		
		return (List<Rota>) criteria.list();
	}

}
