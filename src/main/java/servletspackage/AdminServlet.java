package servletspackage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.Admins;

import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/admin-servlet")
public class AdminServlet extends HttpServlet {
    @Override
    public void init() throws ServletException { super.init(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admins ad = (Admins)request.getSession().getAttribute("user");

        //Gets the option the user selected in the menu
        String selectedOption = request.getParameter("option");

        //Sets the appropriate dynamic html code based on that option and stores it in the dynamicContent request attribute
        switch (selectedOption){
            //Πάνω από όλα τα request.setAttribute.. να βάλουμε τον κώδικα που χρειάζεται με τα queries κλπ (όπου χρειάζεται)
            case "home":
                request.setAttribute("dynamicContent", "<h1> Welcome " + ad.getName() + " </h1>\n" + "<h2> " + ad.getUsername() + " </h2>\n" + "<h2> " + ad.getPassword() + " </h2>");
                break;
            case "see_all_films":
                request.setAttribute("dynamicContent", "Εδώ θα μπει το δυναμικό HTML 1");
                break;
            case "add_new_film":
                request.setAttribute("dynamicContent", "Εδώ θα μπει το δυναμικό HTML 2");
                break;
            case "assign_film":
                request.setAttribute("dynamicContent", "Εδώ θα μπει το δυναμικό HTML 3");
                break;
            case "logout":
                //Εδώ θα μπει ο κώδικας για το logout, να χρησιμοποιήσουμε τη μέθοδο logout απο το Users.java
                break;
        }

        //Forwards and redirects the "dynamic" request to the ContentAdminPage.jsp
        request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admins ad = (Admins)request.getSession().getAttribute("user");

        request.setAttribute("dynamicContent", "<h1> Welcome " + ad.getName() + " </h1>\n" + "<h2> " + ad.getUsername() + " </h2>\n" + "<h2> " + ad.getPassword() + " </h2>");

        request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
    }
}
