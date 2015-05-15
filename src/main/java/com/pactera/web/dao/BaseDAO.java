package com.pactera.web.dao;

import java.util.List;

import com.pactera.web.exception.DAOException;

public interface BaseDAO<T> {

	List<T> listAll(Class<T> c) throws DAOException;

	T findById(Class<T> c, int id) throws DAOException;

	boolean save(Object object) throws DAOException;

	boolean update(Object object) throws DAOException;

	boolean delete(Class<T> c, int id) throws DAOException;

	boolean delete(Object object) throws DAOException;

}
