package helperclasses;

import usersmodelpackage.Admins;
import usersmodelpackage.ContentAdmins;
import usersmodelpackage.Customers;

// This is a helper class that implements some basic methods for adding users to the application
public class AddUserHelper {
    // Adds the specified admin in the db using the addUser method of the DbHelper class
    public static boolean addAdmin(Admins ad) {
        // 1 -> Get the name, username and password from the Admins object
        String name = ad.getName();
        String username = ad.getUsername();
        String plainPassword = ad.getPassword();

        // 2 -> Generate salt and hash the given password
        String salt = HashHelper.generateSalt();
        String hashedPassword = HashHelper.hashPassword(plainPassword, salt);

        // 3 -> Add the user and tha hashed password to the db
        return DbHelper.addUser("AD", name, username, salt, hashedPassword);
    }

    // Adds the specified content admin in the db using the addUser method of the DbHelper class
    public static boolean addContentAdmin(ContentAdmins ca) {
        // 1 -> Get the name, username and password from the ContentAdmins object
        String name = ca.getName();
        String username = ca.getUsername();
        String plainPassword = ca.getPassword();

        // 2 -> Generate salt and hash the given password
        String salt = HashHelper.generateSalt();
        String hashedPassword = HashHelper.hashPassword(plainPassword, salt);

        // 3 -> Add the user and tha hashed password to the db
        return DbHelper.addUser("CA", name, username, salt, hashedPassword);
    }

    // Adds the specified customer in the db using the addUser method of the DbHelper class
    public static boolean addCustomer(Customers cu) {
        // 1 -> Get the name, username and password from the Customers object
        String name = cu.getName();
        String username = cu.getUsername();
        String plainPassword = cu.getPassword();

        // 2 -> Generate salt and hash the given password
        String salt = HashHelper.generateSalt();
        String hashedPassword = HashHelper.hashPassword(plainPassword, salt);

        // 3 -> Add the user and tha hashed password to the db
        return DbHelper.addUser("CU", name, username, salt, hashedPassword);
    }
}
