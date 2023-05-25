package helperclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {
    private final static String url = "jdbc:postgresql://localhost:5432/web_dev_db";
    private final static String username = "postgres";
    private final static String password = "p21xxx";
    public static Connection connect(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return connection;
    }
}
