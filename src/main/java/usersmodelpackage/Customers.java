package usersmodelpackage;

import cinemamodelpackage.Provoles;

public class Customers extends Users{
    private Provoles customerReservation;

    public Customers() {
    }

    public Customers(String name, String username, String password) {
        super(name, username, password);
    }

    public void showAvailableFilms() {
        //Will search the db for all available films and will print them out
        System.out.println("Available films: ");
    }

    public boolean makeReservation(String provoliId, int seats) {
        //Will select the provoli from the db based on provoliId parameter and set this.customerReservation to that provoli
        //If there are available seats for this provoli according to the seats parameter, then it will make the reservation and return true
        //else, it will return false
        return true; //Temporary for the purpose of Askisi 1
    }

    public Provoles viewReservation() {
        return customerReservation;
    }
}
