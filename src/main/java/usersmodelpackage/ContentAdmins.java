package usersmodelpackage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import cinemamodelpackage.Cinemas;
import cinemamodelpackage.Films;
import cinemamodelpackage.Provoles;
import helperclasses.DbHelper;

public class ContentAdmins extends Users{

    public ContentAdmins() {
    }

    public ContentAdmins(String name, String username, String password) {
        super(name, username, password);
    }

    public void insertFilm(Films filmToBeInserted) throws SQLException {
        //Uses the DbHelper class to insert the film into the db, displays the appropriate message and returns the film that was inserted.
        if (DbHelper.addNewFilm(filmToBeInserted)) {
            System.out.println("Film with title " + filmToBeInserted.getFilmTitle() + " inserted successfully by " + username);
            //!!! Σε αυτό, όπως και στα υπόλοιπα (και σε όλες τις κλάσεις), να βρούμε τι θα κάνουμε με το System.out...
        } else {
            System.out.println("Film failed to insert ");
        }
    }

    public Films deleteFilm(Films filmToBeDeleted) throws SQLException {
        //Will delete the film off the db
        System.out.println("Film deleted successfully!");
        return filmToBeDeleted;
    }

    public void createNewProvoli(int filmId, int cinemaId, LocalDateTime startTime) throws SQLException {
        // 1 -> Get the cinema and film objects from the db based in their id's using the DbHelper class
        Cinemas cinema = DbHelper.getCinema(cinemaId);
        Films film = DbHelper.getFilm(filmId);

        // 2 -> Return if the cinema and/or film objects are null (hence the query couldn't find a match with the specific id in the db)
        if (cinema == null || film == null) {
            System.out.println("Couldn't create provoli, because the cinema OR film were not found");
            return;
        }

        // 3 -> Add the new Provoli object in the db using the DbHelper class
        if (DbHelper.addProvoli(new Provoles(film, cinema, startTime))) {
            System.out.println(getUsername() + " created a new provoli");
        } else {
            System.out.println("Failed to add provoli to database");
        }
    }
}
