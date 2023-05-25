package servletspackage;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.Users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    Connection connection;

    public void init(){
        //connection =
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Users user = new Users(username, password);
        //user.login();
        /*response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + username + ", " + password + "</h1>");
        out.println("</body></html>");*/

        response.sendRedirect(request.getContextPath() + "/ContentAdminPage.jsp");
        //Να φτιάξω το login στο Users.java με τα σωστά queries για τη βάση (τόσο για το login όσο και για το signup)
        // και μετά να συνδέομαι με την άλλη σελίδα jsp (dynamic) περνόντας σαν παράμετρο το είδος χρήστη.
    }

    public void destroy() {
    }
}
