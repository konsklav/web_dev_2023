package helperclasses;

import cinemamodelpackage.Cinemas;
import cinemamodelpackage.Films;
import cinemamodelpackage.Provoles;
import usersmodelpackage.Users;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

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

    public static boolean removeFilm(int id) {
        String sql = "DELETE FROM Films WHERE id = ?;";
        PreparedStatement statement = prepareSql(sql);
        try {
            statement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return update(statement) > 0;
    }

    // Searches the DB for all Provoles and returns rows with data from Provoles, Films and Cinemas.
    public static ResultSet getAllProvoles() {
        // Prepare the SQL statement and query the DB.
        // The SQL below retrieves:
        // From Provoles: ID, start_date, nr_of_reservations, cinema
        // From Films: title
        // From Cinemas: nr_of_seats
        String sql = "SELECT p.id, f.title, cinema, start_date, nr_of_reservations, nr_of_seats " +
                    "FROM provoles p " +
                    "JOIN films f ON p.film = f.id " +
                    "JOIN cinemas c ON c.id = cinema " +
                    "ORDER BY p.id ASC;";
        PreparedStatement statement = prepareSql(sql);
        return query(statement);
    }

    public static boolean editProvoli(int provoliId, int filmId, int cinemaId, LocalDateTime start_date) {
        String sql = "UPDATE Provoles SET film = ?, cinema = ?, start_date = ? WHERE id = ?";
        PreparedStatement statement = prepareSql(sql);
        try {
            statement.setInt(1, filmId);
            statement.setInt(2, cinemaId);
            statement.setTimestamp(3, Timestamp.valueOf(start_date));
            statement.setInt(4, provoliId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return update(statement) > 0;
    }

    // Searches the Provoles table for a Provoli with ID = id, and removes it
    public static boolean removeProvoli(int id) {
        String sql = "DELETE FROM Provoles WHERE id = ?;";
        PreparedStatement statement = prepareSql(sql);
        try {
            statement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update(statement) > 0;
    }

    //Searches the db for a user based on the username given as attribute
    public static ResultSet findUser(String username) {
        // 1 -> Prepare a SELECT statement with ? being the parameter for the username
        String sql = "SELECT * FROM Users WHERE username = ?;";
        PreparedStatement statement = prepareSql(sql);

        // 2 -> Set the parameter
        try {
            statement.setString(1, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 3 -> Execute/Run the SQL statement and return the resulting row
        return query(statement);
    }

    //Adds a film (given as the attribute) into the db
    public static boolean addNewFilm(Films film) {
        // 1 -> Prepare an INSERT statement with ? being the parameters for the fields
        String sql = "INSERT INTO Films (title, category, description, duration) VALUES (?, ?, ?, ?);";
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
        String sql = "SELECT * FROM Films WHERE id = ?;";
        PreparedStatement statement = prepareSql(sql);

        // 2 -> Set the parameter
        try {
            statement.setInt(1, film_id);

            // 3 -> Search for the film and create the object (IF FOUND)
            ResultSet queryResults = query(statement);
            if (queryResults.next()) {
                String title = queryResults.getString(2);
                String category = queryResults.getString(3);
                String description = queryResults.getString(4);
                int duration = queryResults.getInt(5);

                return new Films(film_id, title, category, description, Duration.ofSeconds(duration));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 4 -> Else return null
        return null;
    }

    public static Provoles getProvoli(int id) {
        // 1 -> Prepare SQL statement
        String sql = "SELECT * FROM Provoles WHERE id = ?";
        PreparedStatement statement = prepareSql(sql);

        try {
            // 2 -> Configure SQL parameters
            statement.setInt(1, id);

            // 3 -> Query SQL
            ResultSet queryResults = query(statement);
            if (queryResults.next()) {
                // 4 -> Get query results
                int filmId = queryResults.getInt(2);
                int cinemaId = queryResults.getInt(3);
                LocalDateTime start_date = queryResults.getTimestamp(4).toLocalDateTime();
                int nrOfReservations = queryResults.getInt(5);

                // 5 -> Get the cinema and film objects from the resulting IDs
                Cinemas cinema = getCinema(cinemaId);
                Films film = getFilm(filmId);

                // 6 -> Return the new Provoli object and make sure the film and cinema objects aren't null
                assert film != null;
                assert cinema != null;
                return new Provoles(id, film, cinema, start_date, nrOfReservations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 7 -> If there was an exception OR no rows from the query, return null
        return null;
    }


    //Adds a provoli (given as the attribute) into the db
    public static boolean addProvoli(Provoles provoli) {
        // 1 -> Prepare an INSERT statement with ? being the parameters for the fields
        String sql = "INSERT INTO Provoles (film, cinema, start_date, nr_of_reservations) VALUES (?, ?, ?, 0);";
        PreparedStatement statement = prepareSql(sql);

        // 2 -> Set the parameters
        try {
            statement.setInt(1, provoli.getFilm().getFilmId());
            statement.setInt(2, provoli.getProvoliCinema().getCinemaId());
            statement.setTimestamp(3, Timestamp.valueOf(provoli.getStartDateTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 3-> Return true if the row update count is greater than 0
        return update(statement) > 0;
    }

    //Removes the specified content admin off the db
    public static boolean removeContentAdmin(String username) {
        // 1 -> Prepare a DELETE statement with ? being the parameter
        String sql = "DELETE FROM Users WHERE username = ?;";
        PreparedStatement statement = prepareSql(sql);

        // 2 -> Sets the parameter
        try {
            statement.setString(1, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 3-> Return true if the row update count is greater than 0
        return update(statement) > 0;
    }

    //Key method that adds a User (of any type) in the db
    public static boolean addUser(String userType, String name, String username, String salt, String hashedPassword) {
        // 1 -> Prepare an INSERT statement with ? being the parameters for the fields
        String sql = "INSERT INTO Users (name, username, salt, hash, type) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement statement = prepareSql(sql);

        // 2 -> Set the parameters
        try {
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, salt);
            statement.setString(4, hashedPassword);
            statement.setString(5, userType);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 3 -> Return true if the row update count is greater than 0
        return update(statement) > 0;
    }

    public static boolean editFilm(int filmId, String title, String category, String description, int duration) {
        String sql = "UPDATE Films SET title = ?, category = ?, description = ?, duration = ? WHERE id = ?;";
        PreparedStatement statement = prepareSql(sql);

        try {
            statement.setString(1, title);
            statement.setString(2, category);
            statement.setString(3, description);
            statement.setInt(4, duration);
            statement.setInt(5, filmId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return update(statement) > 0;
    }
}