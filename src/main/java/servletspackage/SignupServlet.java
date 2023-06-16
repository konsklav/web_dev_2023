package servletspackage;

import helperclasses.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

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
        Random r1 = new Random();
        int length = r1.nextInt(5, 50);
        String salt = generateSalt(length);

        // Hashes the password using the hashPasswordMethod of the ServletHelper class
        String hashedPassword = ServletHelper.hashPassword(password, salt);

        // Adds the new user (customer) in the db using the addCustomer method of the DbHelper class
        DbHelper.addCustomer(full_name, username, salt, hashedPassword);
    }

    // Generates random salt based on the length provided and returns it to the method call
    private String generateSalt(int length){
        String salt = null;

        // Generates random String
        StringBuilder sb = new StringBuilder(length);
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random r2 = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = r2.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        salt = sb.toString();
        return salt;
    }
}