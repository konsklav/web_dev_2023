package servletspackage;

import helperclasses.DbHelper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.ContentAdmins;
import java.io.IOException;
import java.sql.*;
import java.time.Duration;

@WebServlet(name = "contentAdminServlet", value = "/content-admin-servlet")
public class ContentAdminServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContentAdmins ca = (ContentAdmins)request.getSession().getAttribute("user");

        //Gets the option the user selected in the menu
        String selectedOption = request.getParameter("option");

        //Sets the appropriate dynamic html code based on that option and stores it in the dynamicContent request attribute
        switch (selectedOption){
            //Πάνω από όλα τα request.setAttribute.. να βάλουμε τον κώδικα που χρειάζεται με τα queries κλπ (όπου χρειάζεται)
            case "home":
                request.setAttribute("dynamicContent", "<h1> Welcome " + ca.getName() + " </h1>\n" + "<h2> " + ca.getUsername() + " </h2>\n" + "<h2> " + ca.getPassword() + " </h2>");
                break;
            //
            case "see_all_films":
                request.setAttribute("dynamicContent", viewAllFilms());
                break;
            case "add_new_film":
                request.setAttribute("dynamicContent", addNewFilm());
                break;
            case "assign_film":
                request.setAttribute("dynamicContent", assignFilm());
                break;
            case "logout":
                //Code to be implemented in exercise 2
                break;
        }

        //Forwards and redirects the "dynamic" request to the ContentAdminPage.jsp
        request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContentAdmins ca = (ContentAdmins)request.getSession().getAttribute("user");

        request.setAttribute("dynamicContent", "<h1> Welcome " + ca.getName() + " </h1>\n" + "<h2> " + ca.getUsername() + " </h2>\n" + "<h2> " + ca.getPassword() + " </h2>");

        request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
    }


    private String viewAllFilms() {
        // 1 -> Initialize table HTML
        String html = "<table class='films_table'>\n";
        html += "<tr>\n" +
                "<th>Title</th>\n" +
                "<th>Category</th>\n" +
                "<th>Duration</th>" +
                "</tr>";
        try {
            // 2 -> Prepare and execute SQL SELECT Query from table Films
            ResultSet filmResults = DbHelper.getAllFilms();

            // 3 -> For each row obtained, get the data and create an
            //      HTML table row (<tr>)
            while (filmResults.next()) {
                String title = filmResults.getString(1);
                String category = filmResults.getString(2);
                String description = filmResults.getString(3);
                int duration = filmResults.getInt(4);
                Duration tempDuration = Duration.ofSeconds(duration);
                String formattedDuration = String.format("%d:%02d:%02d",
                        tempDuration.toHours(),
                        tempDuration.toMinutesPart(),
                        tempDuration.toSecondsPart());

                html += "<tr>\n" +
                        "<td>" + title + "</td>\n" +
                        "<td>" + category + "</td>\n" +
                        "<td>" + formattedDuration + "</td>\n" +
                        "</tr>";
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        // 4 -> Close the <table> tag and return :)
        return html + "</table>";
    }

    private String addNewFilm() {
        return null;
    }

    private String assignFilm() {
        return null;
    }

}
