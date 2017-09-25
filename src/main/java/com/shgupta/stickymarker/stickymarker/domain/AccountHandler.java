package com.shgupta.stickymarker.domain;

public class AccountHandler<T> {
	T ref;

	public AccountHandler(T ref) {
		this.ref = ref;
	}

	public T getRef() {
		return ref;
	}
}
