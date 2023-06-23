package usersmodelpackage;

import helperclasses.DbHelper;
import helperclasses.HashHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class Users {
    protected int id;
    protected String name;
    protected String username;
    protected String password;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Users(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId() {return id;}

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
        // 1 -> Query DB and get the User represented by 'username' (possible 0 row return if not found)
        ResultSet userQuery = DbHelper.findUser(username);
        try {
            if (userQuery.next()) {     //If true, the query returned a result, thus the credentials validate through the db
                String salt = userQuery.getString(4);   //Gets the stored salt from the db
                String storedHash = userQuery.getString(5); //Gets the stored hashedPassword from the db
                if (!storedHash.equals(HashHelper.hashPassword(password, salt))) {
                    // If passwords do NOT match, return null
                    return null;
                }
                // If the user was found in the DB AND the passwords match, return the ResultSet
                id = userQuery.getInt(1);
                name = userQuery.getString(2);
                return userQuery;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // If this block hits, a SQLException occurred.
        return null;
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
