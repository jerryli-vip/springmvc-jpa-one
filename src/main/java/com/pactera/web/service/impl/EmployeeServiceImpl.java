package com.pactera.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.web.common.Pagination;
import com.pactera.web.dao.EmployeeDAO;
import com.pactera.web.exception.ServiceException;
import com.pactera.web.model.Employee;
import com.pactera.web.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	Logger log = Logger.getLogger(EmployeeServiceImpl.class);

	@Resource
	EmployeeDAO dao;

	@Value("#{configProperties['page.size']}")
	private String pageSize;

	@Transactional(rollbackFor = ServiceException.class)
	public void save(Employee emp) throws ServiceException {
		final String METHOD_NAME = "save";

		log.debug(METHOD_NAME + " begin");

		try {
			dao.save(emp);
		} catch (Exception e) {
			log.error("Error when save", e);
			throw new ServiceException(e.getMessage());
		}

		log.debug(METHOD_NAME + " end");
	}

	@Transactional(rollbackFor = ServiceException.class)
	public void update(Employee emp) throws ServiceException {
		final String METHOD_NAME = "update";

		log.debug(METHOD_NAME + " begin");

		try {
			dao.save(emp);
		} catch (Exception e) {
			log.error("Error when update", e);
			throw new ServiceException(e.getMessage());
		}

		log.debug(METHOD_NAME + " end");
	}

	@Transactional(rollbackFor = ServiceException.class)
	public void delete(Integer empno) throws ServiceException {
		final String METHOD_NAME = "delete";

		log.debug(METHOD_NAME + " begin");

		try {
			dao.delete(empno);
		} catch (Exception e) {
			log.error("Error when delete by id : " + empno, e);
			throw new ServiceException(e.getMessage());
		}

		log.debug(METHOD_NAME + " end");
	}

	public Employee findById(Integer empno) throws ServiceException {
		final String METHOD_NAME = "findById";
		log.debug(METHOD_NAME + " begin");
		log.debug(METHOD_NAME + " empno : " + empno);

		Employee emp = null;
		try {
			emp = dao.getOne(empno);
		} catch (Exception e) {
			log.error("Error when find by id : " + empno, e);
			throw new ServiceException(e.getMessage());
		}

		log.debug(METHOD_NAME + " end");
		return emp;
	}

	public List<Employee> findAll(Pagination pagination) throws ServiceException {
		final String METHOD_NAME = "findAll";
		log.debug(METHOD_NAME + " begin");

		List<Employee> dataList = null;
		try {
			// 1. no sort
			// list = dao.findAll();

			// 2. sort
			Sort sort = new Sort("empno");
			// list = dao.findAll(sort);

			// 3. pagination
			Integer pagesize = 0;
			try {
				pagesize = Integer.valueOf(pageSize);
				pagination.setPageSize(pagesize);
			} catch (Exception e) {
				log.info("Error when set page size", e);
			}
			pagination.setRecordCount(Integer.valueOf(String.valueOf(dao.count())));
			final int page = pagination.getPageNo() > 0 ? pagination.getPageNo() - 1 : 0;
			Pageable pageable = new PageRequest(page, pagination.getPageSize(), sort);
			Page<Employee> pageRepo = dao.findAll(pageable);

			dataList = pageRepo.getContent();

			log.debug(METHOD_NAME + " dataList.size : " + (CollectionUtils.isEmpty(dataList) ? 0 : dataList.size()));
		} catch (Exception e) {
			log.error("Error when find all", e);
			throw new ServiceException(e.getMessage());
		}

		log.debug(METHOD_NAME + " end");
		return dataList;
	}

}
