package com.pactera.web.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pactera.web.common.Pagination;
import com.pactera.web.dao.DepartmentDAO;
import com.pactera.web.exception.DAOException;
import com.pactera.web.exception.ServiceException;
import com.pactera.web.model.Department;
import com.pactera.web.service.DepartmentService;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

	Logger log = Logger.getLogger(DepartmentServiceImpl.class);

	@Autowired
	private DepartmentDAO deptDAO;
	
	@Value("${page.size}")
	private String pageSize;

	@Override
	public List<Department> findAll() throws ServiceException {
		final String METHOD_NAME = "findAll";
		log.debug(METHOD_NAME + " begin");
		List<Department> list = null;
		try {
			list = deptDAO.findAll();
		} catch (DAOException e) {
			log.error("Error when findAll : " + e.getMessage());
			throw new ServiceException(e);
		}
		log.debug("list.size : " + ((CollectionUtils.isEmpty(list)) ? 0 : list.size()));

		log.debug(METHOD_NAME + " end");
		return list;
	}

	@Override
	public List<Department> findAll(Pagination pagination) throws ServiceException {
		final String METHOD_NAME = "findAll";
		log.debug(METHOD_NAME + " begin");

		List<Department> list = null;
		try {
			Integer pagesize = 0;
			try {
				pagesize = Integer.valueOf(pageSize);
				pagination.setPageSize(pagesize);
			} catch (Exception e) {
				log.info("Error when set page size", e);
			}
			
			list = deptDAO.findAll(pagination);
		} catch (DAOException e) {
			log.error("Error when findAll : " + e.getMessage());
			throw new ServiceException(e);
		}
		log.debug("list.size : " + ((CollectionUtils.isEmpty(list)) ? 0 : list.size()));

		log.debug(METHOD_NAME + " end");
		return list;
	}

}
