package servletspackage;

import cinemamodelpackage.Films;
import helperclasses.ServletHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.ContentAdmins;
import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

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
        ca = ca == null ? (ContentAdmins)request.getSession().getAttribute("user") : ca;

        //Gets the option the user selected in the menu
        String selectedOption = request.getParameter("option");

        //Sets the appropriate dynamic html code (using the ServletHelper Class) based on that option and stores it in the dynamicContent request attribute
        switch (selectedOption){
            case "home":
                request.setAttribute("dynamicContent", ServletHelper.welcomeHtml(ca.getName()));
                break;
            case "view_all_films":
                request.setAttribute("dynamicContent", ServletHelper.viewAllFilms());
                break;
            case "insert_film":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.insertNewFilm());
                break;
            case "add_provoli":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.addProvoli());
                break;
            case "logout":
                //Code to be implemented in exercise 3
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
            request.setAttribute("dynamicContent", ServletHelper.welcomeHtml(ca.getName()));
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
            return;
        }

        //Else, based on the postMode attribute, execute the appropriate methods
        switch (postMode) {
            case "insert_film":
                handleInsertNewFilm(request, response);
            case "add_provoli":
                handleAddProvoli(request, response);
        }
    }

    private void handleInsertNewFilm(HttpServletRequest request, HttpServletResponse response) {
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
        request.setAttribute("dynamicContent", ServletHelper.insertNewFilm());
        try {
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAddProvoli(HttpServletRequest request, HttpServletResponse response) {
        // 1 -> Get POST parameters
        int film_id = Integer.parseInt(request.getParameter("film"));
        int cinema_id = Integer.parseInt(request.getParameter("cinema"));
        LocalDateTime date_and_time = LocalDateTime.parse(request.getParameter("datetime"));

        // 2 -> Execute the createNewProvoli function of the ContentAdmins class
        try {
            ca.createNewProvoli(film_id, cinema_id, date_and_time);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        // 3 -> Go back to assignFilm's dynamic view
        request.setAttribute("dynamicContent", ServletHelper.addProvoli());

        try {
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
