package servletspackage;

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

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    public void init(){
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // !!!! Να φτιάξουμε από γραμμή 33 μέχρι 51 μόλις φτιάξι ο Tζώρτζης τη βάση
        ResultSet loginResult = performLogin(username, password);

        // If the user enters incorrect credentials (the query returns null to the ResultSet), redirect them back to LoginPage.jsp, displaying wrong input warning.
        if (loginResult == null) {
            request.setAttribute("wrongCredentialsWarning", "<h3 style=\"color: red; font-size: 15px;\"> !! Wrong username and/or password, please try again. </h3><br>");
            request.getRequestDispatcher("LoginPage.jsp").forward(request, response);
            return;
        }

        String userType = null;
        String name = null;

        //Gets these fields off the db from the result of the query
        try {
            name = loginResult.getString(2);
            userType = loginResult.getString(5);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        request.setAttribute("from-login", true);
        switch (userType) {
            case "CU":
                Customers cu = new Customers(name, username, password);
                request.getSession().setAttribute("user", cu);
                request.getRequestDispatcher("/customer-servlet").forward(request, response);
                break;
            case "AD":
                Admins ad = new Admins(name, username, password);
                request.getSession().setAttribute("user", ad);
                request.getRequestDispatcher("/admin-servlet").forward(request, response);
                break;
            case "CA":
                ContentAdmins ca = new ContentAdmins(name, username, password);
                request.getSession().setAttribute("user", ca);
                request.getRequestDispatcher("/content-admin-servlet").forward(request, response);
                break;
               }
    }

    // !!!! Να τη φτιάξουμε μόλις φτιάξι ο Tζώρτζης τη βάση
    //Creates a User object based on the username and password inserted and performs the login function. Returns null if the inserted credentials do not match with a users credentials in the db
    private ResultSet performLogin(String username, String password) {
        Users user = new Users(username, password);

        try {
            ResultSet userQuery = user.login();
            if (userQuery.next()) {     //If true, the query returned a result, thus the credentials validate through the db
                return userQuery;
            }
            else {
                return null;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
