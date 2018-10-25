/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.dao;

import edu.nwmissouri.universitypolice.saferidehome.pojos.DailyReport;
import edu.nwmissouri.universitypolice.saferidehome.pojos.GenderByVehicle;
import edu.nwmissouri.universitypolice.saferidehome.pojos.HourlyReport;
import edu.nwmissouri.universitypolice.saferidehome.pojos.Report;
import edu.nwmissouri.universitypolice.saferidehome.pojos.RidersByVehicle;
import edu.nwmissouri.universitypolice.saferidehome.pojos.StudentsByVehicle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SafeRideHomeReportDao {

    private Connection connection;

    private void connectToDatabase() {
        try {
            String username = "root";
            String password = "root";
            String url = "jdbc:mysql://localhost:3306/saferidehome";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void closeStatement(Statement statement, ResultSet resultSet) {
        try {
            statement.close();
        } catch (Exception e) {
        }
        try {
            resultSet.close();
        } catch (Exception e) {
        }
    }

    public Report getReport(Date startDate, Date endDate, Time startTime, Time endTime, int interval) {
        Report report = new Report();
        ArrayList<DailyReport> dailyReports = new ArrayList<DailyReport>();
        PreparedStatement stmt = null;
        ResultSet resultset = null;
        DailyReport dailyReport = null;

        try {
            connectToDatabase();
            stmt = connection.prepareStatement("SELECT COUNT(RIDEID), SUM(NOOFRIDERS) FROM RIDE "
                    + " WHERE REQUESTEDDATE BETWEEN ? AND ? AND REQUESTEDTIME BETWEEN ? AND ? AND STATUS = 4");
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));
            endTime.setSeconds(59);
            stmt.setTime(3, startTime);
            stmt.setTime(4, endTime);
            resultset = stmt.executeQuery();
            if (resultset.next()) {
                report.setTotalNoOfCalls(resultset.getInt(1));
                report.setTotalNoOfRiders(resultset.getInt(2));
            }
            Date incrementedDate;
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            incrementedDate = c.getTime();
            while (incrementedDate.compareTo(endDate) <= 0) {
                dailyReport = getDailyReport(incrementedDate, startTime, endTime, interval);
                if (dailyReport != null) {
                    dailyReports.add(dailyReport);
                }
                c.add(Calendar.DATE, 1);
                incrementedDate = c.getTime();
            }
            report.setDailyReports(dailyReports);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection();
            closePreparedStatement(stmt, resultset);
        }
        return report;
    }

    private DailyReport getDailyReport(Date date, Time startTime, Time endTime, int interval) {
        DailyReport dailyReport = new DailyReport();
        ArrayList<HourlyReport> hourlyReports = new ArrayList<HourlyReport>();
        ArrayList<GenderByVehicle> genderReports = new ArrayList<GenderByVehicle>();
        ArrayList<StudentsByVehicle> studentReports = new ArrayList<StudentsByVehicle>();
        PreparedStatement statement;
        ResultSet resultSet;
        Time intervalStart;
        Time intervalEnd;
        int totalNoOfCalls = 0;
        int totalNumberOfRides = 0;
        HourlyReport hourlyReport = null;

        try {
            statement = connection.prepareStatement("SELECT COUNT(rideID),SUM(NOOFRIDERS) FROM RIDE WHERE REQUESTEDDATE=? AND REQUESTEDTIME BETWEEN ? AND ? AND STATUS = 4");
            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setTime(2, startTime);
            statement.setTime(3, endTime);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalNoOfCalls = resultSet.getInt(1);
                totalNumberOfRides = resultSet.getInt(2);
            }
            closePreparedStatement(statement, resultSet);
            if (totalNoOfCalls == 0) {
                return null;
            }
            statement = connection.prepareStatement("SELECT VEHICLE, SUM(noOfRiders), SUM(noOfMaleRiders), SUM(noOfFemaleRiders) FROM RIDE WHERE REQUESTEDDATE = ? AND REQUESTEDTIME BETWEEN ? AND ? AND STATUS = 4 GROUP BY VEHICLE");
            java.sql.Date dt;
            dt = new java.sql.Date(date.getDate());
            dt.setDate(date.getDate());
            dt.setMonth(date.getMonth());
            dt.setYear(date.getYear());

            statement.setDate(1, dt);
            statement.setTime(2, startTime);
            statement.setTime(3, endTime);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                genderReports.add(new GenderByVehicle(resultSet.getString(1), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(2) - resultSet.getInt(3) - resultSet.getInt(4)));
            }
            dailyReport.setGenderReports(genderReports);
            closePreparedStatement(statement, resultSet);

            statement = connection.prepareStatement("SELECT VEHICLE, SUM(noOfRiders), SUM(NOOFNWSTUDENTS) FROM RIDE WHERE REQUESTEDDATE = ? AND REQUESTEDTIME BETWEEN ? AND ? AND STATUS = 4 GROUP BY VEHICLE");
            statement.setDate(1, dt);
            statement.setTime(2, startTime);
            statement.setTime(3, endTime);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                studentReports.add(new StudentsByVehicle(resultSet.getString(1), resultSet.getInt(3), resultSet.getInt(2) - resultSet.getInt(3)));
            }
            dailyReport.setStudentReports(studentReports);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(startTime);
            intervalStart = startTime;
            do {
                c1.add(Calendar.MINUTE, interval);
                intervalEnd = new Time(c1.getTime().getTime());
                intervalEnd.setSeconds(59);
                hourlyReport = getHourlyReport(dt, intervalStart, intervalEnd);
                if (hourlyReport != null) {
                    hourlyReports.add(hourlyReport);
                }
                intervalEnd.setSeconds(00);
                intervalStart = intervalEnd;
            } while (intervalEnd.compareTo(endTime) < 0);
            dailyReport = new DailyReport(date.toString(), totalNoOfCalls, totalNumberOfRides, hourlyReports, genderReports, studentReports);
        } catch (Exception e) {
            System.out.println(e);
        }
        return dailyReport;
    }

    private HourlyReport getHourlyReport(java.sql.Date date, Time startTime, Time endTime) {
        HourlyReport hourlyReport = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<RidersByVehicle> ridersByVehicleList = new ArrayList<RidersByVehicle>();
        try {
            statement = connection.prepareStatement("SELECT COUNT(*) FROM RIDE WHERE REQUESTEDDATE=? AND REQUESTEDTIME BETWEEN ? AND ?  AND STATUS = 4 ");
            statement.setDate(1, date);
            statement.setTime(2, startTime);
            statement.setTime(3, endTime);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                hourlyReport = new HourlyReport();
                hourlyReport.setInterval(String.format("%02d:%02d - %02d:%02d", startTime.getHours(), startTime.getMinutes(), endTime.getHours(), endTime.getMinutes()));
                hourlyReport.setNoOfCalls(resultSet.getInt(1));
                closePreparedStatement(statement, resultSet);
            }else{
                closePreparedStatement(statement, resultSet);
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            statement = connection.prepareStatement("SELECT VEHICLE,SUM(NOOFRIDERS) FROM RIDE WHERE REQUESTEDDATE=? AND REQUESTEDTIME BETWEEN ? AND ?  AND STATUS = 4 GROUP BY VEHICLE");
            statement.setDate(1, date);
            statement.setTime(2, startTime);
            statement.setTime(3, endTime);
            hourlyReport.setInterval(String.format("%02d:%02d - %02d:%02d", startTime.getHours(), startTime.getMinutes(), endTime.getHours(), endTime.getMinutes()));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ridersByVehicleList.add(new RidersByVehicle(resultSet.getString(1), resultSet.getInt(2)));
            }
            hourlyReport.setRidersByVehicles(ridersByVehicleList);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            statement = connection.prepareStatement("SELECT SUM(noOfRiders) FROM RIDE WHERE requesteddate=? AND requestedTime BETWEEN ? AND ? AND STATUS = 4");
            statement.setDate(1, date);
            statement.setTime(2, startTime);
            statement.setTime(3, endTime);
            //String interval2=startTime.getHours()+":"+startTime.getMinutes()+"-"+endTime+":"+endTime.getMinutes();
            // hourlyReport.setInterval(String.format("%02d:%02d - %02d:%02d", startTime.getHours(), startTime.getMinutes(), endTime.getHours(), endTime.getMinutes()));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hourlyReport.setTotalNoOfRiders(resultSet.getInt(1));
            }


        } catch (Exception e) {
            System.out.println(e);
        }
        return hourlyReport;

    }

    private void closePreparedStatement(PreparedStatement statement, ResultSet resultSet) {
        try {
            statement.close();
        } catch (Exception e) {
        }
        try {
            resultSet.close();
        } catch (Exception e) {
        }
    }
}