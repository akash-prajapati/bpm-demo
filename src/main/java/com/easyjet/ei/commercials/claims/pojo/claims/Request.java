package com.easyjet.ei.commercials.claims.pojo.claims;

import java.io.Serializable;

public class Request implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Claims claim;

	public Claims getClaim() {
		return claim;
	}

	public void setClaim(Claims claim) {
		this.claim = claim;
	}

}
