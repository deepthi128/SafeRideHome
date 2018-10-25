package edu.nwmissouri.universitypolice.saferidehome.ws.ride;


public class RideResponse {
	private String statuscode;
	private String rideId;
	public String getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}
	public String getRideId() {
		return rideId;
	}
	public void setRideId(String rideId) {
		this.rideId = rideId;
	}
	public RideResponse() {
		this("","");
	}
	
	public RideResponse(String statuscode, String rideId) {
		this.statuscode = statuscode;
		this.rideId = rideId;
	}
	
	
	
}
