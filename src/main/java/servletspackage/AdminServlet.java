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
    Admins ad = null;

    @Override
    public void init() throws ServletException { super.init(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ad = ad == null ? (Admins) request.getSession().getAttribute("user") : ad;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ad = ad == null ? (Admins) request.getSession().getAttribute("user") : ad;
        request.getRequestDispatcher("AdminPage.jsp").forward(request,response);
    }
}
