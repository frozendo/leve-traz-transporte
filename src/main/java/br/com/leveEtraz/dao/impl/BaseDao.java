package br.com.leveEtraz.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Repository;

/**
 * Classe base para transações com o banco
 * Todas as classes DAO são construídas a partir dessa
 * @author frozendo
 */
@Repository
public class BaseDao {
	
	@PersistenceContext
	private EntityManager entityManager;

    /**
     * Método para recuperar um elemento de uma classe
     * @param clazz
     * @param id
     * @return entidade
     */
    @SuppressWarnings("unchecked")
    protected <A> A get(Class<A> clazz, Serializable id) {
    	Session session = getSession();
    	Transaction transacao = null;
    	A entity = null;
    	
    	try {
			transacao = session.beginTransaction();
			entity = (A) session.get(clazz, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return entity;
    }

    /**
     * Método para realizar o persist ou o update da entidade  
     * @param entity
     * @return entidade persistida/atualizada
     */
    @SuppressWarnings("unchecked")
    protected <A> A merge(A entity) {
    	Session session = getSession();
    	Transaction transacao = null;
    	
    	try {
			transacao = session.beginTransaction();
			session.merge(entity);
			transacao.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return entity;
    }

    /**
     * Método para persistir uma entidade
     * @param entity
     */
    protected <A> void persist(A entity) {
    	Session session = getSession();
    	Transaction transacao = null;
    	
    	try {
			transacao = session.beginTransaction();
			session.save(entity);
			transacao.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Método para remover uma entidade
     * @param entity
     */
    protected <A> void remove(A entity) {
    	Session session = getSession();
    	Transaction transacao = null;
    	
    	try {
			transacao = session.beginTransaction();
			session.delete(entity);
			transacao.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    }
    
    /**
     * Metodo que retorna um novo objeto criteria para consultas nas classes DAO
     * @param clazz
     * @return novo obj criteria
     */
    protected Criteria getCriteria(Class<?> clazz) {
    	return getSession().createCriteria(clazz);
    }
	
    /**
     * Cria uma sessão com o banco
     * @return
     */
	private Session getSession() {
		return buildSessionFactory().openSession();		
	}
	
	/***
	 * Recupera as configurações para conectar no banco
	 * @return
	 */
	private SessionFactory buildSessionFactory() {
        try {

            Configuration configuration = new Configuration();
            configuration.configure();

            ServiceRegistry serviceregistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            SessionFactory sessionFactory = configuration
                    .buildSessionFactory(serviceregistry);

            return sessionFactory;

        }

        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

}
