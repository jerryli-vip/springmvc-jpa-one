package com.pactera.web.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pactera.web.common.Pagination;
import com.pactera.web.dao.EmployeeDAO;
import com.pactera.web.exception.DAOException;
import com.pactera.web.exception.ServiceException;
import com.pactera.web.model.Employee;
import com.pactera.web.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {

	Logger log = Logger.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeDAO empDAO;

	@Override
	public List<Employee> findAll() throws ServiceException {
		final String METHOD_NAME = "findAll";
		log.debug(METHOD_NAME + " begin");
		List<Employee> list = null;
		try {
			list = empDAO.findAll();
		} catch (DAOException e) {
			log.error("Error when findAll : " + e.getMessage());
			throw new ServiceException(e);
		}
		log.debug("list.size : " + ((CollectionUtils.isEmpty(list)) ? 0 : list.size()));

		log.debug(METHOD_NAME + " end");
		return list;
	}

	@Override
	public List<Employee> findAll(Pagination pagination) throws ServiceException {
		final String METHOD_NAME = "findAll";
		log.debug(METHOD_NAME + " begin");
		List<Employee> list = null;
		try {
			list = empDAO.findAll(pagination);
		} catch (DAOException e) {
			log.error("Error when findAll : " + e.getMessage());
			throw new ServiceException(e);
		}
		log.debug("list.size : " + ((CollectionUtils.isEmpty(list)) ? 0 : list.size()));

		log.debug(METHOD_NAME + " end");
		return list;
	}

}
