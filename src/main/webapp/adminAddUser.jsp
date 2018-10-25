<%-- 
    Document   : adminAddUser
    Created on : Jun 10, 2013, 2:16:30 PM
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Safe Ride Home - Add User</title>
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
                            <form action="safeRideHomeController?requestFrom=signup&action=addUser" method="post" autocomplete="off"> 
                                <h1>Admin Add User</h1> 
                                <%if (request.getParameter("msg") != null) {%>   
                                <p> 
                                <h4> <font family="Lucida Console" color="red"><%=request.getParameter("msg")%> </font> </h4>
                                </p> 
                                <%}%>
                                <p> 
                                    <!--<label for="919#" class="uname" > 919# </label> -->
                                    <input id="userId" name="userId" required="required" type="text" placeholder="919#/username"/>
                                </p>

                                <p> 
                                    <!--<label for="name" class="uname" data-icon="p"> Name </label>-->
                                    <input id="name" name="name" required="required" type="text" placeholder="Lastname,Firstname Middlename" /> 
                                </p>
                                <p> 
                                    <!--<label for="phone" class="uname" data-icon="p"> Phone </label>-->
                                    <input id="phone" name="phone" required="required" type="text" placeholder="Phone" /> 
                                </p>
                                <p> 
                                    <!--<label for="email" class="uname" data-icon="p"> Smail </label> -->
                                    <input id="email" name="email" required="required" type="email" placeholder="University Email" /> 
                                </p>
                                <select width:12px name="userType">
                                    <option value="userType"> User Type </option>
                                    <option value="ADMIN"> Admin </option>
                                    <option value="DISPATCHER"> Dispatcher </option>
                                    <option value="DRIVER"> Driver </option>
                                    <option value="STUDENT"> Student </option>
                                </select>
                                </p>
                                <p class="login button" align="center"> 
                                    <input type="button" size="100%" value="Back" onclick="goBack()"/> &nbsp;&nbsp; <input type="submit" size="100%"  value="Add User" />  &nbsp;&nbsp;<input type="Reset" size="20px" value="Reset" /> 
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
                                        function goBack() {
                                            document.forms[0].method = "POST";
                                            document.forms[0].action = "adminHome.jsp";
                                            document.forms[0].submit();
                                        }

        </script>
</html>
