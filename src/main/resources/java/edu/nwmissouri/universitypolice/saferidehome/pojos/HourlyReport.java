/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.pojos;

import java.util.ArrayList;

/**
 *
 * @author Deepthi
 */
public class HourlyReport {
    private String interval;
    private int noOfCalls;
    private ArrayList<RidersByVehicle> ridersByVehicles;
    private int totalNoOfRiders;

    public HourlyReport() {
    }

    public HourlyReport(String interval, int noOfCalls, ArrayList<RidersByVehicle> ridersByVehicles, int totalNoOfRiders) {
        this.interval = interval;
        this.noOfCalls = noOfCalls;
        this.ridersByVehicles = ridersByVehicles;
        this.totalNoOfRiders = totalNoOfRiders;
    }
    

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getNoOfCalls() {
        return noOfCalls;
    }

    public void setNoOfCalls(int noOfCalls) {
        this.noOfCalls = noOfCalls;
    }

    public ArrayList<RidersByVehicle> getRidersByVehicles() {
        return ridersByVehicles;
    }

    public void setRidersByVehicles(ArrayList<RidersByVehicle> ridersByVehicles) {
        this.ridersByVehicles = ridersByVehicles;
    }

    public int getTotalNoOfRiders() {
        return totalNoOfRiders;
    }

    public void setTotalNoOfRiders(int totalNoOfRiders) {
        this.totalNoOfRiders = totalNoOfRiders;
    }
    
    
}
