<%-- 
    Document   : postMessage
    Created on : 
    Author     : Abhyudaya Reddy Gurram
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.nwmissouri.universitypolice.saferidehome.pojos.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Safe Ride Home - Announcement</title>
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


    <%
        User user = (User) session.getAttribute("user");
        String homeUrl = "";
        String message = "";
        try{
            message = (String)getServletContext().getAttribute("message");
            if(message == null || "null".equalsIgnoreCase(message)){
                message = "";
            }
        }catch(Exception e){
            System.out.println(e);
        }
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
                        <form action="safeRideHomeController?requestFrom=postMessage" method="post" autocomplete="on"> 
                            <h1>
                                Make an announcement
                            </h1> 
                            <%if (request.getParameter("msg") != null) {%>   
                            <p> 
                            <h4> <font family="Lucida Console" color="red"><%=request.getParameter("msg")%> </font> </h4>
                            </p> 
                            <%}%>
                            <h4> <font color="blue"> Enter a message: &nbsp;&nbsp;&nbsp; </font></h4></h3>
                        <textarea id="message" name="message" rows="4" cols="300"><%=message%></textarea>
                            <p class="login button"> 
                                <input type="button" onclick="removeMessage()" value="Remove" /> 
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="submit" value="Announce" /> 
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
        function removeMessage(){
                document.forms[0].action = 'safeRideHomeController?requestFrom=removeMessage';
                document.forms[0].method = 'POST';
                document.forms[0].submit();
        }
        
    </script>
</html>
