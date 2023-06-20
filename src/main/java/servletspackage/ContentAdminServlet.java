package servletspackage;

import cinemamodelpackage.Films;
import cinemamodelpackage.Provoles;
import helperclasses.DbHelper;
import helperclasses.ServletHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.ContentAdmins;
import java.io.IOException;
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

        // Gets the option the user selected in the menu
        String selectedOption = request.getParameter("option");

        // Sets the appropriate dynamic html code (using the ServletHelper Class) based on that option and stores it in the dynamicContent request attribute.
        // Also calls the logout method of the Users class in the appropriate case
        switch (selectedOption){
            case "home":
                request.setAttribute("dynamicContent", ServletHelper.welcomeHtml(ca.getName()));
                break;
            case "view_all_films":
                request.setAttribute("dynamicContent", ServletHelper.viewAllFilms());
                break;
            case "view_all_provoles":
                request.setAttribute("dynamicContent", ServletHelper.viewAllProvoles());
                break;
            case "insert_film":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.insertNewFilm());
                break;
            case "edit_film":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.editFilm());
                break;
            case "remove_film":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.removeFilm());
                break;
            case "add_provoli":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.addProvoli());
                break;
            case "edit_provoli":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.editProvoli());
                break;
            case "remove_provoli":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.removeProvoli());
                break;
            case "logout":
                ca.logout(request, response);
                return;
        }

        // Forwards and redirects the "dynamic" request to the ContentAdminPage.jsp
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

        // Else, based on the postMode attribute, execute the appropriate methods
        switch (postMode) {
            case "insert_film":
                handleInsertNewFilm(request, response);
            case "edit_film":
                handleEditFilm(request, response);
            case "remove_film":
                handleRemoveFilm(request, response);
            case "add_provoli":
                handleAddProvoli(request, response);
            case "edit_provoli":
                handleEditProvoli(request, response);
            case "remove_provoli":
                handleRemoveProvoli(request, response);
            default:
                request.getRequestDispatcher("LoginPage.jsp").forward(request, response);
        }
    }
    private void handleAddProvoli(HttpServletRequest request, HttpServletResponse response) {
        // 1 -> Get POST parameters
        int film_id = Integer.parseInt(request.getParameter("filmid"));
        int cinema_id = Integer.parseInt(request.getParameter("cinemaid"));
        LocalDateTime date_and_time = LocalDateTime.parse(request.getParameter("dateandtime"));

        // 2 -> Execute the createNewProvoli function of the ContentAdmins class
        Provoles provoli = new Provoles(DbHelper.getFilm(film_id), DbHelper.getCinema(cinema_id), date_and_time);
        boolean succeeded = ca.insertProvoli(provoli);
        String status = ServletHelper
                .generateStatusText(succeeded, ServletHelper.AdminAction.INSERT, "screening", -1);

        // 3 -> Go back to assignFilm's dynamic view
        request.setAttribute("dynamicContent", ServletHelper.addProvoli() + status);

        try {
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRemoveProvoli(HttpServletRequest request, HttpServletResponse response) {
        int provoliId = Integer.parseInt(request.getParameter("screeningid"));

        boolean succeeded = ca.deleteProvoli(provoliId);
        String status = ServletHelper
                .generateStatusText(succeeded, ServletHelper.AdminAction.DELETE, "screening", provoliId);

        request.setAttribute("dynamicContent", ServletHelper.removeProvoli() + status);
        try {
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditProvoli(HttpServletRequest request, HttpServletResponse response) {
        int provoliId = Integer.parseInt(request.getParameter("existingscreeningid"));
        int filmId = Integer.parseInt(request.getParameter("filmid"));
        int cinemaId = Integer.parseInt(request.getParameter("cinemaid"));
        LocalDateTime start_date = LocalDateTime.parse(request.getParameter("dateandtime"));

        boolean succeeded = ca.updateProvoli(provoliId, filmId, cinemaId, start_date);
        String status = ServletHelper
                .generateStatusText(succeeded, ServletHelper.AdminAction.UPDATE, "screening", provoliId);

        request.setAttribute("dynamicContent", ServletHelper.editProvoli() + status);
        try {
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
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
        boolean succeeded = ca.insertFilm(film);
        String status = ServletHelper
                .generateStatusText(succeeded, ServletHelper.AdminAction.INSERT, "film", -1);

        // 4 -> Go back to insertNewFilm's dynamic view
        request.setAttribute("dynamicContent", ServletHelper.insertNewFilm() + status);
        try {
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRemoveFilm(HttpServletRequest request, HttpServletResponse response) {
        int filmId = Integer.parseInt(request.getParameter("filmid"));

        boolean succeeded = ca.deleteFilm(filmId);
        String status = ServletHelper
                .generateStatusText(succeeded, ServletHelper.AdminAction.DELETE, "film", filmId);

        request.setAttribute("dynamicContent", ServletHelper.removeFilm() + status);
        try {
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditFilm(HttpServletRequest request, HttpServletResponse response) {
        int filmId = Integer.parseInt(request.getParameter("existingfilmid"));
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        int duration = Integer.parseInt(request.getParameter("duration"));

        boolean succeeded = ca.updateFilm(filmId, title, category, description, duration);
        String status = ServletHelper
                .generateStatusText(succeeded, ServletHelper.AdminAction.UPDATE, "film", filmId);

        request.setAttribute("dynamicContent", ServletHelper.editFilm() + status);
        try{
            request.getRequestDispatcher("ContentAdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }
}
