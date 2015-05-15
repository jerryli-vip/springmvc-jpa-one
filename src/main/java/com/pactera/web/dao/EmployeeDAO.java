package com.pactera.web.dao;

import java.util.List;

import com.pactera.web.common.Pagination;
import com.pactera.web.exception.DAOException;
import com.pactera.web.model.Employee;

public interface EmployeeDAO extends BaseDAO<Employee> {

	public List<Employee> findAll() throws DAOException;

	public List<Employee> findAll(Pagination pagination) throws DAOException;

	public int findCount() throws DAOException;
}
