<%-- 
    Document   : forgotPassword
    Created on : Jun 10, 2013, 2:16:30 PM
    Author     : Abhyudaya Reddy Gurram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Safe Ride Home - Forgot Password</title>
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
                            <a href="index.jsp" > <img src="images/home.gif" title="Home" height="35" width="35"></a>
                        </td>
                    </tr>
                </table>
            </div>
            <div clas="row-fluid">
                <div class="hero">
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form action="safeRideHomeController?requestFrom=forgotPassword" method="post" autocomplete="off"> 
                                <h1>Forgot Password</h1> 
                                <%if (request.getParameter("msg") != null) {%>   
                                <h4> <font family="Lucida Console" color="red">
                                    <p> 
                                        <%=request.getParameter("msg")%>  
                                    </p>
                                    </font> </h4>
                                    <%}%>
                                <div id="invalidUsernameMessage">
                                    <p> 
                                    <h4> <font family="Lucida Console" color="red"> Invalid username/919# </font> </h4>
                                    </p> 
                                </div>
                                <p> 
                                    <!--<label for="919#" class="uname" > 919# </label> -->
                                    <input id="userId" name="userId" required="required" onfocus="clearMessage()" onblur="validateUserId()" type="text" placeholder="username/919#"/>
                                </p>
                                <p> 
                                    <!--<label for="phone" class="uname" data-icon="p"> Phone </label>-->
                                    <input id="phone" name="phone" required="required" type="text" placeholder="Phone" /> 
                                </p>
                                <p> 
                                    <!--<label for="email" class="uname" data-icon="p"> Smail </label> -->
                                    <input type="email" id="email" name="email" required="required" type="text" placeholder="University Email" /> 
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
                                        function createRequest() {
                                            try {
                                                request = new XMLHttpRequest();
                                            } catch (tryMS) {
                                                try {
                                                    request = new ActiveXObject("Msxml2.XMLHTTP");
                                                } catch (otherMS) {
                                                    try {
                                                        request = new ActiveXObject("Microsoft.XMLHTTP");
                                                    } catch (failed) {
                                                        request = null;
                                                    }
                                                }
                                            }
                                            return request;
                                        }

                                        function validateUserId() {
                                            if (document.getElementById("userId").text != '') {
                                                request = createRequest();
                                                if (request == null) {
                                                    alert("Unable to create request");
                                                    return;
                                                }

                                                var url = "safeRideHomeController?requestFrom=validateUserId&userId=" + document.getElementById("userId").value;
                                                request.open("GET", url, true);
                                                request.onreadystatechange = showInvalidUsernameMessage;
                                                request.send(null);
                                            }

                                        }
                                        window.onload = clearMessage();
                                        function clearMessage() {
                                            document.getElementById("invalidUsernameMessage").style.display = "none";
                                        }
                                        function showInvalidUsernameMessage() {
                                            if (request.readyState == 4) {
                                                if (request.status == 200) {
                                                    var resp = 0;
                                                    try {
                                                        resp = parseInt(request.responseText);
                                                    } catch (exp) {
                                                    }
                                                    if (resp === 1) {
                                                        document.getElementById("invalidUsernameMessage").style.display = "none";
                                                    } else {
                                                        document.getElementById("invalidUsernameMessage").style.display = "block";
                                                    }
                                                }
                                            }
                                        }

        </script>
</html>
