package com.shgupta.stickymarker.domain;

import java.util.UUID;

public class User extends BaseDomain {
	String firstname;
	String lastname;
	String emailaddress;

	public User(String firstname, String lastname, String emailaddress) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailaddress = emailaddress;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	static public String formatName(String firstname) {
		return firstname + " -> " + UUID.randomUUID().toString();
	}

}
