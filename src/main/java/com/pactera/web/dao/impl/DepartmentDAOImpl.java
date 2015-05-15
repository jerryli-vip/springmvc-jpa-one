package com.pactera.web.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pactera.web.common.Pagination;
import com.pactera.web.dao.DepartmentDAO;
import com.pactera.web.exception.DAOException;
import com.pactera.web.model.Department;

@Component("DepartmentDAO")
public class DepartmentDAOImpl extends BaseDAOImpl<Department> implements DepartmentDAO {

	Logger log = Logger.getLogger(DepartmentDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findAll() throws DAOException {
		final String METHOD_NAME = "findAll";
		log.debug(METHOD_NAME + " begin");

		List<Department> list = null;
		try {
			StringBuilder sb = new StringBuilder(" from Department order by deptName");
			final String SQL = sb.toString();
			Query query = entityManager.createQuery(SQL);

			list = query.getResultList();
		} catch (Exception e) {
			log.error("Failed to findAll : " + e.getMessage());
			throw new DAOException(e);
		}

		log.debug(METHOD_NAME + " end");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findAll(Pagination pagination) throws DAOException {
		final String METHOD_NAME = "findAll";
		log.debug(METHOD_NAME + " begin");

		List<Department> list = null;
		try {
			StringBuilder sb = new StringBuilder(" from Department order by deptno");
			final String SQL = sb.toString();
			Query query = entityManager.createQuery(SQL);

			pagination.setRecordCount(this.findCount());

			query.setFirstResult(pagination.getStartPos());
			query.setMaxResults(pagination.getPageSize());

			list = query.getResultList();
		} catch (Exception e) {
			log.error("Failed to findAll : " + e.getMessage());
			throw new DAOException(e);
		}

		log.debug(METHOD_NAME + " end");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findCount() throws DAOException {
		final String METHOD_NAME = "findCount";
		log.debug(METHOD_NAME + " begin");

		Integer count = 0;
		try {
			final String SQL = "select count(1) from Department";
			log.debug("SQL : " + SQL);
			Query query = entityManager.createQuery(SQL);
			List<Long> list = query.getResultList();
			if (!CollectionUtils.isEmpty(list)) {
				Long value = list.get(0);
				count = value.intValue();
			}
		} catch (Exception e) {
			log.error("Failed to findCount : " + e.getMessage());
			throw new DAOException(e);
		}

		log.debug("count : " + count);

		log.debug(METHOD_NAME + " end");
		return count;
	}

}
