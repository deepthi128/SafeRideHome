/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.dao;

import edu.nwmissouri.universitypolice.saferidehome.exceptions.RideAlreadyModifiedException;
import edu.nwmissouri.universitypolice.saferidehome.pojos.Ride;
import edu.nwmissouri.universitypolice.saferidehome.pojos.User;
import edu.nwmissouri.universitypolice.saferidehome.pojos.Vehicle;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author Abhyudaya Reddy Gurram
 */
public class SafeRideHomeDao {

    private Connection connection;
    Properties properties = new Properties();

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

    public User updateUserStatus(String userId, String status) {
        connectToDatabase();
        PreparedStatement statement = null;
        ResultSet resultset = null;
        int accStatus = 1;
        try {
            if (status.equalsIgnoreCase("REGISTERED")) {
                accStatus = 1;
            } else if (status.equalsIgnoreCase("ACTIVE")) {
                accStatus = 2;
            } else if (status.equalsIgnoreCase("BLOCKED")) {
                accStatus = 3;
            }
            statement = connection.prepareStatement("UPDATE USER SET ACCOUNTSTATUS = ? WHERE USERID = ?");
            statement.setInt(1, accStatus);
            statement.setString(2, userId);
            statement.executeUpdate();
            closePreparedStatement(statement, null);
            return getUserById(userId);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, resultset);
            closeConnection();
        }
        return null;
    }

    public User validateUser(String userId, String password) {
        connectToDatabase();
        PreparedStatement userValidationStatement = null;
        ResultSet userValidationResultSet = null;
        try {
            userValidationStatement = connection.prepareStatement("SELECT * FROM USER WHERE USERID = ? AND PASSWORD = ?");
            userValidationStatement.setString(1, userId);
            userValidationStatement.setString(2, password);
            userValidationResultSet = userValidationStatement.executeQuery();
            if (userValidationResultSet.next()) {
                return new User(userValidationResultSet.getString(1), userValidationResultSet.getString(2), userValidationResultSet.getString(3),
                        userValidationResultSet.getString(4), userValidationResultSet.getString(5), userValidationResultSet.getString(6),
                        userValidationResultSet.getString(7));
            }
        } catch (Exception e) {
            return null;
        } finally {
            closePreparedStatement(userValidationStatement, userValidationResultSet);
            closeConnection();
        }
        return null;
    }

    public User validateUser(String userId) {
        connectToDatabase();
        PreparedStatement userValidationStatement = null;
        ResultSet userValidationResultSet = null;
        try {
            userValidationStatement = connection.prepareStatement("SELECT * FROM USER WHERE USERID = ?");
            userValidationStatement.setString(1, userId);
            userValidationResultSet = userValidationStatement.executeQuery();
            if (userValidationResultSet.next()) {
                return new User(userValidationResultSet.getString(1), userValidationResultSet.getString(2), userValidationResultSet.getString(3),
                        userValidationResultSet.getString(4), userValidationResultSet.getString(5), userValidationResultSet.getString(6),
                        userValidationResultSet.getString(7));
            }
        } catch (Exception e) {
            return null;
        } finally {
            closePreparedStatement(userValidationStatement, userValidationResultSet);
            closeConnection();
        }
        return null;
    }

    public boolean addUser(User user) {
        connectToDatabase();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO USER (USERID, PASSWORD, NAME, EMAILID, PHONE, USERTYPE, ACCOUNTSTATUS) VALUES (?,?,?,?,?,?,?)");
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmailId());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getUserType());
            statement.setInt(7, 1);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, null);
            closeConnection();
        }
        return false;
    }

    public boolean updateUser(User user) {
        connectToDatabase();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE USER SET PASSWORD = ?, NAME = ?, EMAILID = ?, PHONE = ? WHERE USERID = ?");
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmailId());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getUserId());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, null);
            closeConnection();
        }
        return false;
    }

    public String addRideRequest(Ride ride) {
        connectToDatabase();
        PreparedStatement statement = null;
        ride.setRideId("" + System.currentTimeMillis());

        try {
            statement = connection.prepareStatement("INSERT INTO RIDE (RIDEID, TOADD, FROMADD, NOOFRIDERS, REQUESTEDBY, REQUESTEDDATE, REQUESTEDTIME, STATUS, SCHEDULEDBY, CALLBACKNUMBER, RIDERNAME) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, ride.getRideId());
            statement.setString(2, ride.getTo());
            statement.setString(3, ride.getFrom());
            statement.setInt(4, ride.getNoOfRiders());
            statement.setString(5, ride.getRequestedBy().getUserId());
            statement.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
            statement.setTime(7, new Time(new java.util.Date().getTime()));
            statement.setInt(8, 1);
            statement.setString(9, ride.getScheduledBy().getUserId());
            statement.setString(10, ride.getCallbackNumber());
            try {
                statement.setString(11, ride.getRequestedBy().getName());
            } catch (Exception e) {
                statement.setString(11, "");
            }

            if (statement.executeUpdate() > 0) {
                return ride.getRideId();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, null);
            closeConnection();
        }
        return "";
    }

    public ArrayList<Ride> addRideAndGetRidesListForDriver(Ride ride) {
        connectToDatabase();
        PreparedStatement statement = null;
        try {
            ride.setRideId("" + System.currentTimeMillis());
            statement = connection.prepareStatement("INSERT INTO RIDE (RIDEID, TOADD, FROMADD, NOOFRIDERS, REQUESTEDBY, REQUESTEDDATE, REQUESTEDTIME, ASSIGNEDDATE, ASSIGNEDTIME, DRIVER, ASSIGNEDBY, STATUS, SCHEDULEDBY, CALLBACKNUMBER, RIDERNAME) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, ride.getRideId());
            statement.setString(2, ride.getTo());
            statement.setString(3, ride.getFrom());
            statement.setInt(4, ride.getNoOfRiders());
            statement.setString(5, ride.getRequestedBy().getUserId());
            statement.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
            statement.setTime(7, new Time(new java.util.Date().getTime()));
            statement.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
            statement.setTime(9, new Time(new java.util.Date().getTime()));
            statement.setString(10, ride.getScheduledBy().getUserId());
            statement.setString(11, ride.getScheduledBy().getUserId());
            statement.setInt(12, 2);
            statement.setString(13, ride.getScheduledBy().getUserId());
            statement.setString(14, ride.getCallbackNumber());
            try {
                statement.setString(15, ride.getRequestedBy().getName());
            } catch (Exception e) {
                statement.setString(15, "");
            }
            if (statement.executeUpdate() > 0) {
                return getRidesListForDriver(ride.getScheduledBy().getUserId());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, null);
            closeConnection();
        }
        return null;
    }

    public User getUserById(String id) {
        connectToDatabase();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM USER WHERE USERID = ?");
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
            }
        } catch (Exception e) {
            return null;
        } finally {
            closePreparedStatement(statement, resultSet);
            closeConnection();
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        connectToDatabase();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM USER");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7)));
            }
        } catch (Exception e) {
            return null;
        } finally {
            closePreparedStatement(statement, resultSet);
            closeConnection();
        }
        return users;

    }

    public boolean updateVehicleStatus(String vehicleId, String status) {
        connectToDatabase();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("UPDATE VEHICLE SET STATUS = ? WHERE VEHICLEID = ?");
            statement.setInt(1, (status != null && status.equalsIgnoreCase("SUSPENDED") ? 2 : 1));
            statement.setString(2, vehicleId);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            return false;
        } finally {
            closePreparedStatement(statement, resultSet);
            closeConnection();
        }
        return true;
    }

    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        connectToDatabase();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT VEHICLEID, DESCRIPTION, STATUS FROM VEHICLE");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                vehicles.add(new Vehicle(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (Exception e) {
            return null;
        } finally {
            closePreparedStatement(statement, resultSet);
            closeConnection();
        }
        return vehicles;

    }

    public ArrayList<Ride> getRidesListForDriver(String driverId) {
        ArrayList<Ride> rides = new ArrayList();
        if (connection == null) {
            connectToDatabase();
        }
        PreparedStatement ridesQuery = null;
        ResultSet rideResultSet = null;
        User requestedBy = null;
        Ride ride;
        try {
            ridesQuery = connection.prepareStatement("SELECT RIDE.RIDEID, RIDE.TOADD, RIDE.FROMADD, RIDE.NOOFRIDERS, "
                    + " RIDE.REQUESTEDBY, RIDE.CALLBACKNUMBER, RIDE.STATUS, RIDE.NOOFFEMALERIDERS, "
                    + " RIDE.NOOFMALERIDERS, RIDE.CORIDERS, RIDE.DRIVERCOMMENTS, RIDE.RIDERNAME FROM RIDE WHERE ( STATUS = 1 )"
                    + " OR (STATUS IN(2,3,6) AND DRIVER = ?) ORDER BY RIDE.REQUESTEDDATE, RIDE.REQUESTEDTIME");
            ridesQuery.setString(1, driverId);
            rideResultSet = ridesQuery.executeQuery();
            while (rideResultSet.next()) {
                ride = new Ride();
                ride.setRideId(rideResultSet.getString(1));
                ride.setTo(rideResultSet.getString(2));
                ride.setFrom(rideResultSet.getString(3));
                ride.setNoOfRiders(rideResultSet.getInt(4));
                requestedBy = getUserById(rideResultSet.getString(5));
                if (requestedBy == null) {
                    requestedBy = new User(rideResultSet.getString(5));
                    requestedBy.setName(rideResultSet.getString(12));
                }
                ride.setRequestedBy(requestedBy);
                ride.setCallbackNumber(rideResultSet.getString(6));
                ride.setStatus(rideResultSet.getString(7));
                ride.setNoOfFemaleRiders(rideResultSet.getInt(8));
                ride.setNoOfMaleRiders(rideResultSet.getInt(9));
                ride.setCoRiders(rideResultSet.getString(10) == null ? "" : rideResultSet.getString(10));
                ride.setDriverComments(rideResultSet.getString(11) == null ? "" : rideResultSet.getString(11));
                rides.add(ride);
            }
        } catch (Exception e) {
            System.out.println(e);
            rides = null;
        } finally {
            closePreparedStatement(ridesQuery, rideResultSet);
            closeConnection();
        }
        return rides;
    }

    public ArrayList<Ride> getRidesRequests() {
        ArrayList<Ride> rides = new ArrayList();
        connectToDatabase();
        PreparedStatement ridesQuery = null;
        ResultSet rideResultSet = null;
        Ride ride;
        try {
            ridesQuery = connection.prepareStatement("SELECT RIDE.RIDEID, RIDE.TOADD, RIDE.FROMADD, RIDE.NOOFRIDERS, "
                    + " RIDE.REQUESTEDBY, RIDE.CALLBACKNUMBER, RIDE.STATUS, RIDE.NOOFFEMALERIDERS, "
                    + " RIDE.NOOFMALERIDERS, RIDE.DRIVER, RIDE.DRIVERCOMMENTS, RIDE.RIDERNAME FROM RIDE WHERE  STATUS IN (1,2,3,6) ORDER BY RIDE.REQUESTEDDATE, RIDE.REQUESTEDTIME");
            rideResultSet = ridesQuery.executeQuery();
            while (rideResultSet.next()) {
                ride = new Ride();
                ride.setRideId(rideResultSet.getString(1));
                ride.setTo(rideResultSet.getString(2));
                ride.setFrom(rideResultSet.getString(3));
                ride.setNoOfRiders(rideResultSet.getInt(4));
                User requestedBy = getUserById(rideResultSet.getString(5));
                if (requestedBy == null) {
                    requestedBy = new User();
                    requestedBy.setUserId(rideResultSet.getString(5));
                    requestedBy.setName(rideResultSet.getString(12));
                }
                ride.setRequestedBy(requestedBy);
                ride.setCallbackNumber(rideResultSet.getString(6));
                ride.setStatus(rideResultSet.getString(7));
                ride.setNoOfFemaleRiders(rideResultSet.getInt(8));
                ride.setNoOfMaleRiders(rideResultSet.getInt(9));
                ride.setDriver(rideResultSet.getString(10));
                ride.setDriverComments(rideResultSet.getString(11) == null ? "" : rideResultSet.getString(11));
                rides.add(ride);
            }
        } catch (Exception e) {
            return null;
        } finally {
            closePreparedStatement(ridesQuery, rideResultSet);
            closeConnection();
        }
        return rides;
    }

    public boolean changeRideStatus(Ride ride) throws RideAlreadyModifiedException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        String assignedBy = "";
        int noOfNWStudents = 1;
        try {
            connectToDatabase();
            if (ride.getStatus().equals("ASSIGNED")) {
                stmt = connection.prepareStatement("UPDATE RIDE SET STATUS = ?, ASSIGNEDDATE = ?, ASSIGNEDTIME = ?, ASSIGNEDBY = ?, DRIVER = ? WHERE RIDEID = ? AND ASSIGNEDDATE IS NULL AND ASSIGNEDTIME IS NULL");
                stmt.setInt(1, 2);
                stmt.setDate(2, new Date(new java.util.Date().getTime()));
                stmt.setTime(3, new Time(new java.util.Date().getTime()));
                stmt.setString(4, ride.getUser());
                stmt.setString(5, ride.getDriver());
                stmt.setString(6, ride.getRideId());
            } else if (ride.getStatus().equals("PICKEDUP")) {
                if (ride.getCoRiders() != null && !ride.getCoRiders().equalsIgnoreCase("")) {
                    ride.setCoRiders("919" + ride.getCoRiders().replaceAll(";", ";919"));
                }
                stmt = connection.prepareStatement("UPDATE RIDE SET STATUS = ?, PICKEDUPDATE = ?, PICKEDUPTIME = ?, DRIVER = ?, NOOFRIDERS = ?, CORIDERS = ?, NOOFMALERIDERS = ?, NOOFFEMALERIDERS = ?, "
                        + "NOOFOTHERRIDERS = ?, NOOFNWSTUDENTS = ?, NOOFNONNWSTUDENTS=?, DRIVERCOMMENTS = ?, VEHICLE = ? WHERE RIDEID = ? AND PICKEDUPDATE IS NULL AND PICKEDUPTIME IS NULL");
                stmt.setInt(1, 3);
                stmt.setDate(2, new Date(new java.util.Date().getTime()));
                stmt.setTime(3, new Time(new java.util.Date().getTime()));
                stmt.setString(4, ride.getDriver());
                stmt.setInt(5, ride.getNoOfRiders());
                stmt.setString(6, ride.getCoRiders());
                stmt.setInt(7, ride.getNoOfMaleRiders());
                stmt.setInt(8, ride.getNoOfFemaleRiders());
                stmt.setInt(9, ride.getNoOfRiders() - ride.getNoOfMaleRiders() - ride.getNoOfFemaleRiders());
                try {
                    if (ride.getCoRiders()!=null && !"".equalsIgnoreCase(ride.getCoRiders()) && !"NULL".equalsIgnoreCase(ride.getCoRiders())) {
                        noOfNWStudents = 1+ride.getCoRiders().split(";").length;
                    }
                } catch (Exception e) {
                }
                stmt.setInt(10, noOfNWStudents);
                try {
                    stmt.setInt(11, ride.getNoOfRiders() - noOfNWStudents);
                } catch (Exception e) {
                    stmt.setInt(10, 0);
                }
                stmt.setString(12, ride.getDriverComments());
                stmt.setString(13, ride.getVehicle());
                stmt.setString(14, ride.getRideId());
            } else if (ride.getStatus().equals("DROPPEDOFF")) {
                stmt = connection.prepareStatement("UPDATE RIDE SET STATUS = ?, DROPPEDOFFDATE = ?, DROPPEDOFFTIME = ?, DRIVERCOMMENTS = ? WHERE RIDEID = ?");
                stmt.setInt(1, 4);
                stmt.setDate(2, new Date(new java.util.Date().getTime()));
                stmt.setTime(3, new Time(new java.util.Date().getTime()));
                stmt.setString(4, ride.getDriverComments());
                stmt.setString(5, ride.getRideId());
            } else if (ride.getStatus().equals("CANCELLED")) {
                stmt = connection.prepareStatement("UPDATE RIDE SET STATUS = ?, CANCELLEDDATE = ?, CANCELLEDTIME = ?, CANCELLEDBY = ? WHERE RIDEID = ? AND CANCELLEDDATE IS NULL AND CANCELLEDTIME IS NULL");
                stmt.setInt(1, 5);
                stmt.setDate(2, new Date(new java.util.Date().getTime()));
                stmt.setTime(3, new Time(new java.util.Date().getTime()));
                stmt.setString(4, ride.getUser());
                stmt.setString(5, ride.getRideId());
            } else if (ride.getStatus().equals("OPEN")) {
                stmt = connection.prepareStatement("UPDATE RIDE SET STATUS = 1, ASSIGNEDDATE = NULL, ASSIGNEDTIME = NULL, ASSIGNEDBY = NULL, DRIVER = NULL, PICKEDUPDATE = NULL, PICKEDUPTIME = NULL, CORIDERS = NULL, NOOFMALERIDERS = 0, NOOFFEMALERIDERS = 0, NOOFOTHERRIDERS = 0, NOOFNWSTUDENTS = 0, NOOFNONNWSTUDENTS=0, DRIVERCOMMENTS = NULL, VEHICLE = NULL, DROPPEDOFFDATE = NULL, DROPPEDOFFTIME = NULL, DRIVERCOMMENTS = NULL, CANCELLEDDATE = NULL, CANCELLEDTIME = NULL, CANCELLEDBY = NULL WHERE RIDEID = ?");
                stmt.setString(1, ride.getRideId());
            } else if (ride.getStatus().equals("NOANSWER")) {
                stmt = connection.prepareStatement("UPDATE RIDE SET STATUS = ?, NOANSWERDATE = ?, NOANSWERTIME = ? WHERE RIDEID = ?");
                stmt.setInt(1, 6);
                stmt.setDate(2, new Date(new java.util.Date().getTime()));
                stmt.setTime(3, new Time(new java.util.Date().getTime()));
                stmt.setString(4, ride.getRideId());
            }
            if (stmt.executeUpdate() > 0) {
                return true;
            } else {
                closeStatement(stmt, null);
                stmt = connection.prepareStatement("SELECT ASSIGNEDBY FROM RIDE WHERE RIDEID = ?");
                stmt.setString(1, ride.getRideId());
                resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    assignedBy = resultSet.getString(1);
                }
                throw new RideAlreadyModifiedException("The ride is already being served/cancelled by " + assignedBy);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RideAlreadyModifiedException(e.getMessage());
        } finally {
            closePreparedStatement(stmt, resultSet);
            closeConnection();
        }
        //return false;
    }

    public String getStudentPhoneNumberAndName(String student919) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connectToDatabase();
            stmt = connection.prepareStatement("SELECT PHONE, NAME FROM USER WHERE USERID = ?");
            stmt.setString(1, student919);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1) + "#" + resultSet.getString(2);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(stmt, resultSet);
            closeConnection();
        }
        return "";
    }

    public ArrayList<String> getAvailableVehicles() {
        ArrayList<String> vehicles = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connectToDatabase();
            stmt = connection.prepareStatement("SELECT VEHICLEID FROM VEHICLE WHERE STATUS = 1");
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                vehicles.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(stmt, resultSet);
            closeConnection();
        }
        return vehicles;
    }

    public boolean addVehicle(Vehicle vehicle) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connectToDatabase();
            stmt = connection.prepareStatement("INSERT INTO VEHICLE VALUES(?,?,?,?,?)");
            stmt.setString(1, vehicle.getVehicleId());
            stmt.setString(2, vehicle.getDescription());
            stmt.setInt(3, vehicle.getCapacity());
            stmt.setString(4, vehicle.getPhone());
            try {
                stmt.setInt(5, vehicle.getStatus().equalsIgnoreCase("ACTIVE") ? 1 : 2);
            } catch (Exception e) {
                stmt.setInt(5, 1);
            }

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(stmt, resultSet);
            closeConnection();
        }
        return false;
    }

    public String getRideId(String userId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connectToDatabase();
            statement = connection.prepareStatement("SELECT RIDEID FROM RIDE WHERE REQUESTEDBY = ? AND STATUS = 'OPEN'");
            statement.setString(1, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, resultSet);
            closeConnection();
        }
        return "";
    }

    public int vaidateUserId(String userId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connectToDatabase();
            statement = connection.prepareStatement("SELECT USERID FROM USER WHERE USERID = ?");
            statement.setString(1, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, resultSet);
            closeConnection();
        }

        return 0;
    }

    public double[] getRideStatus(String rideId) {
        double[] status = new double[2];
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connectToDatabase();
            statement = connection.prepareStatement("SELECT AVG(TIMESTAMPDIFF(MINUTE,REQUESTEDTIME,DROPPEDOFFTIME)) FROM RIDE WHERE REQUESTEDDATE = DROPPEDOFFDATE");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                status[0] = resultSet.getDouble(1);
            }
            closePreparedStatement(statement, resultSet);
            statement = connection.prepareStatement("SELECT COUNT(*) FROM RIDE WHERE REQUESTEDTIME<(SELECT REQUESTEDTIME FROM RIDE WHERE RIDEID = ?) AND STATUS <>'DROPPEDOFF' && STATUS <>'CANCELLED'");
            statement.setString(1, rideId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                status[1] = resultSet.getInt(1);
            }
            closePreparedStatement(statement, resultSet);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, resultSet);
            closeConnection();
        }
        return status;
    }

    public String[] getRideStatusForDispatcher(String student919, String phoneNumber) {
        String[] status = new String[3];
        double[] details;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String rideId = "";
        try {
            connectToDatabase();
            closePreparedStatement(statement, resultSet);
            statement = connection.prepareStatement("SELECT RIDEID FROM RIDE WHERE REQUESTEDBY LIKE ? AND CALLBACKNUMBER LIKE ? AND STATUS <>'DROPPEDOFF' && STATUS <>'CANCELLED'");
            statement.setString(1, "%" + student919 + "%");
            statement.setString(2, "%" + phoneNumber + "%");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rideId = resultSet.getString(1);
            } else {
                return null;
            }

            details = getRideStatus(rideId);
            status[0] = rideId;
            status[1] = "" + details[0];
            status[2] = "" + details[1];
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, resultSet);
            closeConnection();
        }
        return status;
    }

    public boolean validateUserDetailsForPasswordReset(String userId, String phoneNumber, String emailId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connectToDatabase();
            statement = connection.prepareStatement("SELECT USERID FROM USER WHERE USERID = ? AND EMAILID = ? AND PHONE = ?");
            statement.setString(1, userId);
            statement.setString(2, emailId);
            statement.setString(3, phoneNumber);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closePreparedStatement(statement, resultSet);
            closeConnection();
        }
        return false;
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
