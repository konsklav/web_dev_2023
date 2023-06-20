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

    // Calls the logout method of the ServletHelper class providing it with the request and response of the page it was triggered by
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletHelper.logout(request, response);
    }
}
