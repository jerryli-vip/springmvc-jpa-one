package com.pactera.web.dao;

import java.util.List;

import com.pactera.web.common.Pagination;
import com.pactera.web.exception.DAOException;
import com.pactera.web.model.Department;

public interface DepartmentDAO extends BaseDAO<Department> {

	public List<Department> findAll() throws DAOException;

	public List<Department> findAll(Pagination pagination) throws DAOException;

	public int findCount() throws DAOException;
}
