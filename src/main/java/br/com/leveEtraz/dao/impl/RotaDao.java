package br.com.leveEtraz.dao.impl;

import org.springframework.stereotype.Repository;

import br.com.leveEtraz.dao.IRotaDao;
import br.com.leveEtraz.entity.Rota;

@Repository
public class RotaDao extends BaseDao implements IRotaDao {

	public void save(Rota rota) {
		super.persist(rota);
	}

}
