package edu.nwmissouri.universitypolice.saferidehome.ws.ride;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import edu.nwmissouri.universitypolice.saferidehome.pojos.Ride;
import edu.nwmissouri.universitypolice.saferidehome.service.SafeRideHomeService;

@Path("/ride")
public class RideService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/new")
	public String newRide(Ride request) {
		return requestRide(request);
	}

	private String requestRide(Ride request) {
		RideResponse response = null;
		SafeRideHomeService util;
		try {
			util = new SafeRideHomeService();
			util.addRideRequest(request);
			response = new RideResponse(request.getStatus(),
					request.getRideId());
		} catch (Exception e) {
			System.out.println(e);
		}
		Gson gson = new Gson();
		return gson.toJson(response);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status/{userId}")
	public String rideStatus(@PathParam("userId") String userId) {
		return statusOfRide(userId);
	}

	@Context
	ServletContext applicationContext;

	@SuppressWarnings({ "unused", "unchecked" })
	private String statusOfRide(String userId) {
		double[] status;
		String rideId;
		double time;
		Gson gson;
		SafeRideHomeService util = new SafeRideHomeService();
		HashMap<String, String> driverVehicleMap;
		int noOfActiveVehicles = 1;
		try {
			driverVehicleMap = (HashMap<String, String>) applicationContext.getAttribute("driverVehicleMap");
			noOfActiveVehicles = driverVehicleMap.size();
		} catch (Exception e) {
		}
			rideId = util.getRideId(userId);
	
			status = util.getRideStatus(rideId);
			time = status[0];
			if (time == 0) {
				time = 10;
			}
			if (status[1] != 0) {
				time *= status[1];
			}
			if (noOfActiveVehicles == 0) {
				noOfActiveVehicles = 1;
			}
			time /= 2;
			time /= noOfActiveVehicles;
			String eta = ((int) time) / 60 + " HRS " + ((int) time)
					% 60;
			if (time > (noOfActiveVehicles * 10)) {
				time = time - (noOfActiveVehicles * 10);
				eta = ((int) time) / 60 + " HRS " + ((int) time)
						% 60 + " - " + eta;
			}

			
			gson = new Gson();
			
			RideStatus rideStatus = new RideStatus(eta, ""+((int)status[1]));
			
			return gson.toJson(rideStatus);

	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/cancel/{userId}")
	public String cancel(@PathParam("userId") String userId) {
		return cancelRide(userId);
	}

	private String cancelRide(String userId) {
		SafeRideHomeService util = new SafeRideHomeService();
		String rideId;
		Ride ride;
		try {
			rideId = util.getRideId(userId);
			if (rideId == null) {
				return "NO_RIDE_FOUND";
			}
			ride = new Ride();
			ride.setRideId(rideId);
			ride.setUser(userId);
			ride.setStatus("CANCELLED");
			if (util.changeRideStatus(ride)) {
				return "SUCCESS";
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return "FAILURE";
	}

	@Path("/new")
	@Produces(MediaType.TEXT_PLAIN)
	private String getServiceStatus() {
		if (applicationContext.getAttribute("serviceStatus") != null) {
			return "ACTIVE";
		} else {
			return "INACTIVE";
		}
	}

}
