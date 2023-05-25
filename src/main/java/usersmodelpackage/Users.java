package usersmodelpackage;

import java.sql.*;

public class Users {
    private String name;
    private String username;
    private String password;

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
    public ResultSet login(Connection connection) throws SQLException{
        // 1 - Prepare a SELECT statement with ? being the parameters for the credentials
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);

        // 2 - Set the parameters
        statement.setString(1, username);
        statement.setString(2, password);

        // 3 - Execute/Run the SQL statement and return the results
        return statement.executeQuery();
    }

    public void logout() {
        System.out.println(username + " has logged out!");
    }
}
