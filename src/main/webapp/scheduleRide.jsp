<%-- 
    Document   : scheduleRide
    Created on : Jun 10, 2013, 2:16:30 PM
    Author     : Abhyudaya Reddy Gurram
--%>

<%@page import="edu.nwmissouri.universitypolice.saferidehome.pojos.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Safe Rides Home - Schedule a Ride</title>
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


        <%if (session.getAttribute("user") == null) {
                request.getRequestDispatcher("/index.jsp?msg=Session Expired. Please login again.").forward(request, response);
            }%>


        <%
            User user = (User) session.getAttribute("user");
            String homeUrl = user.getUserType().equalsIgnoreCase("DRIVER") ? "driverHome.jsp" : user.getUserType().equalsIgnoreCase("DISPATCHER") ? "dispatcherHome.jsp" : "adminHome.jsp";
        %>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

        <!-- This code is taken from http://twitter.github.com/bootstrap/examples/hero.html -->

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
                            <a href="<%=homeUrl%>" > <img src="images/home.gif" title="Go Home" height="35" width="35"></a>
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
                            <form action="safeRideHomeController?requestFrom=rideRequest" method="post" autocomplete="off"> 
                                <h1>Schedule a Ride</h1> 
                                
                                <p> 
                                    <!--<label for="919#" class="uname" > 919# </label> -->
                                    <input id="from" name="from" required="required" type="text" placeholder="From Location/Address"/>
                                </p>
                                <p> 
                                    <!--<label for="name" class="uname" data-icon="p"> Name </label>-->
                                    <input id="to" name="to" required="required" type="text" placeholder="To Location/Address" /> 
                                </p>
                                <p> 
                                    <!--<label for="phone" class="uname" data-icon="p"> Phone </label>-->
                                    <input id="noOfRiders" name="noOfRiders" required="required" type="number" placeholder="No of Riders" /> 
                                </p>
                                <p> 
                                    <!--<label for="919#" class="uname" > 919# </label> -->
                                    <input id="student919" name="student919" id="student919" onblur="getRiderPhoneNumberAndName()" required="required" type="text" placeholder="Student 919#"/>
                                </p>
                                <p> 
                                    <!--<label for="919#" class="uname" > 919# </label> -->
                                    <input id="name" name="name" id="name" required="required" type="text" placeholder="Student Name"/>
                                </p>
                                <p> 
                                    <!--<label for="phone" clas s="uname" data-icon="p"> Phone </label>-->
                                    <input id="callBackNumber" name="callBackNumber" required="required" type="text" placeholder="Call back number." /> 
                                </p>

                                <p class="login button" align="center"> 
                                    <input type="submit" value="Submit" />  &nbsp;&nbsp;&nbsp;<input type="Reset" value="Reset" /> 
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

        <script lang="JavaScript">

                                        function createRequest()
                                        {
                                            try
                                            {
                                                request = new XMLHttpRequest();
                                            }
                                            catch (tryMS)
                                            {
                                                try
                                                {
                                                    request = new ActiveXObject("Msxml2.XMLHTTP");
                                                }
                                                catch (otherMS)
                                                {
                                                    try {
                                                        request = new ActiveXObject("Microsoft.XMLHTTP");
                                                    }
                                                    catch (failed)
                                                    {
                                                        request = null;
                                                    }
                                                }
                                            }
                                            return request;
                                        }

                                        function getRiderPhoneNumberAndName() {
                                            request = createRequest();

                                            if (request == null) {
                                                alert("Unable to create request");
                                                return;
                                            }

                                            var url = "safeRideHomeController?requestFrom=getStudentPhoneNumberAndName&student919=" + document.getElementById("student919").value;
                                            request.open("GET", url, true);
                                            request.onreadystatechange = setStudentPhoneNumberAndName;
                                            request.send(null);
                                        }

                                        function setStudentPhoneNumberAndName() {
                                            var details = request.responseText.split("#");
                                            if (request.readyState == 4) {
                                                if (request.status == 200) {
                                                    document.getElementById("callBackNumber").value = details[0];
                                                    if (details.length > 1) {
                                                        document.getElementById("name").value = details[1];
                                                    } else {
                                                        document.getElementById("name").value = "";
                                                    }
                                                }
                                            }
                                        }

        </script>
</html>
