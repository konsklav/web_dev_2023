package servletspackage;

import helperclasses.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.Customers;
import java.io.IOException;

@WebServlet(name = "signupServlet", value = "/signup-servlet")
public class SignupServlet extends HttpServlet {

    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String full_name = request.getParameter("full_name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmedPassword = request.getParameter("confirm_password");

        // If the user enters two different passwords, redirect them back to SignupPage.jsp, displaying passwords not matching warning.
        if (!confirmedPassword.equals(password)) {
            request.setAttribute("wrongCredentialsWarning", "<h3 style=\"color: red; font-size: 15px;\"> !! Passwords not matching, please try again. </h3><br>");
            request.getRequestDispatcher("SignupPage.jsp").forward(request, response);
            return;
        }

        // Adds the new user (customer) in the db using the addCustomer methods of the DbHelper class and redirects to the LoginPage.jsp if the insertion was successful
        Customers cu = new Customers(full_name, username, password);
        if(AddUserHelper.addCustomer(cu)) {
            response.sendRedirect("LoginPage.jsp");
            request.removeAttribute("username");
            request.removeAttribute("password");
        }
    }
}