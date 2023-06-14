<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<html>
<head>
	<!-- Θα μπορουσε κιολας το title να γράφει το ονομα του Customer/Admin/CA -->
	<title>Customer Page</title>
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
			<a href="customer-servlet?option=home">
				<i class="fa fa-home fa-2x"></i>
				<span class="nav-text">
                           Home
                </span>
			</a>
		</li>
		<li>
			<a href="customer-servlet?option=view_all_films">
				<i class="fa fa-film fa-2x"></i>
				<span class="nav-text">
                            View all available films
                </span>
			</a>
		</li>
		<li class="has-subnav">
			<a href="customer-servlet?option=view_all_provoles">
				<i class="fa fa-plus fa-2x"></i>
				<span class="nav-text">
                            View all screenings
                </span>
			</a>
		</li>
		<li class="has-subnav">
			<a href="customer-servlet?option=make_reservation">
				<i class="fa fa-globe fa-2x"></i>
				<span class="nav-text">
                            Make a reservation
                </span>
			</a>
		</li>
		<li class="has-subnav">
			<a href="customer-servlet?option=show_reservation_history">
				<i class="fa fa-globe fa-2x"></i>
				<span class="nav-text">
                            Show reservation history
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