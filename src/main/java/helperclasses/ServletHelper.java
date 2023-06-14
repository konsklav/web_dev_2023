package helperclasses;

public class ServletHelper {
    public static String welcomeHtml(String fullName) {
        return "<div class=\"text\"> " + "<h1> Welcome, " + fullName + "! </h1>\n"
                + "<h3> Choose an option in the menu on your left! </h3>" + " </div>";
    }
}
