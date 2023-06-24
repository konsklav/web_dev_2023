package servletspackage;

import helperclasses.ServletHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.Admins;
import usersmodelpackage.ContentAdmins;
import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/admin-servlet")
public class AdminServlet extends HttpServlet {
    Admins ad = null;
    String postMode = null;

    @Override
    public void init() throws ServletException { super.init(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ad = (Admins) request.getSession().getAttribute("user");

        // Gets the option the user selected in the menu
        String selectedOption = request.getParameter("option");

        // Sets the appropriate dynamic html code (using the ServletHelper Class) based on that option and stores it in the dynamicContent request attribute.
        // Also calls the logout method of the Users class in the appropriate case
        switch (selectedOption) {
            case "home":
                request.setAttribute("dynamicContent", ServletHelper.welcomeHtml(ad.getName()));
                break;
            case "add_content_admin":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.addContentAdmin());
                break;
            case "remove_content_admin":
                postMode = selectedOption;
                request.setAttribute("dynamicContent", ServletHelper.removeContentAdmin());
                break;
            case "logout":
                ad.logout(request, response);
                return;
        }
        request.getRequestDispatcher("AdminPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If the user is redirected from LoginPage.jsp, do the following and return:
        if (request.getAttribute("from-login") != null && (boolean) request.getAttribute("from-login")) {
            ad = (Admins) request.getSession().getAttribute("user");
            request.setAttribute("from-login", false);
            request.setAttribute("dynamicContent", ServletHelper.welcomeHtml(ad.getName()));
            request.getRequestDispatcher("AdminPage.jsp").forward(request, response);
            return;
        }

        // Else, based on the postMode attribute, execute the appropriate methods
        switch (postMode) {
            case "add_content_admin":
                handleAddContentAdmin(request, response);
                break;
            case "remove_content_admin":
                handleRemoveContentAdmin(request, response);
                break;
        }
    }

    private void handleAddContentAdmin(HttpServletRequest request, HttpServletResponse response) {
        // 1 -> Gets the parameters (name, username and password) off the request sent by the form in AdminPage.jsp
        String name = request.getParameter("fullname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2 -> Creates a new ContentAdmins object using the parameters from above
        ContentAdmins ca = new ContentAdmins(name, username, password);

        // 3 -> Create status HTML for display if the action succeeded or failed
        String status = ad.createContentAdmin(ca) ?
                "Successfully created ContentAdmin \"" + ca.getUsername() +"\"!" :
                "Failed to create ContentAdmin \"" + username + "\", check server logs for more info!";
        String statusHtml = "<h2>" + status + "</h2>";

        // 4 -> Sets the new dynamicContent (with the functions statusHtml) and redirects it to the AdminPage.jsp
        request.setAttribute("dynamicContent", ServletHelper.addContentAdmin() + statusHtml);
        try {
            request.getRequestDispatcher("AdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRemoveContentAdmin(HttpServletRequest request, HttpServletResponse response) {
        // 1 -> Gets the username parameter off the request sent by the form in AdminPage.jsp
        String username = request.getParameter("username");

        // 2 -> Create status HTML for display if the action succeeded or failed
        String status = ad.deleteContentAdmin(username) ?
                "Successfully removed ContentAdmin \"" + username + "\"!" :
                "Failed to remove ContentAdmin \"" +username + "\", check server logs for more info!";
        String statusHtml = "<h2>" + status + "</h2>";

        // 3 -> Sets the new dynamicContent (with the functions statusHtml) and redirects it to the AdminPage.jsp
        request.setAttribute("dynamicContent", ServletHelper.removeContentAdmin() + statusHtml);
        try {
            request.getRequestDispatcher("AdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
