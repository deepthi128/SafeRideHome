/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.pojos;

import java.util.ArrayList;

/**
 *
 * @author Deepthi Nelavelli
 */
public class Report {
    private String reportMonthAndYear;
    private int totalNoOfCalls;
    private int totalNoOfRiders;
    ArrayList<DailyReport> dailyReports;

    public String getReportMonthAndYear() {
        return reportMonthAndYear;
    }

    public void setReportMonthAndYear(String reportMonthAndYear) {
        this.reportMonthAndYear = reportMonthAndYear;
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

    public ArrayList<DailyReport> getDailyReports() {
        return dailyReports;
    }

    public void setDailyReports(ArrayList<DailyReport> dailyReports) {
        this.dailyReports = dailyReports;
    }
    
    
}
