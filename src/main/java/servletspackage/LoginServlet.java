package servletspackage;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.Users;
import helperclasses.DbHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    Connection connection;

    public void init(){
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        connection = DbHelper.connect();

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        out.println("<h1>test</h1>");

        Users user = new Users(username, password);
        try {
            if (user.login(connection)) {
                out.println("<h1> Successfully logged in! </h1>");
            } else {
                out.println("<h1> Wrong username or password! </h1>");
            };
        } catch (SQLException ex) {
            out.println("<h1>" + ex.getMessage() + "</h1>");
        }

        out.println("<br><br>");
        out.println("<h1>" + user.getUsername() + ", " + user.getPassword() + "</h1>");

        out.println("</body></html>");

//        response.sendRedirect(request.getContextPath() + "/ContentAdminPage.jsp");
        //Να φτιάξω το login στο Users.java με τα σωστά queries για τη βάση (τόσο για το login όσο και για το signup)
        // και μετά να συνδέομαι με την άλλη σελίδα jsp (dynamic) περνόντας σαν παράμετρο το είδος χρήστη.
    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException ex) {

        }
    }
}
