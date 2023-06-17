package helperclasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

//This is a helper class that implements methods for generating all the dynamic HTML code for all the Servlets
public class ServletHelper {
    private static HtmlBuilder htmlBuilder = new HtmlBuilder();

    // HTML for all Servlets
    public static String welcomeHtml(String fullName) {
        return "<div class=\"text\"> " + "<h1> Welcome, " + fullName + "! </h1>\n"
                + "<h3> Choose an option in the menu on your left! </h3>" + " </div>";
    }
    public static String viewAllFilms() {
        // 1 -> Initialize table HTML
        HtmlBuilder.Table filmsTable = htmlBuilder.new Table("ID", "Title", "Category", "Duration");
        try {
            // 2 -> Gets the films through the DbHelper class and adds them in a ResultSet object
            ResultSet filmResults = DbHelper.getAllFilms();

            // 3 -> For each row obtained, get the data and create an HTML table row (<tr>)
            while (filmResults.next()) {    //If there are movies in the ResultSet --> continue
                int id = filmResults.getInt(1);
                String title = filmResults.getString(2);
                String category = filmResults.getString(3);
                String description = filmResults.getString(4); //Variable to be used in exercise 3
                int duration = filmResults.getInt(5);
                Duration tempDuration = Duration.ofSeconds(duration);
                String formattedDuration = String.format("%d:%02d:%02d",
                        tempDuration.toHours(),
                        tempDuration.toMinutesPart(),
                        tempDuration.toSecondsPart());

                filmsTable.addRow(id, title, category, formattedDuration);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        // 4 -> Return the html string
        return filmsTable.toString();
    }
    public static String viewAllProvoles() {
        HtmlBuilder.Table provolesTable = htmlBuilder.new Table("ID", "Film", "Cinema", "Start Date", "Seats Available");
        try {
            ResultSet provolesResult = DbHelper.getAllProvoles();

            while (provolesResult.next()) {
                int id = provolesResult.getInt(1);
                String film = provolesResult.getString(2);
                int cinema_id = provolesResult.getInt(3);
                Timestamp start_date = provolesResult.getTimestamp(4);
                int nr_of_rsrv = provolesResult.getInt(5);
                int nr_of_seats = provolesResult.getInt(6);

                int availableSeats = nr_of_seats - nr_of_rsrv;

                if (availableSeats == 0) {
                    provolesTable.addRowWithClass("prov_no_seats", id, film, cinema_id, start_date, availableSeats);
                }
                else if (availableSeats <= nr_of_seats / 2) {
                    provolesTable.addRowWithClass("prov_lim_seats", id, film, cinema_id, start_date, availableSeats);
                }
                else if (availableSeats > nr_of_seats / 2) {
                    provolesTable.addRowWithClass("prov_many_seats", id, film, cinema_id, start_date, availableSeats);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return provolesTable.toString();
    }

    // Admin-specific HTML
    public static String addContentAdmin() {
        return "<form action=\"admin-servlet\" method=\"post\">" +
                "<label for=\"name\">Full Name: </label>" +
                "<input type=\"text\" id=\"name\" name=\"name\"><br>" +
                "<label for=\"username\">Username: </label>" +
                "<input type=\"text\" id=\"username\" name=\"username\"><br>" +
                "<label for=\"password\">Password: </label>" +
                "<input type=\"text\" id=\"password\" name=\"password\"><br>" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form>";
    }

    public static String removeContentAdmin() {
        return "<form action=\"admin-servlet\" method=\"post\">" +
                "<label for=\"username\"> Search by username: </label>" +
                "<input type=\"text\" id=\"username\" name=\"username\">" +
                "</form>";
    }

    // ContentAdmin-specific HTML
    public static String insertNewFilm() {
        // Create form with text fields: Title, Category, Description, Duration
        HtmlBuilder.Form insertFilmForm = htmlBuilder.new Form("content-admin-servlet");
        insertFilmForm.addInput("text", "Title");
        insertFilmForm.addInput("text", "Category");
        insertFilmForm.addInput("text", "Description");
        insertFilmForm.addInput("text", "Duration");

        return insertFilmForm.toString();
    }

    public static String addProvoli() {
        // Create form with text fields: Title, Cinema, Date and Time
        HtmlBuilder.Form addProvoliForm = htmlBuilder.new Form("content-admin-servlet");
        addProvoliForm.addInput("text", "Film ID");
        addProvoliForm.addInput("text", "Cinema ID");
        addProvoliForm.addInput("datetime-local", "Date and Time");

        return addProvoliForm.toString();
    }

    public static String editFilm() {
       HtmlBuilder.Form editFilmForm = htmlBuilder.new Form("content-admin-servlet");
       editFilmForm.addInput("text", "Existing Film ID");
       editFilmForm.addInput("text", "Title");
       editFilmForm.addInput("text", "Category");
       editFilmForm.addInput("text", "Description");
       editFilmForm.addInput("text", "Duration");

       return editFilmForm.toString();
    }

    public static String removeFilm() {
        HtmlBuilder.Form removeFilmForm = htmlBuilder.new Form("content-admin-servlet");
        removeFilmForm.addInput("text", "Film ID");

        return removeFilmForm.toString();
    }

    public static String editProvoli() {
        HtmlBuilder.Form editProvoliForm = htmlBuilder.new Form("content-admin-servlet");
        editProvoliForm.addInput("text", "Existing Screening ID");
        editProvoliForm.addInput("text", "Film ID");
        editProvoliForm.addInput("text", "Cinema ID");
        editProvoliForm.addInput("datetime-local", "Date and Time");

        return editProvoliForm.toString();
    }

    public static String removeProvoli() {
        HtmlBuilder.Form removeProvoliForm = htmlBuilder.new Form("content-admin-servlet");
        removeProvoliForm.addInput("text", "Screening ID");

        return removeProvoliForm.toString();
    }
}
