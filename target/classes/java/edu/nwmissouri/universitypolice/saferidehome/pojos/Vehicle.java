/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.pojos;

/**
 *
 * @author Abhyudaya Reddy Gurram
 */
public class Vehicle {
    private String vehicleId;
    private String description;
    private String status;
    private int capacity;
    private String phone;

    public Vehicle(String vehicleId, String description, String status, int capacity, String phone) {
        this.vehicleId = vehicleId;
        this.description = description;
        this.status = status;
        this.capacity = capacity;
        this.phone = phone;
    }
    
    
    public Vehicle(String vehicleId, String description, String status) {
        this.vehicleId = vehicleId;
        this.description = description;
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
