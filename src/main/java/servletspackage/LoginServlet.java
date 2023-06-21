package servletspackage;

import helperclasses.AddUserHelper;
import helperclasses.DbHelper;
import helperclasses.HashHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.Admins;
import usersmodelpackage.ContentAdmins;
import usersmodelpackage.Customers;
import usersmodelpackage.Users;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    public void init(){
        createDefaultAdminIfNotExists();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Users user = new Users(username, password);
        ResultSet loginResult = user.login();

        // If the user enters incorrect credentials (the query returns null to the ResultSet), redirect them back to LoginPage.jsp, displaying wrong input warning.
        if (loginResult == null) {
            request.setAttribute("wrongCredentialsWarning", "<h3 style=\"color: red; font-size: 15px;\"> !! Wrong username and/or password, please try again. </h3><br>");
            request.getRequestDispatcher("LoginPage.jsp").forward(request, response);
            return;
        }

        String userType = null;

        //Gets these fields off the db from the result of the query
        try {
            userType = loginResult.getString(6);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        //Sets a request attribute (from-login) to true, in order to indicate that the redirection to the servlet (and later to the page) "came" from the login page
        request.setAttribute("from-login", true);

        // Perform switch if and only if the string userType is NOT null
        // Each case does the following:
        // - Convert the Users object into the proper type (Customers, ContentAdmins, Admins)
        // - Store the converted object into the Session
        // - Dispatch the existing (request, response) pair to the proper servlet
        switch (Objects.requireNonNull(userType)) {
            case "CU":
                Customers cu = new Customers(user);
                request.getSession().setAttribute("user", cu);
                request.getRequestDispatcher("/customer-servlet").forward(request, response);
                break;
            case "AD":
                Admins ad = new Admins(user);
                request.getSession().setAttribute("user", ad);
                request.getRequestDispatcher("/admin-servlet").forward(request, response);
                break;
            case "CA":
                ContentAdmins ca = new ContentAdmins(user);
                request.getSession().setAttribute("user", ca);
                request.getRequestDispatcher("/content-admin-servlet").forward(request, response);
                break;
               }
    }

    // If a default admin doesn't already exist, then a new one is created with username and password "admin"
    // This is for the purposes of always having at least one administrator existing in the server's database
    private void createDefaultAdminIfNotExists()  {
        ResultSet qr = DbHelper.findUser("admin");
        try {
            if (!qr.next()) {
                Admins defaultAdmin = new Admins(1, "Default Administrator", "admin", "admin");
                if (AddUserHelper.addAdmin(defaultAdmin)) {
                    System.out.println("Created default admin!");
                } else {
                    System.out.println("Failed to create default admin!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
