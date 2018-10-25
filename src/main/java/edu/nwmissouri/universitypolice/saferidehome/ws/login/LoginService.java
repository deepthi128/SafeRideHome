package edu.nwmissouri.universitypolice.saferidehome.ws.login;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import edu.nwmissouri.universitypolice.saferidehome.pojos.User;
import edu.nwmissouri.universitypolice.saferidehome.service.SafeRideHomeService;

@Path("/login")
public class LoginService {
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/validateUser")
	public String login(LoginRequest request) {
		return validateUser(request);
	}

	private String validateUser(LoginRequest request) {
		SafeRideHomeService util;
		LoginResponse response = null;
		User user = null;
		Gson gson = null;
		try {
			util = new SafeRideHomeService();

			user = util
					.validateUser(request.getUserId(), request.getPassword());

			if (user != null) {
				response = new LoginResponse("SUCCESS", user);
			} else {
				response = new LoginResponse("INVALID_CREDENTIALS", null);
			}

			gson = new Gson();
		} catch (Exception e) {
			System.out.println(e);
		}
		return gson.toJson(response);
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/forgotPassword")
	public String forgotPassword(LoginRequest request)
	{
		System.out.println("Forgot Password Called..");
		return passwordReset(request);
		
	}
	
	public String passwordReset(LoginRequest request)
	{
		SafeRideHomeService util;
		try{
		util = new SafeRideHomeService();
		String resp= util.sendResetPasswordEmail(
				request.getUserId(),
				request.getPhoneNumber(),
				request.getEmailId());
		if(resp.equals("MAILSENT"))
		{
			return "SUCCESS";
		}
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return "Failed";

	}
	
	
}
