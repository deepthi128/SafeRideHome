<%-- 
    Document   : generateReport
    Created on : 
    Author     : Abhyudaya Reddy Gurram
--%>

<%@page import="edu.nwmissouri.universitypolice.saferidehome.pojos.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Safe Ride Home - Schedule a Ride</title>
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
                            <form action="safeRideHomeController?requestFrom=getReport" method="post" autocomplete="off"> 
                                <h1>Statistics</h1> 
                                <%if (request.getParameter("msg") != null) {%>   
                                <p> 
                                <h4> <font family="Lucida Console" color="red"><%=request.getParameter("msg")%> </font> </h4>
                                </p> 
                                <%}%>
                                <center>
                                    <%if (request.getParameter("result") != null) {%>
                                    <p class="change_link">
                                        <a href="/SafeRideHome/files/SafeRideHomeReport.xls" target="_blank"> Download Report </a>
                                    </p>
                                    <br/>
                                    <br/>
                                    <br/>
                                    <%}%>
                                    <p> 
                                        <label for="fromDate" class="uname" > From Date </label> 
                                        <input type="date" id="fromDate" style="width: 150px" name="fromDate"  required="required" />
                                        <label for="toDate" class="uname" > To Date </label> 
                                        <input type="date" id="toDate" name="toDate"  style="width: 150px" required="required" />
                                    </p>
                                    <p> 
                                        <label for="fromTime" class="uname" > From Time </label> 
                                        <input type="time" id="fromTime" name="fromTime" style="width: 150px" required="required" />
                                        <label for="toTime" class="uname" > To Time </label> 
                                        <input type="time" id="toTime" name="toTime"  style="width: 150px" required="required" />
                                    </p>

                                    <p> 
                                        <label for="interval" class="uname" > From Time </label> 
                                        <select id="interval" name="interval" style="width: 150px" required="required" >
                                            <option value="30"> 30 MIN </option>
                                            <option value="60"> 1 HR </option>
                                            <option value="90">  1 HR 30 MIN </option>
                                            <option value="120"> 2 HRS</option>
                                            <option value="150"> 2 HRS 30 MIN </option>
                                        </select>
                                    </p>
                                    <p class="login button" > 
                                        <input type="submit" value="Get Report" />  &nbsp;&nbsp;&nbsp;<input type="Reset" value="Reset" /> 
                                    </p>
                                </center>
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
