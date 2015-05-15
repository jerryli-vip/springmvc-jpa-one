package com.pactera.web.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pactera.web.dao.BaseDAO;
import com.pactera.web.exception.DAOException;

@Component("baseDAO")
public class BaseDAOImpl<T> implements BaseDAO<T> {

	Logger log = Logger.getLogger(BaseDAOImpl.class);

	@PersistenceContext(unitName = "persistenceUnit")
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<T> listAll(Class<T> c) throws DAOException {
		List<T> list = null;
		try {
			Query query = entityManager.createQuery(" from " + c.getName());
			list = query.getResultList();
		} catch (Exception e) {
			log.error("failed to listAll : " + e);
			throw new DAOException(e);
		}
		return list;
	}

	public T findById(Class<T> c, int id) throws DAOException {
		T t = null;
		try {
			t = entityManager.find(c, id);
		} catch (Exception e) {
			log.error("failed to findById Class:id [" + c.getName() + ":" + id + "] : " + e);
			throw new DAOException(e);
		}
		return t;
	}

	public boolean save(Object object) throws DAOException {
		boolean b = false;
		try {
			entityManager.persist(object);
			b = true;
		} catch (Exception e) {
			log.error("failed to save : " + e);
			throw new DAOException(e);
		}
		return b;
	}

	public boolean update(Object object) throws DAOException {
		boolean b = false;
		try {
			entityManager.merge(object);
			b = true;
		} catch (Exception e) {
			log.error("failed to update : " + e);
			throw new DAOException(e);
		}
		return b;
	}

	public boolean delete(Class<T> c, int id) throws DAOException {
		boolean b = false;
		try {
			entityManager.remove(entityManager.find(c, id));
			b = true;
		} catch (Exception e) {
			log.error("failed to delete : " + e);
			throw new DAOException(e);
		}
		return b;
	}

	public boolean delete(Object object) throws DAOException {
		boolean b = false;
		try {
			entityManager.remove(object);
			b = true;
		} catch (Exception e) {
			log.error("failed to delete : " + e);
			throw new DAOException(e);
		}
		return b;
	}

}
