/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.pojos;

/**
 *
 * @author Abhyudaya Reddy Gurram
 */
public class Ride {

    private String rideId;
    private String from;
    private String to;
    private int noOfRiders;
    private int noOfFemaleRiders;
    private int noOfMaleRiders;
    private String status;
    private User requestedBy;
    private User scheduledBy;
    private String callbackNumber;
    private String coRiders;
    private String driverComments;
    private String name;
    private String user;
    private String vehicle;
    private String driver;

    
    public User getScheduledBy() {
        return scheduledBy;
    }

    public void setScheduledBy(User scheduledBy) {
        this.scheduledBy = scheduledBy;
    }
    
    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
    
    public String getCallbackNumber() {
        return callbackNumber;
    }

    public void setCallbackNumber(String callbackNumber) {
        this.callbackNumber = callbackNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ride() {
    }

    public int getNoOfFemaleRiders() {
        return noOfFemaleRiders;
    }

    public void setNoOfFemaleRiders(int noOfFemaleRiders) {
        this.noOfFemaleRiders = noOfFemaleRiders;
    }

    public int getNoOfMaleRiders() {
        return noOfMaleRiders;
    }

    public void setNoOfMaleRiders(int noOfMaleRiders) {
        this.noOfMaleRiders = noOfMaleRiders;
    }

    public String getDriverComments() {
        return driverComments;
    }

    public void setDriverComments(String driverComments) {
        this.driverComments = driverComments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getCoRiders() {
        return coRiders;
    }

    public void setCoRiders(String coRiders) {
        this.coRiders = coRiders;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getNoOfRiders() {
        return noOfRiders;
    }

    public void setNoOfRiders(int noOfRiders) {
        this.noOfRiders = noOfRiders;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    
}
