package helperclasses;

import cinemamodelpackage.Cinemas;
import cinemamodelpackage.Films;
import cinemamodelpackage.Provoles;
import java.sql.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

//This is a helper class that establishes the connection with the db and implements the key functions that require connection and access to the db
public class DbHelper {
    private final static String url = "jdbc:postgresql://localhost:5432/web_dev_db";
    private final static String username = "postgres";
    private final static String password = "p21xxx";
    private static Connection conn = null;

    //Implements the connection with the db based on the credentials given above
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

    //Calls the connect() method, to connect to the db if the conn variable is null
    private static void connectIfNull() {
        if (conn == null) {
            connect();
        }
    }

    public static ResultSet getAllFilms() throws SQLException {
        connectIfNull();

        //Prepare SELECT statement and return the ResultSet
        String sql = "SELECT id, title, category, description, duration FROM Films;";
        Statement statement = conn.createStatement();

        return statement.executeQuery(sql);
    }

    //Searches the db for a user based on the credentials given as attributes
    public static ResultSet findUser(String username, String password) throws SQLException{
        connectIfNull();

        // 1 -> Prepare a SELECT statement with ? being the parameters for the credentials
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?;";
        PreparedStatement statement = conn.prepareStatement(sql);

        // 2 -> Set the parameters
        statement.setString(1, username);
        statement.setString(2, password);

        // 3 -> Execute/Run the SQL statement and return the resulting row
        return statement.executeQuery();
    }

    //Adds a film (given as the attribute) into the db
    public static boolean addNewFilm(Films film) throws SQLException {
        connectIfNull();

        // 1 -> Prepare an INSERT statement with ? being the parameters for the fields
        String sql = "INSERT INTO Films (title, category, description, duration) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);

        // 2 -> Set the parameters
        statement.setString(1, film.getFilmTitle());
        statement.setString(2, film.getFilmCategory());
        statement.setString(3, film.getFilmDescription());
        statement.setInt(4, (int) film.getFilmDuration().toSeconds());

        // 3 -> Return true if the row update count is greater than 0
        return statement.executeUpdate() > 0;
    }

    public static Cinemas getCinema(int cinema_id) throws SQLException {
        connectIfNull();

        // 1 -> Prepare a SELECT statement with ? being the parameter
        String sql = "SELECT * FROM Cinemas WHERE id = ?;";
        PreparedStatement statement = conn.prepareStatement(sql);

        // 2 -> Set the parameter
        statement.setInt(1, cinema_id);

        // 3 -> Search for the cinema, create the object and return it to the method call
        ResultSet queryResults = statement.executeQuery();
        if (queryResults.next()) {
            int id = queryResults.getInt(1);
            boolean is_3d = queryResults.getBoolean(2);
            int nr_of_seats = queryResults.getInt(3);

            return new Cinemas(id, is_3d, nr_of_seats);
        }
        // 4 -> Else return null.
        return null;
    }

    public static Films getFilm(int filmId) throws SQLException {
        connectIfNull();

        // 1 -> Prepare SELECT statement
        String sql = "SELECT * FROM Films WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);

        // 2 -> Set the parameter
        statement.setInt(1, filmId);

        // 3 -> Search for the film and create the object (IF FOUND)
        ResultSet queryResults = statement.executeQuery();
        if (queryResults.next()) {
            int id = queryResults.getInt(1);
            String title = queryResults.getString(2);
            String category = queryResults.getString(3);
            String description = queryResults.getString(4);
            int duration = queryResults.getInt(5);

            return new Films(id, title, category, description, Duration.ofSeconds(duration));
        }
        // 4 -> Else return null
        return null;
    }

    //Adds a provoli (given as the attribute) into the db
    public static boolean addProvoli(Provoles provoli) throws SQLException {
        connectIfNull();

        // 1 -> Prepare an INSERT statement with ? being the parameters for the fields
        String sql = "INSERT INTO Provoles (film, cinema, start_date, nr_of_reservations) VALUES (?, ?, ?, 0);";
        PreparedStatement statement = conn.prepareStatement(sql);

        // 2 -> Set the parameters
        statement.setInt(1, provoli.getProvoliFilm().getFilmId());
        statement.setInt(2, provoli.getProvoliCinema().getCinemaId());
        statement.setTimestamp(3, Timestamp.valueOf(provoli.getProvoliStartDate()));

        // 3-> Return true if the row update count is greater than 0
        return statement.executeUpdate() > 0;
    }
}