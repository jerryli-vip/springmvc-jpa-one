package com.pactera.web.service;

import java.util.List;

import com.pactera.web.common.Pagination;
import com.pactera.web.exception.ServiceException;
import com.pactera.web.model.Department;

public interface DepartmentService extends BaseService<Department> {

	public List<Department> findAll() throws ServiceException;

	public List<Department> findAll(Pagination pagination) throws ServiceException;
}
