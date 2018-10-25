<%-- 
    Document   : index
    Created on : Apr 15, 2013, 10:35:01 AM
    Author     : Abhyudaya Reddy Gurram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Safe Ride Home - Login</title>
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
    <div class="container">
        <div class="row-fluid">
            <div class="span12 title well">
                <table align="center">
                    <tr>
                        <td> <img height="65" width="65" src="images/safeRideLogo.jpg" alt="Safe Ride Home"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
                        <td><h2>Safe Ride Home</h2> </td>

                    </tr>
                </table>
            </div>
        </div>
        <div clas="row-fluid">
            <div class="hero">
                <div id="wrapper">
                    <div id="login" class="animate form">
                        <form action="safeRideHomeController?requestFrom=login" method="post" autocomplete="on"> 
                            <h1>Login</h1> 
                            <%if (request.getParameter("msg") != null) {%>   
                            <p> 
                            <h4> <font family="Lucida Console" color="red"><%=request.getParameter("msg")%> </font> </h4>
                            </p> 
                            <%}%>
                            
                            <%if (getServletContext().getAttribute("message") != null) {%>   
                            <p> 
                            <h4> <font family="Lucida Console" color="blue"><%=(String)getServletContext().getAttribute("message")%> </font> </h4>
                            </p> 
                            <%}%>
                            
                            <p> 
                                <!--     <label for="username" class="uname" data-icon="u" > Your email or username </label> -->
                                <input id="username" name="username" required="required" type="text"  placeholder="username/919#"/>
                            </p>
                            <p> 
                                <!--    <label for="password" class="youpasswd" data-icon="p"> Your password </label> -->
                                <input id="password" name="password" required="required" type="password" placeholder="password" /> 
                            </p>
                            <p class="login button"> 
                               <a href="forgotPassword.jsp" class="to_register">Forgot Password?</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <input type="submit" value="Login" /> 
                            </p>
                            <br/>
                            <p>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                Haven't registered yet?
                                <a href="studentSignup.jsp" >Register</a>
                            </p>
                            <br><br><br><br>
                            <center>
                            <p>
                                <a href="https://www.facebook.com/safe.rides.9" > <img src="images/facebook-1.png" title="Follow us on Facebook" height="50" width="50"></a>
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
