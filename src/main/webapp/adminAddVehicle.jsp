<%-- 
    Document   : adminAddVehicle
    Created on : Jun 10, 2013, 2:16:30 PM
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Safe Ride Home - Add Vehicle</title>
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
        <script src="js/generalValidations.js"></script>
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

        <!-- This code is taken from http://twitter.github.com/bootstrap/examples/hero.html -->

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
                            <a href="adminHome.jsp" > <img src="images/home.gif" title="Go Home" height="35" width="35"></a>
                            &nbsp;&nbsp;&nbsp;
                            <a href="safeRideHomeController?requestFrom=logout" > <img title="Logout" src="images/logout.gif" height="33" width="33"></a> 
                        </td>
                    </tr>
                </table>
            </div>
            <div clas="row-fluid">
                <div class="hero">
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form action="safeRideHomeController?requestFrom=adminAddVehicle" method="POST" autocomplete="off"> 
                                <h1>Add Vehicle</h1> 
                                <%if (request.getParameter("msg") != null) {%>   
                                <p> 
                                <h4> <font family="Lucida Console" color="red"><%=request.getParameter("msg")%> </font> </h4>
                                </p> 
                                <%}%>
                                <p> 
                                    <!--<label for="919#" class="uname" > 919# </label> -->
                                    <input id="vehicleId" name="vehicleId" required="required" type="text" placeholder="VehicleId"/>
                                </p>
                                <p> 
                                    <!--<label for="name" class="uname" data-icon="p"> Name </label>-->
                                    <input id="description" name="description" required="required" type="text" placeholder="Description" /> 
                                </p>
                                <p> 
                                    <!--<label for="name" class="uname" data-icon="p"> Name </label>-->
                                    <input id="capacity" name="capacity" required="required" type="number" placeholder="Capacity" /> 
                                    <!--   </p>
                                                                        
                                                                       <p> 
                                           <label for="email" class="uname" data-icon="p"> Smail </label> 
                                           <input id="Schedule" name="Schedule" required="required" type="text" placeholder="Schedule: DDHHMMHHMM" /> -->
                                </p>
                                <p> 
                                    <!--<label for="name" class="uname" data-icon="p"> Name </label>-->
                                    <input id="phone" name="phone" required="required" type="text" placeholder="Phone" /> 
                                </p>
                                <p>
                                    <select width:12px>
                                        <option value="Active"> Active </option>
                                        <option value="Suspended"> Suspended </option>
                                    </select>
                                </p>
                                <p class="login button" align="center"> 
                                    <input type="submit" value="Add Vehicle" />  &nbsp;&nbsp;&nbsp;<input type="Reset" value="Reset" /> 
                                </p>
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
</html>
