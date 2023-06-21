package usersmodelpackage;

import helperclasses.DbHelper;
import helperclasses.AddUserHelper;

import java.util.ArrayList;
import java.util.List;

public class Admins extends Users{

    public Admins(int id, String name, String username, String password) {
        super(id, name, username, password);
    }

    public Admins(String name, String username, String password) {
        super(name, username, password);
    }

    public Admins(Users user) {
        this(user.id, user.name, user.username, user.password);
    }

    public boolean createContentAdmin(ContentAdmins ca) {
        System.out.println("Admin \"" + username + "\" is creating ContentAdmin \"" + ca.username + "\".");
        return AddUserHelper.addContentAdmin(ca);
    }

    public void updateUser(String username, Users userWithTheNewInformation) {
        //Updates the specified User in the db according to its username before the change and sets its fields with the
        //attributes of the userWithTheNewInformation parameter
    }

    public boolean deleteContentAdmin(String username) {
        System.out.println("Admin \"" + username + "\" is attempting to remove ContentAdmin \"" + username + "\".");
        return DbHelper.removeContentAdmin(username);
    }

    public String searchUser(String username) {
        //Selects a User from the db according to the username parameter
        //and returns it to the method if found.
        //If its not found. It will return the string: "User not found!"
        return "User found!"; //Temporary for the purposes of Askisi 1
    }

    public List<Users> viewAllUsers() {
        List<Users> allUsers = new ArrayList<>();
        //Selects all users from the db and places them in the list
        return allUsers;
    }
}
