package servletspackage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    Connection connection;

    public void init(){
        connection = DbHelper.connect();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ResultSet loginResult = performLogin(username, password);

        // If the user enters incorrect credentials (the query returns null to the ResultSet), redirect them back to LoginPage.jsp and destroy this servlet
        if (loginResult == null) {
            response.sendRedirect(request.getContextPath() + "/LoginPage.jsp");
            this.destroy();
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

        // TEMPORARY
        // Every RequestDispatcher redirects to ContentAdminServlet as per the requirements of Exercise 2
        // In the future, the RequestDispatcher shall redirect to the proper servlet depending on the type of user
        switch (userType) {
            case "CU":
            case "CA":
            case "AD":
                ContentAdmins ca = new ContentAdmins(name, username, password);
                request.getSession().setAttribute("user", ca);
                break;
        }

        request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
    }


    private ResultSet performLogin(String username, String password) {
        Users user = new Users(username, password);
        try {
            ResultSet userQuery = user.login(connection);
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

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException ex) {

        }
    }
}
