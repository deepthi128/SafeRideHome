<%-- 
    Document   : signupSuccess
    Created on : Apr 15, 2013, 10:35:01 AM
    Author     : Abhyudaya Reddy Gurram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Success!</title>
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
            <div class="span12 title well">
                <table align="center">
                    <tr>
                        <td> <img height="80" width="80" src="images/safeRideLogo.jpg" alt="Safe Ride Home"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
                        <td><h1>Safe Ride Home</h1> </td>

                    </tr>
                </table>
            </div>
        </div>
        <div clas="row-fluid">
            <div class="hero">
                <div id="wrapper">
                    <div id="login" class="animate form">
                        <form action="index.jsp" method="post" autocomplete="on"> 
                            <h1>Success</h1> 
                            <%if (request.getParameter("msg") != null) {%>   
                            <p> 
                            <h3> <font family="Lucida Console" color="red"><%=request.getParameter("msg")%> </font> </h3>
                            </p> 
                            <%}%>

                            <h4> Signup successful. Please complete the verification process following the instructions in the email sent to you. </h4>
                            <p class="login button"> 
                                <input type="submit" value="Sign in" /> 
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
