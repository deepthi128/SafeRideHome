/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nwmissouri.universitypolice.saferidehome.pojos;

/**
 *
 * @author Deepthi Nelavalli
 */
public class RidersByVehicle {
    private String vehicleId;
    private int noOfRiders;

    public RidersByVehicle() {
    }

    public RidersByVehicle(String vehicleId, int noOfRiders) {
        this.vehicleId = vehicleId;
        this.noOfRiders = noOfRiders;
    }
    
    

    public String getVehicleId() {
        return vehicleId;
    }
    

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getNoOfRiders() {
        return noOfRiders;
    }

    public void setNoOfRiders(int noOfRiders) {
        this.noOfRiders = noOfRiders;
    }
        
}
