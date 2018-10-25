/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nwmissouri.universitypolice.saferidehome.pojos;

/**
 *
 * @author Deepthi Nelavalli
 */
public class GenderByVehicle {
    private String vehicleId;
    private int noOfMaleRiders;
    private int noOfFemaleRiders;
    private int noOfOtherRiders;

    public GenderByVehicle(String vehicleId, int noOfMaleRiders, int noOfFemaleRiders, int noOfOtherRiders) {
        this.vehicleId = vehicleId;
        this.noOfMaleRiders = noOfMaleRiders;
        this.noOfFemaleRiders = noOfFemaleRiders;
        this.noOfOtherRiders = noOfOtherRiders;
    }
    

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getNoOfMaleRiders() {
        return noOfMaleRiders;
    }

    public int getNoOfOtherRiders() {
        return noOfOtherRiders;
    }

    public void setNoOfOtherRiders(int noOfOtherRiders) {
        this.noOfOtherRiders = noOfOtherRiders;
    }

    public void setNoOfMaleRiders(int noOfMaleRiders) {
        this.noOfMaleRiders = noOfMaleRiders;
    }

    public int getNoOfFemaleRiders() {
        return noOfFemaleRiders;
    }

    public void setNoOfFemaleRiders(int noOfFemaleRiders) {
        this.noOfFemaleRiders = noOfFemaleRiders;
    }

    
        
}
