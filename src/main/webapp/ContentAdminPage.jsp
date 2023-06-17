<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Content Admin Page</title>
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
            <a href="content-admin-servlet?option=view_all_films">
                <i class="fa fa-film fa-2x"></i>
                <span class="nav-text">
                            View all available films
                </span>
            </a>
        </li>
        <li>
            <a href="content-admin-servlet?option=view_all_provoles">
                <i class="fa fa-video-camera fa-2x"></i>
                <span class="nav-text">
                            View all available screenings
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="content-admin-servlet?option=insert_film">
                <i class="fa fa-plus fa-2x"></i>
                <span class="nav-text">
                            Add a new film
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="content-admin-servlet?option=edit_film">
                <i class="fa fa-edit fa-2x"></i>
                <span class="nav-text">
                            Edit an existing film
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="content-admin-servlet?option=remove_film">
                <i class="fa fa-minus fa-2x"></i>
                <span class="nav-text">
                            Remove a film
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="content-admin-servlet?option=add_provoli">
                <i class="fa fa-plus fa-2x"></i>
                <span class="nav-text">
                            Add a new screening
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="content-admin-servlet?option=edit_provoli">
                <i class="fa fa-edit fa-2x"></i>
                <span class="nav-text">
                            Edit an existing screening
                </span>
            </a>
        </li>
        <li class="has-subnav">
            <a href="content-admin-servlet?option=remove_provoli">
                <i class="fa fa-minus fa-2x"></i>
                <span class="nav-text">
                            Remove a screening
                </span>
            </a>
        </li>
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