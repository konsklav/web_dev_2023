<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% if (request.getSession().getAttribute("user") == null && request.getAttribute("from-login") == null) {
    response.sendRedirect("LoginPage.jsp");
}%>
<html>
<head>
    <title>Admin Page</title>
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
            <a href="admin-servlet?option=home">
                <i class="fa fa-home fa-2x"></i>
                <span class="nav-text">
                           Home
                </span>
            </a>
        </li>
        <li>
            <a href="admin-servlet?option=add_content_admin">
                <i class="fa fa-plus fa-2x"></i>
                <span class="nav-text">
                           Add Content Admin
                </span>
            </a>
        </li>
        <li>
            <a href="admin-servlet?option=remove_content_admin">
                <i class="fa fa-minus fa-2x"></i>
                <span class="nav-text">
                           Remove Content Admin
                </span>
            </a>
        </li>
    </ul>
    <ul class="logout">
        <li>
            <a href="admin-servlet?option=logout">
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
