package helperclasses;

import cinemamodelpackage.Cinemas;
import cinemamodelpackage.Films;
import cinemamodelpackage.Provoles;
import usersmodelpackage.ContentAdmins;

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

    // Check for connection, prepare a statement and catch any SQLExceptions.
    private static PreparedStatement prepareSql(String sql) {
        PreparedStatement statement = null;
        if (conn == null) {
            connect();
        }
        try {
            statement = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("Something went wrong whilst preparing SQL statement");
            System.out.println("SQL: " + sql);
            e.printStackTrace();
        }

        return statement;
    }

    // Performs a query (SELECT) on an existing PreparedStatement with 0 or more parameters
    private static ResultSet query(PreparedStatement statement) {
        ResultSet results = null;
        try {
            results = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Something went wrong whilst trying to query SQL");
            System.out.println("Statement: " + statement);
            e.printStackTrace();
        }

        return results;
    }

    // Performs an update (INSERT, UPDATE, DELETE) on an existing PreparedStatement with 0 or more parameters
    private static int update(PreparedStatement statement) {
        int updateCount = Integer.MIN_VALUE;
        try {
            updateCount = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong whilst trying to update SQL");
            System.out.println("Statement: " + statement);
            e.printStackTrace();
        }

        return updateCount;
    }


    public static ResultSet getAllFilms() {
        //Prepare SELECT statement and return the ResultSet
        String sql = "SELECT id, title, category, description, duration FROM Films;";
        PreparedStatement statement = prepareSql(sql);
        return query(statement);
    }

    //Searches the db for a user based on the credentials given as attributes
    public static ResultSet findUser(String username, String password) {
        // 1 -> Prepare a SELECT statement with ? being the parameters for the credentials
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?;";
        PreparedStatement statement = prepareSql(sql);

        // 2 -> Set the parameters
        try {
            statement.setString(1, username);
            statement.setString(2, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 3 -> Execute/Run the SQL statement and return the resulting row
        return query(statement);
    }

    //Adds a film (given as the attribute) into the db
    public static boolean addNewFilm(Films film) {
        // 1 -> Prepare an INSERT statement with ? being the parameters for the fields
        String sql = "INSERT INTO Films (title, category, description, duration) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = prepareSql(sql);

        // 2 -> Set the parameters
        try {
            statement.setString(1, film.getFilmTitle());
            statement.setString(2, film.getFilmCategory());
            statement.setString(3, film.getFilmDescription());
            statement.setInt(4, (int) film.getFilmDuration().toSeconds());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 3 -> Return true if the row update count is greater than 0
        return update(statement) > 0;
    }

    public static Cinemas getCinema(int cinema_id) {
        // 1 -> Prepare a SELECT statement with ? being the parameter
        String sql = "SELECT * FROM Cinemas WHERE id = ?;";
        PreparedStatement statement = prepareSql(sql);

        try {
            // 2 -> Set the parameter
            statement.setInt(1, cinema_id);

            // 3 -> Search for the cinema, create the object and return it to the method call
            ResultSet queryResults = query(statement);
            if (queryResults.next()) {
                int id = queryResults.getInt(1);
                boolean is_3d = queryResults.getBoolean(2);
                int nr_of_seats = queryResults.getInt(3);

                return new Cinemas(id, is_3d, nr_of_seats);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // 4 -> Else return null.
        return null;
    }

    public static Films getFilm(int film_id) {

        // 1 -> Prepare SELECT statement
        String sql = "SELECT * FROM Films WHERE id = ?";
        PreparedStatement statement = prepareSql(sql);

        // 2 -> Set the parameter
        try {
            statement.setInt(1, film_id);

            // 3 -> Search for the film and create the object (IF FOUND)
            ResultSet queryResults = query(statement);
            if (queryResults.next()) {
                int id = queryResults.getInt(1);
                String title = queryResults.getString(2);
                String category = queryResults.getString(3);
                String description = queryResults.getString(4);
                int duration = queryResults.getInt(5);

                return new Films(id, title, category, description, Duration.ofSeconds(duration));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 4 -> Else return null
        return null;
    }

    //Adds a provoli (given as the attribute) into the db
    public static boolean addProvoli(Provoles provoli) {

        // 1 -> Prepare an INSERT statement with ? being the parameters for the fields
        String sql = "INSERT INTO Provoles (film, cinema, start_date, nr_of_reservations) VALUES (?, ?, ?, 0);";
        PreparedStatement statement = prepareSql(sql);

        // 2 -> Set the parameters
        try {
            statement.setInt(1, provoli.getProvoliFilm().getFilmId());
            statement.setInt(2, provoli.getProvoliCinema().getCinemaId());
            statement.setTimestamp(3, Timestamp.valueOf(provoli.getProvoliStartDate()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 3-> Return true if the row update count is greater than 0
        return update(statement) > 0;
    }

    public static boolean addContentAdmin(ContentAdmins ca) {
        String sql = "INSERT INTO Users (name, username, password, type) VALUES (?, ?, ?, 'CA')";
        PreparedStatement statement = prepareSql(sql);

        try {
            statement.setString(1, ca.getName());
            statement.setString(2, ca.getUsername());
            statement.setString(3, ca.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return update(statement) > 0;
    }

    public static boolean removeContentAdmin(String username) {
        String sql = "DELETE FROM Users WHERE username = ?";
        PreparedStatement statement = prepareSql(sql);

        try {
            statement.setString(1, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return update(statement) > 0;
    }
}