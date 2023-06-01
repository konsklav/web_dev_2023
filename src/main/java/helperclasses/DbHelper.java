package helperclasses;

import cinemamodelpackage.Films;
import java.lang.annotation.Documented;
import java.sql.*;
import java.time.Duration;

//This is a helper class that establishes the connection with the db and implements the key functions that require connection and access to the db
public class DbHelper {
    private final static String url = "jdbc:postgresql://localhost:5432/web_dev_db";
    private final static String username = "postgres";
    private final static String password = "p21xxx";
    private static Connection conn = null;

    private static void connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        conn = connection;
    }

    private static void connectIfNull() {
        if (conn == null) {
            connect();
        }
    }

    public static ResultSet getAllFilms() throws SQLException {
        connectIfNull();

        String sql = "SELECT title, category, description, duration FROM Films;";
        Statement statement = conn.createStatement();
        return statement.executeQuery(sql);
    }

    public static ResultSet findUser(String username, String password) throws SQLException{
        connectIfNull();

        // 1 - Prepare a SELECT statement with ? being the parameters for the credentials
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?;";
        PreparedStatement statement = conn.prepareStatement(sql);

        // 2 - Set the parameters
        statement.setString(1, username);
        statement.setString(2, password);

        // 3 - Execute/Run the SQL statement and return the resulting row
        return statement.executeQuery();
    }

    public static boolean addNewFilm(Films film) throws SQLException {
        connectIfNull();

        String sql = "INSERT INTO Films (title, category, description, duration) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, film.getFilmTitle());
        statement.setString(2, film.getFilmCategory());
        statement.setString(3, film.getFilmDescription());
        statement.setInt(4, (int) film.getFilmDuration().toSeconds());

        // Return true if the row update count is greater than 0.
        return statement.executeUpdate() > 0;
    }
}
