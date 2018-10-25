<%-- 
    Document   : adminManageUsers
    Created on : Apr 15, 2013, 10:35:01 AM
    Author     : Abhyudaya Reddy Gurram
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.nwmissouri.universitypolice.saferidehome.pojos.User"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Manage Users</title>
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
            ArrayList<User> users;
            users = (ArrayList) request.getAttribute("users");
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
                            <a href="adminHome.jsp" > <img src="images/home.gif" title="Go Home" height="35" width="35"></a>
                            &nbsp;&nbsp;&nbsp;
                            <a href="safeRideHomeController?requestFrom=logout" > <img title="Logout" src="images/logout.gif" height="33" width="33"></a> 
                        </td>
                    </tr>
                </table>
            </div>
            <form method="POST"> 
                <div clas="row-fluid">
                    <div class="hero">
                        <div id="wrapper">
                            <div id="login" class="animate form">
                                <h1>Manage Users</h1> 
                            </div>
                        </div>
                    </div>
                </div>
                <div clas="row-fluid">
                    <div class="span12">
                        <table class="footable">
                            <thead>
                                <tr>
                                    <th>User ID</th>
                                    <th data-hide="phone,tablet">Name</th>
                                    <th data-hide="phone">User Type</th>
                                    <th data-hide="phone,tablet">Email</th>
                                    <th>Status</th>
                                    <th data-hide="phone">Save</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% int listLen = users.size();
                                    for (int i = 0; i < listLen; i++) {
                                        User user = users.get(i);
                                        if (user != null) {%>
                                <tr>
                                    <td><%=user.getUserId()%></td>
                                    <td><%=user.getName()%></td>								
                                    <td><%=user.getUserType()%></td>
                                    <td><%=user.getEmailId()%></td>
                                    <td>
                                        <input type="hidden" name="prevStatus<%=i%>" id="prevStatus<%=i%>" value="<%=user.getAccountStatus()%>">
                                        <select width:5px name="status<%=i%>" id="status<%=i%>">
                                            <option value="Registered" <%=(user.getAccountStatus().equalsIgnoreCase("REGISTERED")) ? "selected" : ""%> > Registered </option>
                                            <option value="Active" <%=(user.getAccountStatus().equalsIgnoreCase("ACTIVE")) ? "selected" : ""%> > Active </option>
                                            <option value="Blocked" <%=(user.getAccountStatus().equalsIgnoreCase("BLOCKED")) ? "selected" : ""%> > Blocked </option>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="button" value="Save" onClick="updateUserStatus(<%=user.getUserId()%>, 'status<%=i%>')" />
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
    <script type="text/javascript">
                                            $(function() {
                                                $('table').footable();
                                            });
    </script>
    <script lang="JavaScript">
        function updateUserStatus(userId, statusId) {
            document.forms[0].method = "POST";
            document.forms[0].action = "safeRideHomeController?requestFrom=adminManageUsers&userId=" + userId + "&status=" + document.getElementById(statusId).value;
            document.forms[0].submit();
        }

    </script>
    <script src="js/main.js"></script>
</html>
