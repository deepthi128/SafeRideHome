/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.pojos;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Abhyudaya Reddy Gurram
 */
public class RideFullValue {

    private String rideId;
    private String from;
    private String to;
    private int noOfRiders;
    private int noOfFemaleRiders;
    private int noOfMaleRiders;
    private String status;
    private String requestedBy;
    private String phone;
    private String coRiders;
    private String driverComments;
    private String name;
    private String scheduledBy;
    private String estimatedTime;
    private String noOfStops;
    private String vehicle;

    public Date getPickedUpDate() {
        return pickedUpDate;
    }

    public void setPickedUpDate(Date pickedUpDate) {
        this.pickedUpDate = pickedUpDate;
    }

    public Time getPickedUpTime() {
        return pickedUpTime;
    }

    public void setPickedUpTime(Time pickedUpTime) {
        this.pickedUpTime = pickedUpTime;
    }

    public Date getDroppedOffDate() {
        return droppedOffDate;
    }

    public void setDroppedOffDate(Date droppedOffDate) {
        this.droppedOffDate = droppedOffDate;
    }

    public Time getDroppedOffTime() {
        return droppedOffTime;
    }

    public void setDroppedOffTime(Time droppedOffTime) {
        this.droppedOffTime = droppedOffTime;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public Time getCancelledTime() {
        return cancelledTime;
    }

    public void setCancelledTime(Time cancelledTime) {
        this.cancelledTime = cancelledTime;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public int getNoOfNWStudents() {
        return noOfNWStudents;
    }

    public void setNoOfNWStudents(int noOfNWStudents) {
        this.noOfNWStudents = noOfNWStudents;
    }
    private String riderComments;
    private String driver;
    private Date requestedDate;
    private Time requestedTime;
    private Date assignedDate;
    private Time assignedTime;
    private Date pickedUpDate;
    private Time pickedUpTime;
    private Date droppedOffDate;
    private Time droppedOffTime;
    private Date cancelledDate;
    private Time cancelledTime;
    private String assignedBy;
    private String cancelledBy;
    private int noOfNWStudents;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCoRiders() {
        return coRiders;
    }

    public void setCoRiders(String coRiders) {
        this.coRiders = coRiders;
    }

    public String getDriverComments() {
        return driverComments;
    }

    public void setDriverComments(String driverComments) {
        this.driverComments = driverComments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScheduledBy() {
        return scheduledBy;
    }

    public void setScheduledBy(String scheduledBy) {
        this.scheduledBy = scheduledBy;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Time getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(Time assignedTime) {
        this.assignedTime = assignedTime;
    }

    public String getNoOfStops() {
        return noOfStops;
    }

    public void setNoOfStops(String noOfStops) {
        this.noOfStops = noOfStops;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getRiderComments() {
        return riderComments;
    }

    public void setRiderComments(String riderComments) {
        this.riderComments = riderComments;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Time getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Time requestedTime) {
        this.requestedTime = requestedTime;
    }

    
}
