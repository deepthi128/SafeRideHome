<%-- 
    Document   : driverSelectVehicle
    Created on : Apr 15, 2013, 10:35:01 AM
    Author     : Abhyudaya Reddy Gurram
--%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Safe Ride Home - Select Vehicle</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="shortcut icon" type="image/ico" href="images/favicon.gif" />	
    <style>
        body {
            padding-top: 60px;
            padding-bottom: 40px;
            background-image:url('images/bg.png');
        }
    </style>
    <link rel="stylesheet" href="css/bootstrap-responsive.min.css">
    <link rel="stylesheet" href="css/main.css">
    <link href='http://fonts.googleapis.com/css?family=Droid+Sans' rel='stylesheet' type='text/css'>
    <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body>
    <%if (session.getAttribute("user") == null) {
                    request.getRequestDispatcher("/index.jsp?msg=Session Expired. Please login again.").forward(request, response);
                }%>
    <div class="container">
        <div class="row-fluid">
            <table width="100%">
                <tr>
                    <td>
                        <div class="span12 title well">
                            <table align="center">
                                <tr>
                                    <td> <img height="65" width="65" src="images/safeRideLogo.jpg"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
                                    <td><h2>Safe Ride Home</h2> </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <a href="safeRideHomeController?requestFrom=logout" > <img title="Logout" src="images/logout.gif" height="33" width="33"></a> 
                    </td>
                </tr>
            </table>
        </div>
        <div clas="row-fluid">
            <div class="hero">
                <div id="wrapper">
                    <div id="login" class="animate form">
                        <form action="index.jsp" method="post" autocomplete="on"> 
                            <h1>
                                Select the Vehicle
                            </h1> 
                            <%if (request.getParameter("msg") != null) {%>   
                            <p> 
                            <h4> <font family="Lucida Console" color="red"><%=request.getParameter("msg")%> </font> </h4>
                            </p> 
                            <%}%>
                            <%
                                ArrayList<String> vehicles = (ArrayList) request.getAttribute("vehicleList");
                                if (vehicles != null && vehicles.size() < 1) {%>   

                            <p> 
                            <h3> <font family="Lucida Console" color="red"> No Vehicle Available. </font> </h3>
                            </p> 
                            <p class="login button"> 
                                <input type="submit" value="Sign out" /> 
                            </p>
                            <%} else {%>
                            <table>
                                <tr>
                                    <td>
                                        <h3> Please select the vehicle: &nbsp;&nbsp;&nbsp;</h3>
                                    </td>
                                    <td style="vertical-align: middle">
                                        <select width:5px id="vehicle" name="vehicle" align="right" style="width: 120px">
                                            <%for (String vehicleId : vehicles) {%> 
                                            <option value='<%=vehicleId%>' > <%=vehicleId%>  </option>
                                            <%}%>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                            <p class="login button"> 
                                <input type="button" onclick="submitPage()" value="Go!" /> 
                            </p>
                            <%}%>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div> <!-- /container -->

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.0.min.js"><\/script>')</script>
    <script src="js/vendor/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
    <script lang="JavaScript">
                                    function submitPage() {
                                        document.forms[0].method = "POST";
                                        document.forms[0].action = "safeRideHomeController?requestFrom=driverVehicleSelect";
                                        document.forms[0].submit();
                                    }
    </script>
</html>
