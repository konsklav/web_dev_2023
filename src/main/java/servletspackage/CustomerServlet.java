package servletspackage;

import helperclasses.ServletHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.Customers;
import java.io.IOException;

@WebServlet(name = "CustomerServlet", value = "/customer-servlet")
public class CustomerServlet extends HttpServlet {
    Customers cu = null;
    String postMode = null;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cu = cu == null ? (Customers) request.getSession().getAttribute("user") : cu;

        // Gets the option the user selected in the menu
        String selectedOption = request.getParameter("option");

        // Sets the appropriate dynamic html code (using the ServletHelper Class) based on that option and stores it in the dynamicContent request attribute.
        // Also calls the logout method of the Users class in the appropriate case
        switch (selectedOption) {
            case "home":
                request.setAttribute("dynamicContent", ServletHelper.welcomeHtml(cu.getName()));
                break;
            case "view_all_films":
                request.setAttribute("dynamicContent", ServletHelper.viewAllFilms());
                break;
            case "view_all_provoles":
                request.setAttribute("dynamicContent", ServletHelper.viewAllProvoles());
                break;
            case "make_reservation":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.makeReservation());
                break;
            case "show_reservation_history":
                request.setAttribute("dynamicContent", ServletHelper.viewReservationHistory(cu));
                break;
            case "logout":
                cu.logout(request, response);
                return;
        }
        request.getRequestDispatcher("CustomerPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If the user is redirected from LoginPage.jsp, do the following and return:
        if (request.getAttribute("from-login") != null && (boolean) request.getAttribute("from-login")) {
            cu = cu == null ? (Customers) request.getSession().getAttribute("user") : cu;
            request.setAttribute("from-login", false);
            request.setAttribute("dynamicContent", ServletHelper.welcomeHtml(cu.getName()));
            request.getRequestDispatcher("CustomerPage.jsp").forward(request, response);
            return;
        }

        // Else, based on the postMode attribute, execute the appropriate methods
        if ("make_reservation".equals(postMode)) {
            handleMakeReservation(request, response);
        }
    }

    private void handleMakeReservation(HttpServletRequest request, HttpServletResponse response) {
        // 1 -> Get the POST parameters
        int provoliId = Integer.parseInt(request.getParameter("screeningid"));
        int nrOfSeats = Integer.parseInt(request.getParameter("numberofseats"));

        // 2 -> Call DB function through customer object
        boolean succeeded = cu.makeReservation(provoliId, nrOfSeats);
        String status = ServletHelper
                .generateStatusText(succeeded, ServletHelper.AdminAction.INSERT, "reservation", cu.getId());

        // 3 -> Send user back to CustomerPage.jsp?option=make_reservation with status of success or failure
        request.setAttribute("dynamicContent", ServletHelper.makeReservation() + status);
        try {
            request.getRequestDispatcher("CustomerPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}