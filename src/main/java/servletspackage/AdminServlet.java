package servletspackage;

import jakarta.servlet.RequestDispatcher;
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
        ad = ad == null ? (Admins) request.getSession().getAttribute("user") : ad;

        String selectedOption = request.getParameter("option");

        switch (selectedOption) {
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
        ad = ad == null ? (Admins) request.getSession().getAttribute("user") : ad;
        request.getRequestDispatcher("AdminPage.jsp").forward(request,response);
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
