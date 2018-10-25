package edu.nwmissouri.universitypolice.saferidehome.ws.ride;

public class RideStatus {

	private String eta;
	private String noOfStops;

	public RideStatus() {
		super();
	}

	public RideStatus(String eta, String noOfStops) {
		super();
		this.eta = eta;
		this.noOfStops = noOfStops;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public String getNoOfStops() {
		return noOfStops;
	}

	public void setNoOfStops(String noOfStops) {
		this.noOfStops = noOfStops;
	}

}
