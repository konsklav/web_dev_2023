package helperclasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class ServletHelper {
    // HTML for all Servlets
    public static String welcomeHtml(String fullName) {
        return "<div class=\"text\"> " + "<h1> Welcome, " + fullName + "! </h1>\n"
                + "<h3> Choose an option in the menu on your left! </h3>" + " </div>";
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
    public static String viewAllFilms() {
        // 1 -> Initialize table HTML
        String html = "<table class='films_table'>\n";
        html += "<tr>\n" +
                "<th>ID</th>\n" +
                "<th>Title</th>\n" +
                "<th>Category</th>\n" +
                "<th>Duration</th>" +
                "</tr>";
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

                html += "<tr>\n" +
                        "<td>" + id + "</td>\n" +
                        "<td>" + title + "</td>\n" +
                        "<td>" + category + "</td>\n" +
                        "<td>" + formattedDuration + "</td>\n" +
                        "</tr>";
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        // 4 -> Close the <table> tag and return the html string
        return html + "</table>";
    }

    public static String insertNewFilm() {
        // Create form with text fields: Title, Category, Description, Duration
        return "<form class='edit_film_form' action=\"content-admin-servlet\" method=\"post\">\n" +
                "<label for=\"title\"> Title </label>\n" + "<input type=\"text\" id=\"title\" name=\"title\"><br>\n" +
                "<label for=\"category\"> Category </label>\n" + "<input type=\"text\" id=\"category\" name=\"category\"><br>\n" +
                "<label for=\"description\"> Description </label>\n" + "<input type=\"text\" id=\"description\" name=\"description\"><br>\n" +
                "<label for=\"duration\"> Duration </label>\n" + "<input type=\"text\" id=\"duration\" name=\"duration\"><br><br>\n" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form>";
    }


    public static String addProvoli() {
        // Create form with text fields: Title, Cinema, Date and Time
        return "<form class='edit_film_form' action=\"content-admin-servlet\" method=\"post\">\n" +
                "<label for=\"film\"> Film ID </label>\n" + "<input type=\"text\" id=\"film\" name=\"film\"><br>\n" +
                "<label for=\"cinema\"> Cinema ID </label>\n" + "<input type=\"text\" id=\"cinema\" name=\"cinema\"><br>\n" +
                "<label for=\"datetime\"> Date and Time </label>\n" + "<input type=\"datetime-local\" id=\"datetime\" name=\"datetime\"><br><br>\n" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form>";
    }
}
