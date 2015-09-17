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

/**
 * Classe de acesso a dados de {@link Rota}
 * @author frozendo
 */
@Repository
public class RotaDao extends BaseDao implements IRotaDao {

	@Override
	public void save(Rota rota) {
		super.persist(rota);
	}

	@Override
	public boolean rotaExiste(Rota rota, Long mapaId) {
		Criteria criteria = super.getCriteria(Rota.class);
		
		//se existir uma rota com a mesma origem e destino para o mapa, não permite continuar
		criteria.add(Restrictions.eq("origem", rota.getOrigem()));
		criteria.add(Restrictions.eq("destino", rota.getDestino()));
		criteria.add(Restrictions.eq("mapa.id", mapaId));
		
		//se o retorno for nulo, o nome não existe na base de dados
		return criteria.uniqueResult() != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rota> buscarRotas(String nomeMapa) {
		Criteria criteria = super.getCriteria(Rota.class);
		
		//cria o join com a tabela mapa
		criteria.createAlias("mapa", "mapa", JoinType.INNER_JOIN);
        
		criteria.add(Restrictions.eq("mapa.nome", nomeMapa));		
		criteria.addOrder(Order.asc("origem"));
		
		//retorna as rotas para o mapa passado no parametro
		return (List<Rota>) criteria.list();
	}

}
