package usersmodelpackage;

import helperclasses.DbHelper;
import helperclasses.ServletHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class Users {
    protected String name;
    protected String username;
    protected String password;

    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Accesses DB and queries the "Users" table for the (username, password) combination
    public ResultSet login() {
        return DbHelper.findUser(username);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1 -> Invalidates the session
        request.getSession().invalidate();

        // 2 -> Clears the cache, setting different headers for compatibility with all HTTP versions and caching mechanisms
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.

        // 3 -> Redirects back to the Login Page upon successful logout
        response.sendRedirect("LoginPage.jsp");
    }
}
