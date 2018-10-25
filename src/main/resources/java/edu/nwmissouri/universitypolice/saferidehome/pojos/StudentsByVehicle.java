/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nwmissouri.universitypolice.saferidehome.pojos;

/**
 *
 * @author Deepthi Nelavalli
 */
public class StudentsByVehicle {
    private String vehicleId;
    private int noOfNWStudents;
    private int noOfNonNWStudents;

    public StudentsByVehicle(String vehicleId, int noOfNWStudents, int noOfNonNWStudents) {
        this.vehicleId = vehicleId;
        this.noOfNWStudents = noOfNWStudents;
        this.noOfNonNWStudents = noOfNonNWStudents;
    }
    
    

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getNoOfNWStudents() {
        return noOfNWStudents;
    }

    public void setNoOfNWStudents(int noOfNWStudents) {
        this.noOfNWStudents = noOfNWStudents;
    }

    public int getNoOfNonNWStudents() {
        return noOfNonNWStudents;
    }

    public void setNoOfNonNWStudents(int noOfNonNWStudents) {
        this.noOfNonNWStudents = noOfNonNWStudents;
    }
    
}
