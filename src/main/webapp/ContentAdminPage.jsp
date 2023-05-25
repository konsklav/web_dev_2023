<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!-- Gets the user created in the LoginServlet.java and casts it into a ContentAdmin object to be used in the page. -->
<%@ page import="usersmodelpackage.ContentAdmins" %>
<% ContentAdmins ca = (ContentAdmins)request.getSession().getAttribute("user");%>

<html>
<head>
    <link rel="stylesheet" href="DynamicPageStyle.css">
    <meta charset="ISO-8859-1">
</head>
<body>

<div class="area">
    <h1> Welcome <%=ca.getName()%> </h1>
    <h2> <%=ca.getUsername()%> </h2>
    <h2> <%=ca.getPassword()%> </h2>
    <!-- Εδω μπαίνει το δυναμικό περιεχόμενο -->
</div>
<nav class="main-menu">
    <ul>
        <li>
            <a href="https://jbfarrow.com">
                <i class="fa fa-home fa-2x"></i>
                <span class="nav-text">
                           Community Dashboard
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="#">
                <i class="fa fa-globe fa-2x"></i>
                <span class="nav-text">
                            Global Surveyors
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="#">
                <i class="fa fa-plus fa-2x"></i>
                <span class="nav-text">
                            Group Hub Forums
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="#">
                <i class="fa fa-camera-retro fa-2x"></i>
                <span class="nav-text">
                            Survey Photos
                </span>
            </a>
        </li>
        <li>
            <a href="#">
                <i class="fa fa-film fa-2x"></i>
                <span class="nav-text">
                            Surveying Tutorials
                </span>
            </a>
        </li>
        <li>
            <a href="#">
                <i class="fa fa-book fa-2x"></i>
                <span class="nav-text">
                           Surveying Jobs
                </span>
            </a>
        </li>
        <li>
            <a href="#">
                <i class="fa fa-cogs fa-2x"></i>
                <span class="nav-text">
                            Tools & Resources
                        </span>
            </a>
        </li>
        <li>
            <a href="#">
                <i class="fa fa-map-marker fa-2x"></i>
                <span class="nav-text">
                            Member Map
                </span>
            </a>
        </li>
        <li>
            <a href="#">
                <i class="fa fa-info fa-2x"></i>
                <span class="nav-text">
                            Documentation
                </span>
            </a>
        </li>
    </ul>
    <ul class="logout">
        <li>
            <a href="#">
                <i class="fa fa-power-off fa-2x"></i>
                <span class="nav-text">
                            Logout
                </span>
            </a>
        </li>
    </ul>
</nav>
</body>
</html>