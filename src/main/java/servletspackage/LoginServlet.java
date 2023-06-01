package servletspackage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import usersmodelpackage.Admins;
import usersmodelpackage.ContentAdmins;
import usersmodelpackage.Customers;
import usersmodelpackage.Users;
import helperclasses.DbHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CyclicBarrier;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    public void init(){
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ResultSet loginResult = performLogin(username, password);

        // If the user enters incorrect credentials (the query returns null to the ResultSet), redirect them back to LoginPage.jsp, displaying wrong input warning.
        if (loginResult == null) {
            request.setAttribute("wrongCredentialsWarning", "<h3 style=\"color: red;\"> !! Wrong username and/or password, please try again. </h3>");
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

        // TEMPORARY:
        // Every RequestDispatcher redirects to ContentAdminServlet as per the requirements of Exercise 2
        // In the future, the RequestDispatcher shall redirect to the proper servlet depending on the type of user
        /*switch (userType) {
            case "CU":
                Customers cu = new Customers(name, username, password);
                request.getSession().setAttribute("user", cu);
                request.getRequestDispatcher("/client-servlet").forward(request, response);
                break;
            case "CA":
                Admins ad = new Admins(name, username, password);
                request.getSession().setAttribute("user", ad);
                request.getRequestDispatcher("/admin-servlet").forward(request, response);
                break;
            case "AD":
                ContentAdmins ca = new ContentAdmins(name, username, password);
                request.getSession().setAttribute("user", ca);
                request.getRequestDispatcher("/content-admin-servlet").forward(request, response);
                break;
               }
         */
        ContentAdmins ca = new ContentAdmins(name, username, password);
        request.getSession().setAttribute("user", ca);
        request.getRequestDispatcher("/content-admin-servlet").forward(request, response);
    }

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
