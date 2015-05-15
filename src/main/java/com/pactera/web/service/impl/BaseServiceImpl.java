package com.pactera.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.web.dao.BaseDAO;
import com.pactera.web.exception.DAOException;
import com.pactera.web.exception.ServiceException;
import com.pactera.web.service.BaseService;

@Service
public class BaseServiceImpl<T> implements BaseService<T> {

	Logger log = Logger.getLogger(BaseServiceImpl.class);

	@Resource(name = "baseDAO")
	private BaseDAO<T> baseDAO;

	public List<T> listAll(Class<T> c) throws ServiceException {
		List<T> list = null;
		try {
			list = baseDAO.listAll(c);
		} catch (DAOException e) {
			log.error("failed to listAll : " + e);
			throw new ServiceException(e);
		}

		return list;
	}

	public T findById(Class<T> c, int id) throws ServiceException {
		T t = null;
		try {
			t = baseDAO.findById(c, id);
		} catch (DAOException e) {
			log.error("failed to findById : " + e);
			throw new ServiceException(e);
		}

		return t;
	}

	@Transactional(rollbackFor = ServiceException.class)
	public boolean save(Object object) throws ServiceException {
		boolean b = false;
		try {
			baseDAO.save(object);
			b = true;
		} catch (DAOException e) {
			log.error("failed to save : " + e);
			throw new ServiceException(e);
		}

		return b;
	}

	@Transactional(rollbackFor = ServiceException.class)
	public boolean update(Object object) throws ServiceException {
		boolean b = false;
		try {
			baseDAO.update(object);
			b = true;
		} catch (DAOException e) {
			log.error("failed to update : " + e);
			throw new ServiceException(e);
		}

		return b;
	}

	@Transactional(rollbackFor = ServiceException.class)
	public boolean delete(Class<T> c, int id) throws ServiceException {
		boolean b = false;
		try {
			baseDAO.delete(c, id);
			b = true;
		} catch (DAOException e) {
			log.error("failed to delete : " + e);
			throw new ServiceException(e);
		}

		return b;
	}

	@Transactional(rollbackFor = ServiceException.class)
	public boolean delete(Object object) throws ServiceException {
		boolean b = false;
		try {
			baseDAO.delete(object);
			b = true;
		} catch (DAOException e) {
			log.error("failed to delete : " + e);
			throw new ServiceException(e);
		}

		return b;
	}
}
