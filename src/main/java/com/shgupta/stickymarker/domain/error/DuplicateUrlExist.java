package com.shgupta.stickymarker.domain.error;

public class DuplicateUrlExist extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateUrlExist(String msg) {
		super(msg);
	}

}
