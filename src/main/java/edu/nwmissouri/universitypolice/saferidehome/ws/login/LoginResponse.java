package edu.nwmissouri.universitypolice.saferidehome.ws.login;

import edu.nwmissouri.universitypolice.saferidehome.pojos.User;

public class LoginResponse {
	private String responsecode;
	private User user;

	public LoginResponse(String statuscode, User user) {
		this.responsecode = statuscode;
		this.user = user;
	}

	public String getResponsecode() {
		return responsecode;
	}

	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
