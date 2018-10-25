<%-- 
    Document   : dispatcherRideRequests
    Created on : Apr 15, 2013, 10:35:01 AM
    Author     : Abhyudaya Reddy Gurram
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.nwmissouri.universitypolice.saferidehome.pojos.Ride"%>
<%@page import="edu.nwmissouri.universitypolice.saferidehome.pojos.User"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <!--<meta http-equiv="refresh" content="30" > -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Safe Ride Home-Ride Requests</title>
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
        <link href="css/footable-0.1.css" rel="stylesheet"/>
        <link href='http://fonts.googleapis.com/css?family=Droid+Sans' rel='stylesheet' type='text/css'>
        <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    </head>
    <body>
        <%if (session.getAttribute("user") == null) {
                request.getRequestDispatcher("/index.jsp?msg=Session Expired. Please login again.").forward(request, response);
            }%>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

        <!-- This code is taken from http://twitter.github.com/bootstrap/examples/hero.html -->
        <%
            ArrayList<Ride> ridesList;
            ArrayList<String> drivers = (ArrayList) request.getAttribute("drivers");
            ridesList = (ArrayList) request.getAttribute("ridesList");
            User user = (User) session.getAttribute("user");
            String homeUrl = "";
            if (user.getUserType().equalsIgnoreCase("DISPATCHER")) {
                homeUrl = "dispatcherHome.jsp";
            } else if (user.getUserType().equalsIgnoreCase("ADMIN")) {
                homeUrl = "adminHome.jsp";
            }
        %>

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
                            <a href="safeRideHomeController?requestFrom=rideList" > <img src="images/refresh.png" title="Refresh List" height="35" width="35"></a>
                            &nbsp;&nbsp;&nbsp;
                            <a href="<%=homeUrl%>" > <img src="images/home.gif" title="Home" height="35" width="35"></a>
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
                            <h1>                                
                                Ride Requests
                            </h1> 
                            <%if (request.getParameter("msg") != null && !"".equalsIgnoreCase(request.getParameter("msg")) && !"null".equalsIgnoreCase(request.getParameter("msg"))) {%>   
                            <p> 
                            <h4> <font family="Lucida Console" color="red"><%=request.getParameter("msg")%> </font> </h4>
                            </p> 
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
            <form method="POST"> 
                <div clas="row-fluid">
                    <div class="span12">
                        <table class="footable">
                            <thead>
                                <tr>
                                    <th data-hide="phone,tablet">Ride ID</th>
                                    <th>From</th>
                                    <th>To</th>
                                    <th data-class="expand">Status</th>
                                    <th data-hide="phone">Name</th>
                                    <th data-hide="phone">919#</th>
                                    <th data-hide="phone">Callback#</th>
                                    <th data-hide="phone">No. of riders</th>
                                    <th data-hide="phone,tablet">Driver</th>
                                    <th data-hide="phone,tablet">Comments</th>
                                    <th data-hide="phone,tablet">Save</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% int listSize = ridesList.size();
                                    for (int i = 0; i < listSize; i++) {
                                        Ride ride = ridesList.get(i);
                                        if (ride != null) {%>
                                <tr>
                                    <td><%=ride.getRideId()%></td>
                                    <td><%=ride.getFrom()%></td>								
                                    <td><%=ride.getTo()%></td>
                                    <td>
                                        <select width:5px id="status<%=i%>" name="status<%=i%>" style="width: 120px">
                                            <option value="OPEN" <%=(ride.getStatus().equalsIgnoreCase("OPEN")) ? "selected" : ""%> > Open </option>
                                            <option value="ASSIGNED" <%=(ride.getStatus().equalsIgnoreCase("ASSIGNED")) ? "selected" : ""%> > Assigned </option>
                                            <option value="PICKEDUP" <%=(ride.getStatus().equalsIgnoreCase("PICKEDUP")) ? "selected" : ""%> > Picked Up </option>
                                            <option value="DROPPEDOFF" <%=(ride.getStatus().equalsIgnoreCase("DROPPEDOFF")) ? "selected" : ""%> > Dropped Off </option>
                                            <option value="CANCELLED" <%=(ride.getStatus().equalsIgnoreCase("CANCELLED")) ? "selected" : ""%> > Canceled </option>
                                            <option value="NOANSWER" <%=(ride.getStatus().equalsIgnoreCase("NOANSWER")) ? "selected" : ""%> > No Answer </option>
                                        </select>
                                    </td>
                                    <td><%=ride.getRequestedBy().getName()%></td>
                                    <td><%=ride.getRequestedBy().getUserId()%></td>
                                    <td><%=ride.getCallbackNumber()%></td>
                                    <td>
                                        <input type="number" max="8" min="1" style="max-width: 80px" id="totalRiders<%=i%>" name="totalRiders<%=i%>" value="<%=ride.getNoOfRiders()%>" placeholder="# of Riders"/>  
                                    </td>
                                    <td>
                                        <!--<textarea rows="4" id="919Numbers" name="919Numbers" placeholder="919#s seperated by ';'">  </textarea> -->
                                        <select style="width:80px" id="driver<%=i%>" name="driver<%=i%>" >
                                            <option value="null"> Select </option>
                                            <%
                                                if (drivers != null) {
                                                    for (String driver : drivers) {
                                            %>
                                            <option value ="<%=driver%>" 
                                                    <%
                                                        if (ride.getDriver() != null && ride.getDriver().equals(driver)) {
                                                    %>
                                                    selected
                                                    <%}%> > <%=driver%>
                                            </option>
                                            <%
                                                    }
                                                }
                                                if (ride.getDriver() != null && !"null".equalsIgnoreCase(ride.getDriver()) && !"".equalsIgnoreCase(ride.getDriver())) {
                                            %>
                                            <option value ="<%=ride.getDriver()%>" selected> <%=ride.getDriver()%> </option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="text"  id="comments<%=i%>" name="comments<%=i%>" value="<%=ride.getDriverComments()%>" placeholder="Comments"/>
                                        <!--<textarea rows="4" id="comments" name="comments" placeholder="Comments">  </textarea> -->
                                    </td>
                                    <td>
                                        <input type="button" value="Save" onclick="updateRide('<%=ride.getRideId()%>', '<%=i%>')"/>
                                    </td>
                                </tr>
                                <%}
                                    }%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </form>
        </div>
    </div> <!-- /container -->

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.0.min.js"><\/script>')</script>
    <script src="js/vendor/bootstrap.min.js"></script>
    <script src="js/footable-0.1.js" type="text/javascript"></script>
    <script lang="JavaScript">
            function updateRide(rideId, rowNumber) {
                document.forms[0].method = "POST";
                document.forms[0].action = "safeRideHomeController?requestFrom=changeRideStatus&rideId=" + rideId + "&rowNum=" + rowNumber;
                document.forms[0].submit();
            }

    </script>
    <script type="text/javascript">
        $(function() {
            $('table').footable();
        });
    </script>
    <script src="js/main.js"></script>
</html>
