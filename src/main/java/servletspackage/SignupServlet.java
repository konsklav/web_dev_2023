package servletspackage;

import helperclasses.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

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

        // Generates random salt using the generateSalt method and a random length value for it's length
        SecureRandom r1 = new SecureRandom();
        int length = r1.nextInt(45) + 5;
        String salt = ServletHelper.generateSalt(length);

        // Hashes the password using the hashPasswordMethod of the ServletHelper class
        String hashedPassword = ServletHelper.hashPassword(password, salt);

        // Adds the new user (customer) in the db using the addCustomer method of the DbHelper class
        DbHelper.addCustomer(full_name, username, salt, hashedPassword);
    }
}