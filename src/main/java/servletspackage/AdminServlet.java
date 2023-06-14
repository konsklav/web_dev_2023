package servletspackage;

import helperclasses.ServletHelper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usersmodelpackage.Admins;
import usersmodelpackage.ContentAdmins;

import java.io.IOException;
import java.rmi.server.ServerCloneException;

@WebServlet(name = "AdminServlet", value = "/admin-servlet")
public class AdminServlet extends HttpServlet {
    Admins ad = null;
    String postMode = null;

    @Override
    public void init() throws ServletException { super.init(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ad = ad == null ? (Admins) request.getSession().getAttribute("user") : ad;

        String selectedOption = request.getParameter("option");

        switch (selectedOption) {
            case "home":
                request.setAttribute("dynamicContent", ServletHelper.welcomeHtml(ad.getName()));
            case "add_content_admin":
                request.setAttribute("dynamicContent", addContentAdmin());
                break;
            case "remove_content_admin":
                request.setAttribute("dynamicContent", removeContentAdmin());
                break;
        }
        request.getRequestDispatcher("AdminPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("from-login") != null && (boolean) request.getAttribute("from-login")) {
            ad = ad == null ? (Admins) request.getSession().getAttribute("user") : ad;
            request.setAttribute("from-login", false);
            request.setAttribute("dynamicContent", ServletHelper.welcomeHtml(ad.getName()));
            request.getRequestDispatcher("AdminPage.jsp").forward(request, response);
            return;
        }

        switch (postMode) {
            case "add_content_admin":
                break;
            case "remove_content_admin":
                break;
        }
    }

    private void handleAddContentAdmin(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ContentAdmins ca = new ContentAdmins(name, username, password);
        String status = ad.createContentAdmin(ca) ?
                "Successfully created ContentAdmin \"" + ca.getUsername() +"\"!" :
                "Failed to create ContentAdmin \"" + username + "\", check server logs for more info!";
        String statusHtml = "<h2>" + status + "</h2>";

        request.setAttribute("dynamicContent", addContentAdmin() + statusHtml);
        try {
            request.getRequestDispatcher("AdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRemoveContentAdmin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");

        String status = ad.deleteContentAdmin(username) ?
                "Successfully removed ContentAdmin \"" + username + "\"!" :
                "Failed to remove ContentAdmin \"" +username + "\", check server logs for more info!";
        String statusHtml = "<h2>" + status + "</h2>";

        request.setAttribute("dynamicContent", removeContentAdmin() + statusHtml);
        try {
            request.getRequestDispatcher("AdminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private String addContentAdmin() {
        postMode = "add_content_admin";
        return "<form action=\"admin-servlet\" method=\"post\">" +
                "<label for=\"name\">Full Name: </label>" +
                "<input type=\"text\" id=\"name\" name=\"name\"><br>" +
                "<label for=\"username\">Username: </label>" +
                "<input type=\"text\" id=\"username\" name=\"username\"><br>" +
                "<label for=\"password\">Password: </label>" +
                "<input type=\"text\" id=\"password\" name=\"password\"><br>" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form>";
    }

    private String removeContentAdmin() {
        postMode = "remove_content_admin";
        return "<form action=\"admin-servlet\" method=\"post\">" +
                "<label for=\"username\"> Search by username: </label>" +
                "<input type=\"text\" id=\"username\" name=\"username\">" +
                "</form>";
    }
}
