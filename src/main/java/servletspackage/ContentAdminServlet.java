package servletspackage;

import cinemamodelpackage.Films;
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
import java.util.Objects;

@WebServlet(name = "contentAdminServlet", value = "/content-admin-servlet")
public class ContentAdminServlet extends HttpServlet {

    ContentAdmins ca = null;
    String postMode = null;

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
            case "home":
                request.setAttribute("dynamicContent", "<div class=\"text\"> " + "<h1> Welcome, " + ca.getName() + "! </h1>\n" + "<h3> Choose an option in the menu on your left! </h3>" + " </div>");
                break;
            //
            case "view_all_films":
                request.setAttribute("dynamicContent", viewAllFilms());
                break;
            case "insert_new_film":
                request.setAttribute("dynamicContent", insertNewFilm());
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
        // If the user is redirected from LoginPage.jsp, do the following and return:
        if (request.getAttribute("from-login") != null && (boolean) request.getAttribute("from-login")) {
            ca = (ContentAdmins) request.getSession().getAttribute("user");
            request.setAttribute("from-login", false);
            request.setAttribute("dynamicContent", "<div class=\"text\"> " + "<h1> Welcome, " + ca.getName() + "! </h1>\n" + "<h3> Choose an option in the menu on your left! </h3>" + " </div>");
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
            return;
        }

        //Else, based on the postMode attribute, execute the appropriate methods
        switch (postMode) {
            case "insert_film":
                handleInsertNewFilm(request, response);
            case "assign_film":
                handleAssignFilm(request, response);
        }
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
            // 2 -> Gets the films through the DbHelper class and adds them in a ResultSet object
            ResultSet filmResults = DbHelper.getAllFilms();

            // 3 -> For each row obtained, get the data and create an HTML table row (<tr>)
            while (filmResults.next()) {    //If there are movies in the ResultSet --> continue
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

        // 4 -> Close the <table> tag and return the html string
        return html + "</table>";
    }

    private String insertNewFilm() {
        postMode = "insert_film";

        // Create form with text fields: Title, Category, Description, Duration
        String html =
                "<form class='insert_film_form' action=\"content-admin-servlet\" method=\"post\">\n" +
                "<label for=\"title\"> Title </label>\n" + "<input type=\"text\" id=\"title\" name=\"title\"><br>\n" +
                    "<label for=\"category\"> Category </label>\n" + "<input type=\"text\" id=\"category\" name=\"category\"><br>\n" +
                    "<label for=\"description\"> Description </label>\n" + "<input type=\"text\" id=\"description\" name=\"description\"><br>\n" +
                    "<label for=\"duration\"> Duration </label>\n" + "<input type=\"text\" id=\"duration\" name=\"duration\"><br><br>\n" +
                    "<input type=\"submit\" value=\"Submit\">" +
                "</form>";
        return html;
    }

    private void handleInsertNewFilm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1 -> Get POST parameters
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        int duration = Integer.parseInt(request.getParameter("duration"));

        // 2 -> Create Films object
        Films film = new Films();
        film.setFilmTitle(title);
        film.setFilmCategory(category);
        film.setFilmDescription(description);
        film.setFilmDuration(Duration.ofSeconds(duration));

        // 3 -> Insert Films object into DB
        try {
            ca.insertFilm(film);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 4 -> Go back to insertNewFilm's dynamic view
        request.setAttribute("dynamicContent", insertNewFilm());
        request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
    }

    private String assignFilm() {
        postMode = "assign_film";
        //Code to be implemented
        String html = "html code!";
        return html;
    }

    private void handleAssignFilm(HttpServletRequest request, HttpServletResponse response) {
        //Code to be implemented
    }
}
