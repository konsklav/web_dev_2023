package usersmodelpackage;

import helperclasses.DbHelper;
import java.sql.ResultSet;

public class Customers extends Users{
    public Customers(String name, String username, String password) {
        super(name, username, password);
    }

    public Customers(int id, String name, String username, String password) {
        super(id, name, username, password);
    }

    public Customers(Users user) {
        this(user.id, user.name, user.username, user.password);
    }

    public boolean makeReservation(int provoliId, int seats) {
        if (DbHelper.insertCustomerReservation(id, provoliId, seats)) {
            System.out.println(username + " succesfully made a reservation for Provoli ID " + provoliId);
            System.out.println("Seats: " + seats);
            return true;
        } else {
            System.out.println("Could not make reservation for " + username);
            return false;
        }
    }

    public ResultSet viewReservationHistory() {
        return DbHelper.viewAllReservationsForCustomer(id);
    }

}
