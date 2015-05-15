package com.pactera.web.exception;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1966495071175222208L;

	public DAOException() {

	}

	public DAOException(String msg) {
		super(msg);
	}

	public DAOException(Throwable e) {
		super(e.getMessage());
	}

}
