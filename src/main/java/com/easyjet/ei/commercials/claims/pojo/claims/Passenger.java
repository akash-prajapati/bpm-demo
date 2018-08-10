package com.easyjet.ei.commercials.claims.pojo.claims;

import java.io.Serializable;

public class Passenger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 
	  private String firstName = null;
	 
	  private String lastName = null;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

}
