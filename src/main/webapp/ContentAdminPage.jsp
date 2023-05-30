<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="usersmodelpackage.ContentAdmins" %>
<!-- Gets the user created in the LoginServlet.java and casts it into a ContentAdmin object to be used in the page. -->
<% ContentAdmins ca = (ContentAdmins)request.getSession().getAttribute("user");%>

<html>
<head>
    <link rel="stylesheet" href="DynamicPageStyle.css">
    <meta charset="ISO-8859-1">
</head>
<body>

<div class="area">
    <!-- Dynamic Content Area -->
    ${requestScope.dynamicContent}
</div>
<nav class="main-menu">
    <ul>
        <li>
            <a href="content-admin-servlet?option=home">
                <i class="fa fa-home fa-2x"></i>
                <span class="nav-text">
                           Home
                </span>
            </a>
        </li>
        <li>
            <a href="content-admin-servlet?option=see_all_films">
                <i class="fa fa-film fa-2x"></i>
                <span class="nav-text">
                            See all available films
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="content-admin-servlet?option=add_new_film">
                <i class="fa fa-plus fa-2x"></i>
                <span class="nav-text">
                            Add a new film
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="content-admin-servlet?option=assign_film">
                <i class="fa fa-globe fa-2x"></i>
                <span class="nav-text">
                            Assign film to a screening hall and projection time
                </span>
            </a>
        </li>
        <!--
        <li class="has-subnav">
            <a href="#">
                <i class="fa fa-camera-retro fa-2x"></i>
                <span class="nav-text">
                            Global Surveyors
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
        -->
    </ul>
    <ul class="logout">
        <li>
            <a href="content-admin-servlet?option=logout">
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