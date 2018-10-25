package edu.nwmissouri.universitypolice.saferidehome.ws.user;

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
import edu.nwmissouri.universitypolice.saferidehome.ws.login.LoginResponse;


@Path("/user")
public class UserService {
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/register")
	public String registerService(User user) {
		System.out.println("User Registration..");
		return register(user);
	}

	public String register(User user) {
		SafeRideHomeService util;
		try {
			util = new SafeRideHomeService();
			if (util.addUser(user)) {
				return "SUCCESS";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "ERROR";
	}

	@Path("/emailverification/{userId}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@GET
	public String emailValidation(@PathParam("userId") String userId) {
		System.out.println("validating User..");
		return validateUser(userId);
	}

	public String validateUser(String userId) {
		SafeRideHomeService util = new SafeRideHomeService();
		LoginResponse response = null;
		Gson gson;
		try {
			User verifiedUser = util.updateUserStatus(userId, "ACTIVE");
			if (verifiedUser != null && verifiedUser.getUserType() != null) {
				response = new LoginResponse("SUCCESS", verifiedUser);
			} else {
				response = new LoginResponse("ERROR", null);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		gson = new Gson();
		return gson.toJson(response);
	}

}
