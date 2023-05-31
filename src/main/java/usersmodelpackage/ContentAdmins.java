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

    public Films insertFilm(Films filmToBeInserted) throws SQLException {
        //Will insert the film into the db
        if (DbHelper.addNewFilm(filmToBeInserted)) {
            System.out.println
                    ("Film with title " + filmToBeInserted.getFilmTitle() + " inserted successfully by " + username);
        } else {
            System.out.println("Film failed to insert ");
        }

        return filmToBeInserted;
    }

    public Films deleteFilm(Films filmToBeDeleted) throws SQLException {
        //Will delete the film off the db
        System.out.println("Film deleted succesfully!");
        return filmToBeDeleted;
    }

    public Provoles createNewProvoli(Films film, Cinemas cinema, LocalDateTime startTime) throws SQLException {
        Provoles provoli = new Provoles(film, cinema, startTime);
        //Insert this provoli to the db
        System.out.println("Created new provoli!");
        return provoli;
    }
}
