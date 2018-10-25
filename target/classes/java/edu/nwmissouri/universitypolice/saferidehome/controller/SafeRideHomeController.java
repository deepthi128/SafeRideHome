/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.controller;

import edu.nwmissouri.universitypolice.saferidehome.exceptions.RideAlreadyModifiedException;
import edu.nwmissouri.universitypolice.saferidehome.pojos.Ride;
import edu.nwmissouri.universitypolice.saferidehome.pojos.User;
import edu.nwmissouri.universitypolice.saferidehome.pojos.Vehicle;
import edu.nwmissouri.universitypolice.saferidehome.util.SafeRideHomeReportUtil;
import edu.nwmissouri.universitypolice.saferidehome.util.SafeRideHomeUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Abhyudaya Reddy Gurram
 */
@WebServlet(name = "SafeRidesController", urlPatterns = {"/safeRideHomeController"})
public class SafeRideHomeController extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String requestFrom;
            HttpSession session;
            SafeRideHomeUtil util;
            SafeRideHomeReportUtil reportUtil;
            String userId;
            String fromAddress;
            String toAddress;
            String errorMessage = null;
            User user;
            int noOfRiders;
            HashMap<String, String> driverVehicleMap = null;
            User validatedUser;
            ServletContext applicationContext;
            ArrayList<Ride> rides;

            requestFrom = request.getParameter("requestFrom");
            session = request.getSession();
            util = new SafeRideHomeUtil();
            applicationContext = getServletContext();
            if (requestFrom == null) {
                requestFrom = "";
            }

            if (requestFrom.equalsIgnoreCase("login")) {
                validatedUser = util.validateUser(request.getParameter("username"), request.getParameter("password"));
                if (validatedUser == null) {
                    forwardRequest("/index.jsp?msg=Invalid User ID or Password. Please try again.", request, response);
                }
                if (validatedUser.getAccountStatus() != null && validatedUser.getAccountStatus().equalsIgnoreCase("REGISTERED")) {
                    forwardRequest("/index.jsp?msg=Please validate your account before you can login to the system.", request, response);
                }
                if (validatedUser.getAccountStatus() != null && validatedUser.getAccountStatus().equalsIgnoreCase("BLOCKED")) {
                    forwardRequest("/index.jsp?msg=Your account has been blocked. Please contact the admin.", request, response);
                } else if (validatedUser.getUserType().equalsIgnoreCase("ADMIN")) {
                    session.setAttribute("user", validatedUser);
                    if (getServletContext().getAttribute("serviceStatus") != null) {
                        session.setAttribute("serviceStatus", "true");
                    }
                    forwardRequest("/adminHome.jsp", request, response);

                } else if (validatedUser.getUserType().equalsIgnoreCase("STUDENT")) {
                    session.setAttribute("user", validatedUser);
                    forwardRequest("/studentHome.jsp?name=" + request.getParameter("username"), request, response);
                } else if (validatedUser.getUserType().equalsIgnoreCase("DISPATCHER")) {
                    session.setAttribute("user", validatedUser);
                    if (getServletContext().getAttribute("serviceStatus") != null) {
                        session.setAttribute("serviceStatus", "true");
                    }
                    forwardRequest("/dispatcherHome.jsp", request, response);

                } else if (validatedUser.getUserType().equalsIgnoreCase("DRIVER")) {
                    session.setAttribute("user", validatedUser);
                    ArrayList<String> vehicles = util.getAvailableVehicles();
                    try {
                        driverVehicleMap = (HashMap<String, String>) getServletContext().getAttribute("driverVehicleMap");
                        if (driverVehicleMap == null) {
                            driverVehicleMap = new HashMap(1, 1);
                            applicationContext.setAttribute("driverVehicleMap", driverVehicleMap);
                        }
                        driverVehicleMap.remove(validatedUser.getUserId());
                        for (String driver : driverVehicleMap.keySet()) {
                            vehicles.remove(driverVehicleMap.get(driver));
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    request.setAttribute("vehicleList", vehicles);
                    forwardRequest("/driverSelectVehicle.jsp", request, response);
                } else {
                    forwardRequest("/index.jsp?msg=Oops! Something went wrong. Please try again later.", request, response);
                }
            } else if (requestFrom.equalsIgnoreCase("driverVehicleSelect")) {
                user = (User) session.getAttribute("user");
                String vehicleId = request.getParameter("vehicle");
                applicationContext = getServletContext();
                driverVehicleMap = (HashMap) applicationContext.getAttribute("driverVehicleMap");
                if (driverVehicleMap == null) {
                    driverVehicleMap = new HashMap(1, 1);
                    applicationContext.setAttribute("driverVehicleMap", driverVehicleMap);
                }
                if (driverVehicleMap.containsValue(vehicleId)) {
                    ArrayList<String> vehicles = util.getAvailableVehicles();
                    try {
                        driverVehicleMap = (HashMap<String, String>) getServletContext().getAttribute("driverVehicleMap");
                        driverVehicleMap.remove(((User) session.getAttribute("user")).getUserId());
                        for (String driver : driverVehicleMap.keySet()) {
                            vehicles.remove(driverVehicleMap.get(driver));
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    request.setAttribute("vehicleList", vehicles);
                    forwardRequest("/driverSelectVehicle.jsp?msg=The vehicle has already been taken by other driver", request, response);
                } else {
                    driverVehicleMap.put(user.getUserId(), vehicleId);
                    forwardRequest("/driverHome.jsp", request, response);
                }

            } else if (requestFrom.equalsIgnoreCase("driverRideList")) {
                user = (User) session.getAttribute("user");
                rides = util.getRidesListForDriver(user.getUserId());
                request.setAttribute("ridesList", rides);
                forwardRequest("/driverRideRequests.jsp", request, response);
            } else if (requestFrom.equalsIgnoreCase("rideList")) {
                user = (User) session.getAttribute("user");
                rides = util.getRidesRequests();
                request.setAttribute("ridesList", rides);
                try {
                    request.setAttribute("drivers", (new ArrayList<String>(((HashMap<String, String>) getServletContext().getAttribute("driverVehicleMap")).keySet())));
                } catch (Exception e) {
                }
                forwardRequest("/dispatcherRideRequests.jsp", request, response);
            } else if (requestFrom.equalsIgnoreCase("signup")) {
                try {
                    String action = request.getParameter("action");
                    userId = request.getParameter("userId");
                    user = new User(userId, request.getParameter("password"), "STUDENT", request.getParameter("name"), request.getParameter("email"), request.getParameter("phone"), "Registered");
                    if (request.getParameter("userType") == null) {
                        user.setUserType("STUDENT");
                    } else {
                        user.setUserType(request.getParameter("userType"));
                    }
                    if (util.addUser(user)) {
                        if (action != null && action.equalsIgnoreCase("addUser")) {
                            forwardRequest("/adminAddUser.jsp?msg=User added successfully", request, response);
                        } else {
                            forwardRequest("/signupSuccess.jsp", request, response);
                        }

                    } else {
                        if (action != null && action.equalsIgnoreCase("addUser")) {
                            forwardRequest("/adminAddUser.jsp?msg=Oops! Something went wrong. Please try again later", request, response);
                        } else {
                            forwardRequest("/index.jsp?msg=Oops! Something went wrong. Please try again later.", request, response);
                        }

                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else if (requestFrom.equalsIgnoreCase("updateUser")) {
                try {
                    user = (User) session.getAttribute("user");
                    User editedUser;
                    editedUser = new User(user.getUserId(), request.getParameter("password"), user.getUserType(), request.getParameter("name"), request.getParameter("email"), request.getParameter("phone"), "Registered");
                    session.setAttribute("user", editedUser);
                    if (util.updateUser(editedUser)) {
                        if (user.getUserType().equalsIgnoreCase("STUDENT")) {
                            forwardRequest("/studentHome.jsp?msg=Your changes have been saved.", request, response);
                        } else if (user.getUserType().equalsIgnoreCase("DISPATCHER")) {
                            forwardRequest("/dispatcherHome.jsp?msg=Your changes have been saved.", request, response);
                        } else if (user.getUserType().equalsIgnoreCase("DRIVER")) {
                            forwardRequest("/driverHome.jsp?msg=Your changes have been saved.", request, response);
                        } else if (user.getUserType().equalsIgnoreCase("ADMIN")) {
                            forwardRequest("/adminHome.jsp?msg=Your changes have been saved.", request, response);
                        }
                    } else {
                        forwardRequest("/index.jsp?msg=Oops! Something went wrong. Please try again later.", request, response);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else if (requestFrom.equalsIgnoreCase("adminManageUsers")) {
                try {
                    if (request.getParameter("userId") != null) {
                        util.updateUserStatus(request.getParameter("userId"), request.getParameter("status"));
                    }
                    request.setAttribute("users", util.getUsers());
                    forwardRequest("/adminManageUsers.jsp", request, response);
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else if (requestFrom.equalsIgnoreCase("adminAddVehicle")) {
                int capacity = 1;
                String msg = null;
                try {
                    try {
                        capacity = Integer.parseInt(request.getParameter("capacity"));
                    } catch (Exception e) {
                    }
                    Vehicle vehicle = new Vehicle(request.getParameter("vehicleId"), request.getParameter("description"), request.getParameter("status"), capacity, request.getParameter("phone"));
                    if (util.addVehicle(vehicle)) {
                        msg = "Vehicle successfully added";
                    } else {
                        msg = "Oops! Something went wrong. Please try again later";
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                forwardRequest("/adminAddVehicle.jsp?msg=" + msg, request, response);
            } else if (requestFrom.equalsIgnoreCase("requestRide")) {
                try {
                    if (getServletContext().getAttribute("serviceStatus") == null) {
                        forwardRequest("/studentHome.jsp?msg=The service is not available right now. The hours of operations are Wed:4PM-2AM, Thu:10PM-2AM, Fri:4PM-4AM, Sat:10PM-2AM", request, response);
                    } else {
                        user = (User) session.getAttribute("user");
                        String rideId = util.getRideId(user.getUserId());
                        if (rideId.equalsIgnoreCase("")) {
                            forwardRequest("/studentRequestRide.jsp", request, response);
                        } else {
                            forwardRequest("/studentHome.jsp?msg=Your ride request, " + rideId + " is still to be/being served. You cannot request another one.", request, response);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else if (requestFrom.equalsIgnoreCase("rideRequest")) {
                try {
                    user = (User) session.getAttribute("user");
                    User requestedBy;
                    fromAddress = request.getParameter("from");
                    toAddress = request.getParameter("to");
                    noOfRiders = Integer.parseInt(request.getParameter("noOfRiders"));
                    if (user.getUserType() != null && user.getUserType().equalsIgnoreCase("STUDENT")) {
                        requestedBy = user;
                    } else {
                        requestedBy = new User();
                        requestedBy.setUserId(request.getParameter("student919"));
                        requestedBy.setName(request.getParameter("name"));
                    }
                    Ride ride = new Ride();
                    String rideId = "";
                    ride.setFrom(fromAddress);
                    ride.setTo(toAddress);
                    ride.setNoOfRiders(noOfRiders);
                    ride.setRequestedBy(requestedBy);
                    ride.setScheduledBy(user);
                    ride.setCallbackNumber(request.getParameter("callBackNumber"));
                    if (user.getUserType() != null && user.getUserType().equalsIgnoreCase("DRIVER")) {
                        rides = util.addRideAndGetRidesListForDriver(ride);
                        if (rides == null) {
                            forwardRequest("/scheduleRide.jsp?msg=Oops! Something went wrong. Please try again later", request, response);
                        }
                        request.setAttribute("ridesList", rides);
                        forwardRequest("/driverRideRequests.jsp", request, response);
                    } else {
                        rideId = util.addRideRequest(ride);
                        if (!"".equals(rideId)) {
                            if (user.getUserType().equalsIgnoreCase("STUDENT")) {
                                forwardRequest("/studentRideRequestSuccess.jsp?rideId=" + rideId, request, response);
                            } else {
                                forwardRequest("/scheduleRideSuccess.jsp?rideId=" + rideId, request, response);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("cancelRide")) {
                Ride ride = new Ride();
                user = (User) session.getAttribute("user");
                ride.setStatus("CANCELLED");
                ride.setUser(user.getUserId());
                ride.setRideId(request.getParameter("rideId"));
                util.changeRideStatus(ride);
                if (user.getUserType().equalsIgnoreCase("STUDENT")) {
                    forwardRequest("/studentHome.jsp?msg=Your ride request has been cancelled", request, response);
                } else if (user.getUserType().equalsIgnoreCase("DISPATCHER")) {
                    forwardRequest("/dispatcherHome.jsp?msg=Ride Request has been cancelled", request, response);
                }
            } else if (requestFrom.equalsIgnoreCase("changeRideStatus")) {
                try {
                    user = (User) session.getAttribute("user");
                    String rowNumber = request.getParameter("rowNum");
                    String rideId = request.getParameter("rideId");
                    String status = request.getParameter("status" + rowNumber);

                    Ride ride = new Ride();
                    ride.setRideId(rideId);
                    ride.setStatus(status);
                    try {
                        ride.setNoOfRiders(Integer.parseInt(request.getParameter("totalRiders" + rowNumber)));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    try {
                        ride.setNoOfFemaleRiders(Integer.parseInt(request.getParameter("femaleRiders" + rowNumber)));
                    } catch (Exception e) {
                        ride.setNoOfFemaleRiders(0);
                        System.out.println(e);
                    }
                    try {
                        ride.setNoOfMaleRiders(Integer.parseInt(request.getParameter("maleRiders" + rowNumber)));
                    } catch (Exception e) {
                        ride.setNoOfMaleRiders(0);
                        System.out.println(e);
                    }
                    ride.setCoRiders(request.getParameter("coRiders" + rowNumber));
                    ride.setDriverComments(request.getParameter("comments" + rowNumber));
                    String driver = request.getParameter("driver" + rowNumber);
                    if (user.getUserType().equalsIgnoreCase("DRIVER")) {
                        driver = user.getUserId();
                    }
                    if (driver != null && !"null".equalsIgnoreCase(driver.trim()) && !"".equalsIgnoreCase(driver.trim())) {
                        ride.setDriver(driver);
                    }
                    ride.setUser(user.getUserId());
                    try {
                        ride.setVehicle(((HashMap<String, String>) getServletContext().getAttribute("driverVehicleMap")).get(driver));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    try {
                        util.changeRideStatus(ride);
                    } catch (RideAlreadyModifiedException e) {
                        errorMessage = e.getMessage();
                    }
                    if (user.getUserType().equalsIgnoreCase("DRIVER")) {
                        rides = util.getRidesListForDriver(user.getUserId());
                        request.setAttribute("ridesList", rides);
                        forwardRequest("/driverRideRequests.jsp?msg=" + errorMessage, request, response);
                    } else {
                        try {
                            rides = util.getRidesRequests();
                            request.setAttribute("ridesList", rides);
                            request.setAttribute("drivers", (new ArrayList<String>(((HashMap<String, String>) getServletContext().getAttribute("driverVehicleMap")).keySet())));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        forwardRequest("/dispatcherRideRequests.jsp?msg=" + errorMessage, request, response);
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("verifyUser")) {
                try {
                    userId = request.getParameter("userId");
                    try {
                        User verifiedUser = util.updateUserStatus(userId, "ACTIVE");
                        session.setAttribute("user", verifiedUser);
                        if (verifiedUser != null && verifiedUser.getUserType() != null) {
                            if (verifiedUser.getUserType().equalsIgnoreCase("ADMIN")) {
                                forwardRequest("/adminHome.jsp?name=" + verifiedUser.getName() + "&msg=Your account has been verified!", request, response);
                            } else if (verifiedUser.getUserType().equalsIgnoreCase("DISPATCHER")) {
                                forwardRequest("/dispatcherHome.jsp?name=" + verifiedUser.getName() + "&msg=Your account has been verified!", request, response);
                            } else if (verifiedUser.getUserType().equalsIgnoreCase("DRIVER")) {
                                forwardRequest("/driverHome.jsp?name=" + verifiedUser.getName() + "&msg=Your account has been verified!", request, response);
                            } else if (verifiedUser.getUserType().equalsIgnoreCase("STUDENT")) {
                                forwardRequest("/studentHome.jsp?name=" + verifiedUser.getName() + "&msg=Your account has been verified!", request, response);
                            }
                        }
                        forwardRequest("/studentHome.jsp?name=" + verifiedUser.getName() + "&msg=Your account has been verified!", request, response);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("getStudentPhoneNumberAndName")) {
                try {
                    String student919 = request.getParameter("student919");
                    try {
                        out.println(util.getStudentPhoneNumberAndName(student919));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("manageVehicles")) {
                try {
                    request.setAttribute("vehicles", util.getVehicles());
                    forwardRequest("/manageVehicles.jsp", request, response);
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else if (requestFrom.equalsIgnoreCase("updateVehicleStatus")) {
                try {
                    util.updateVehicleStatus(request.getParameter("vehicleId"), request.getParameter("status"));
                    request.setAttribute("vehicles", util.getVehicles());
                    forwardRequest("/manageVehicles.jsp", request, response);
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else if (requestFrom.equalsIgnoreCase("logout")) {
                try {
                    user = (User) session.getAttribute("user");
                    try {
                        if (user.getUserType().equalsIgnoreCase("DRIVER")) {
                            ((HashMap) getServletContext().getAttribute("driverVehicleMap")).remove(user.getUserId());
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    session.removeAttribute("user");
                    forwardRequest("/index.jsp?msg=Successfully Logged out!", request, response);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("rideStatus")) {
                try {
                    double[] status;
                    double time;
                    int noOfActiveVehicles = 1;
                    try {
                        driverVehicleMap = (HashMap<String, String>) getServletContext().getAttribute("driverVehicleMap");
                        noOfActiveVehicles = driverVehicleMap.size();
                    } catch (Exception e) {
                    }
                    user = (User) session.getAttribute("user");
                    String rideId = request.getParameter("rideId");
                    if (rideId == null) {
                        rideId = util.getRideId(user.getUserId());
                    }
                    if (rideId != null && !rideId.equalsIgnoreCase("")) {
                        status = util.getRideStatus(rideId);
                        // for demonstration. remove thise lines after a midterm presentation
//                        if(status[0] == 0){
//                            status[0] = 38;
//                        }
//                            status[1] = 2;
                        // end remove lines
                        time = status[0];
                        if (time == 0) {
                            time = 10;
                        }
                        if (status[1] != 0) {
                            time *= status[1];
                        }
                        if (noOfActiveVehicles == 0) {
                            noOfActiveVehicles = 1;
                        }
                        time /= 2;
                        time /= noOfActiveVehicles;
                        String eta = ((int) time) / 60 + " HRS " + ((int) time) % 60;
                        if(time> (noOfActiveVehicles * 10)){
                            time = time - (noOfActiveVehicles * 10);
                            eta = ((int) time) / 60 + " HRS " + ((int) time) % 60 + " - " + eta;
                        }
                        
                        forwardRequest("/rideStatus.jsp?rideId=" + rideId + "&eta=" + eta + " MIN&stops=" + (int) status[1], request, response);
                    } else {
                        if (user.getUserType().equalsIgnoreCase("STUDENT")) {
                            forwardRequest("/studentHome.jsp?msg=You don't have an open ride request or you are already being served the request", request, response);
                        }

                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("dispatcherRideStatus")) {
                try {
                    String[] status;
                    double time = 0;
                    int noOfActiveVehicles = 1;
                    int noOfReqs = 0;
                    status = util.getRideStatusForDispatcher(request.getParameter("student919"), request.getParameter("phone"));
                    if (status != null) {
                        try {
                            driverVehicleMap = (HashMap<String, String>) getServletContext().getAttribute("driverVehicleMap");
                            noOfActiveVehicles = driverVehicleMap.size();
                        } catch (Exception e) {
                        }

                        //  status = util.getRideStatus(rideId);
                        // for demonstration. remove thise lines after a midterm presentation
//                        if(status[0] == 0){
//                            status[0] = 38;
//                        }
//                            status[1] = 2;
                        // end remove lines
                        try {
                            time = Double.parseDouble(status[1]);
                        } catch (Exception e) {
                        }
                        try {
                            noOfReqs = ((int) Double.parseDouble(status[2]));
                        } catch (Exception e) {
                        }

                        if (time == 0) {
                            time = 10;
                        }
                        if (noOfReqs != 0) {
                            time *= noOfReqs;
                        }
                        if (noOfActiveVehicles == 0) {
                            noOfActiveVehicles = 1;
                        }
                        
                        time /= 2;
                        time /= noOfActiveVehicles;
                        String eta = ((int) time) / 60 + " HRS " + ((int) time) % 60 + " MIN ";
                        if(time> (noOfActiveVehicles * 10)){
                            time = time - (noOfActiveVehicles * 10);
                            eta = ((int) time) / 60 + " HRS " + ((int) time) % 60 + " MIN - " + eta;
                        }
                        
                        forwardRequest("/rideStatus.jsp?rideId=" + eta +"&stops=" + (int) noOfReqs, request, response);
                    } else {
                        forwardRequest("/dispatcherCheckRideStatus.jsp?msg=No ride request exists with the given details", request, response);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("forgotPassword")) {
                try {
                    String resp = util.sendResetPasswordEmail(request.getParameter("userId"), request.getParameter("phone"), request.getParameter("email"));
                    if (resp.equalsIgnoreCase("MAILSENT")) {
                        forwardRequest("/index.jsp?msg=An email has been sent to you with the steps to reset your password.", request, response);
                    } else if (resp.equalsIgnoreCase("MAILEXCEPTION")) {
                        forwardRequest("/index.jsp?msg=An email has been sent to you with the steps to reset your password.", request, response);
                    } else if (resp.equalsIgnoreCase("INVALIDDETAILS")) {
                        forwardRequest("/forgotPassword.jsp?msg=Phone# and/or Email does not match with the username", request, response);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("resetPassword")) {
                try {
                    user = util.validateUser(request.getParameter("userId"));
                    if (user != null) {
                        session.setAttribute("user", user);
                        forwardRequest("/editProfile.jsp", request, response);
                    } else {
                        forwardRequest("/index.jsp?msg=Oops! Something went wrong. Please try again later or contact Admin.", request, response);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("validateUserId")) {
                try {
                    userId = request.getParameter("userId");
                    try {
                        out.println(util.vaidateUserId(userId));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("releaseVehicles")) {
                String driver = request.getParameter("driver");
                try {
                    driverVehicleMap = (HashMap<String, String>) getServletContext().getAttribute("driverVehicleMap");
                    driverVehicleMap.remove(driver);
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    request.setAttribute("driverVehicleMap", driverVehicleMap);
                    forwardRequest("/releaseVehicles.jsp", request, response);
                }
            } else if (requestFrom.equalsIgnoreCase("setServiceStatus")) {
                String status = request.getParameter("status");
                try {
                    if (status != null && status.equalsIgnoreCase("true")) {
                        getServletContext().setAttribute("serviceStatus", "true");
                        session.setAttribute("serviceStatus", "true");
                    } else {
                        getServletContext().removeAttribute("serviceStatus");
                        session.removeAttribute("serviceStatus");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("postMessage")) {
                user = (User) session.getAttribute("user");
                String adminMessage = request.getParameter("message");
                try {
                    if (adminMessage != null && !"".equalsIgnoreCase(adminMessage.trim())) {
                        getServletContext().setAttribute("message", adminMessage);
                    } else {
                        getServletContext().removeAttribute("message");
                    }

                    if (user.getUserType().equalsIgnoreCase("ADMIN")) {
                        forwardRequest("/adminHome.jsp?msg=Message successfully posted on the Home Page", request, response);
                    } else if (user.getUserType().equalsIgnoreCase("DISPATCHER")) {
                        forwardRequest("/dispatcherHome.jsp?msg=Message successfully posted on the Home Page", request, response);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("removeMessage")) {
                user = (User) session.getAttribute("user");
                try {
                        getServletContext().removeAttribute("message");
                    if (user.getUserType().equalsIgnoreCase("ADMIN")) {
                        forwardRequest("/adminHome.jsp?msg=Message on the Home Page has been removed", request, response);
                    } else if (user.getUserType().equalsIgnoreCase("DISPATCHER")) {
                        forwardRequest("/dispatcherHome.jsp?msg=Message on the Home Page has been removed", request, response);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (requestFrom.equalsIgnoreCase("getReport")) {
                try {
                    String fullyQualifiedFileName="";
                    HSSFWorkbook workbook = null;
                    FileOutputStream reportStream = null;
                    String fromDate = "";
                    String toDate = "";
                    String fromTime = "";
                    String toTime = "";
                    String interval = "";
                    
                    reportUtil = new SafeRideHomeReportUtil();
                    fromDate = request.getParameter("fromDate");
                    toDate = request.getParameter("toDate");
                    fromTime = request.getParameter("fromTime");
                    toTime = request.getParameter("toTime");
                    interval = request.getParameter("interval");
                    workbook = reportUtil.generateReport(fromDate, toDate, fromTime, toTime, interval);
                    fullyQualifiedFileName = getServletContext().getRealPath("/")+"files/SafeRideHomeReport.xls";
                    reportStream = new FileOutputStream(new File(fullyQualifiedFileName));
                    //reportStream = new FileOutputStream(new File("C:/1/a.xls"));
                    workbook.write(reportStream);
                    reportStream.close();
                    forwardRequest("/generateReport.jsp?result=success", request, response);
                } catch (Exception e) {
                    System.out.println(e);
                    forwardRequest("/generateReport.jsp?msg=An error occured while generating report", request, response);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            out.close();
        }
    }

    public void forwardRequest(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
