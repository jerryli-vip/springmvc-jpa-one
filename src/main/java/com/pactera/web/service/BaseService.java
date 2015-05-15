package com.pactera.web.service;

import java.util.List;

import com.pactera.web.exception.ServiceException;

public interface BaseService<T> {

	List<T> listAll(Class<T> c) throws ServiceException;

	T findById(Class<T> c, int id) throws ServiceException;

	boolean save(Object object) throws ServiceException;

	boolean update(Object object) throws ServiceException;

	boolean delete(Class<T> c, int id) throws ServiceException;

	boolean delete(Object object) throws ServiceException;
}
