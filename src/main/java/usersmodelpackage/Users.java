package usersmodelpackage;

import helperclasses.DbHelper;

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
    public ResultSet login() throws SQLException{
        return DbHelper.findUser(username, password);
    }

    public void logout() {
        System.out.println(username + " has logged out!");
    }
}
