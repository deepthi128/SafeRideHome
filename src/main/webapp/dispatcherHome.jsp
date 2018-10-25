<%-- 
    Document   : dispatcherHome
    Created on : Apr 15, 2013, 10:35:01 AM
    Author     : Abhyudaya Reddy Gurram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.nwmissouri.universitypolice.saferidehome.pojos.User"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Safe Ride Home - Dispatcher Home</title>
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

<%
    User user = (User) session.getAttribute("user");
%>
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
                                    <td> <img height="65" width="65" src="images/safeRideLogo.jpg" alt="Safe Ride Home"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
                                    <td><h2>Safe Ride Home</h2> </td>

                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <input class="checkbox" name ="serviceStatus" id ="serviceStatus"  type="checkbox" <%=session.getAttribute("serviceStatus") != null ? "checked" : ""%> onchange="changeServiveStatus()"> &nbsp;Service Active&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="safeRideHomeController?requestFrom=logout" > <img title="Logout" src="images/logout.gif" height="33" width="33"></a>
                    </td>
                </tr>
            </table>
        </div>
        <div clas="row-fluid">
            <div class="hero">
                <div id="wrapper">
                    <div id="login" class="animate form">
                        <form action="" method="post" autocomplete="on"> 
                            <center>
                                <h1>Dispatcher Home</h1> 
                                <%if (request.getParameter("msg") != null) {%>   
                                <p> 
                                <h4> <font family="Lucida Console" color="red"><%=request.getParameter("msg")%> </font> </h4>
                                </p> 
                                <%}%>
                                <h4><font color="blue" family="Lucida Console" >
                                    <br/>
                                    <p>
                                        <a href="scheduleRide.jsp" >Schedule a Ride</a>
                                    </p>

                                    <br/>
                                    <p >
                                        <a href="safeRideHomeController?requestFrom=rideList" >Manage Requests</a>
                                    </p>

                                    <br/>
                                    <p >
                                        <a href="dispatcherCheckRideStatus.jsp" >Check Ride Status</a>
                                    </p>
                                    <br/>

                                    <p >
                                        <a href="postMessage.jsp" >Make an announcement</a>
                                    </p>

                                    <br/>
                                    <p >
                                        <a href="safeRideHomeController?requestFrom=manageVehicles" >Manage Vehicles</a>
                                    </p>
                                    <br/>

                                    <p >
                                        <a href="editProfile.jsp" >Edit Profile</a>
                                    </p>
                                    </font> </h4>
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

                            function changeServiveStatus() {
                                request = createRequest();

                                if (request == null) {
                                    alert("Unable to create request");
                                    return;
                                }

                                var url = "safeRideHomeController?requestFrom=setServiceStatus&status=" + document.getElementById("serviceStatus").checked;
                                request.open("GET", url, true);
                                request.onreadystatechange = dummy;
                                request.send(null);
                            }

                            function dummy() {

                            }

    </script>


</html>
