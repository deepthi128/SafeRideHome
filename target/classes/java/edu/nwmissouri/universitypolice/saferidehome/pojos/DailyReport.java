/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.pojos;

import java.util.ArrayList;

/**
 *
 * @author Deepthi Nelavalli
 */
public class DailyReport {
   private String date;
   private int totalNoOfCalls;
   private int totalNoOfRiders;
   private ArrayList<HourlyReport> hourlyReports;
   private ArrayList<GenderByVehicle> genderReports;
   private ArrayList<StudentsByVehicle> studentReports;

    public DailyReport() {
    }
   

    public DailyReport(String date, int totalNoOfCalls, int totalNoOfRiders, ArrayList<HourlyReport> hourlyReports, ArrayList<GenderByVehicle> genderReports, ArrayList<StudentsByVehicle> studentReports) {
        this.date = date;
        this.totalNoOfCalls = totalNoOfCalls;
        this.totalNoOfRiders = totalNoOfRiders;
        this.hourlyReports = hourlyReports;
        this.genderReports = genderReports;
        this.studentReports = studentReports;
    }
   

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalNoOfCalls() {
        return totalNoOfCalls;
    }

    public void setTotalNoOfCalls(int totalNoOfCalls) {
        this.totalNoOfCalls = totalNoOfCalls;
    }

    public int getTotalNoOfRiders() {
        return totalNoOfRiders;
    }

    public void setTotalNoOfRiders(int totalNoOfRiders) {
        this.totalNoOfRiders = totalNoOfRiders;
    }

    public ArrayList<HourlyReport> getHourlyReports() {
        return hourlyReports;
    }

    public void setHourlyReports(ArrayList<HourlyReport> hourlyReports) {
        this.hourlyReports = hourlyReports;
    }

    public ArrayList<GenderByVehicle> getGenderReports() {
        return genderReports;
    }

    public void setGenderReports(ArrayList<GenderByVehicle> genderReports) {
        this.genderReports = genderReports;
    }

    public ArrayList<StudentsByVehicle> getStudentReports() {
        return studentReports;
    }

    public void setStudentReports(ArrayList<StudentsByVehicle> studentReports) {
        this.studentReports = studentReports;
    }  
}
