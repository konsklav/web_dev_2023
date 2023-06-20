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
    public void init() throws ServletException { super.init(); }

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
            case "view_all_films":
                postMode = selectedOption;
                //!!! Να υλοποιήσουμε το τι θα κάνει
                break;
            case "view_all_provoles":
                postMode = selectedOption;
                //!!! Να υλοποιήσουμε το τι θα κάνει
                break;
            case "make_reservation":
                postMode = selectedOption;
                //!!! Να υλοποιήσουμε το τι θα κάνει
                break;
            case "show_reservation_history":
                postMode = selectedOption;
                //!!! Να υλοποιήσουμε το τι θα κάνει
                break;
            case "logout":
                cu.logout(request, response);
                break;
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
        switch (postMode) {
            case "view_all_films":
                //!!! Να υλοποιήσουμε το τι θα κάνει
                break;
            case "view_all_provoles":
                //!!! Να υλοποιήσουμε το τι θα κάνει
                break;
            case "make_reservation":
                //!!! Να υλοποιήσουμε το τι θα κάνει
                break;
            case "show_reservation_history":
                //!!! Να υλοποιήσουμε το τι θα κάνει
                break;
        }
    }

    // !!! Να υλοποιήσουμε τις μεθόδους
}