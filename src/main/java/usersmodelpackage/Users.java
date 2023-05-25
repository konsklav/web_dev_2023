package usersmodelpackage;

import java.sql.*;

public class Users {
    private String name;
    private String username;
    private String password;

    public Users() {
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

    public void login() {
        System.out.println(username + " has logged in!");
    }

    public void logout() {
        System.out.println(username + " has logged out!");
    }
}
