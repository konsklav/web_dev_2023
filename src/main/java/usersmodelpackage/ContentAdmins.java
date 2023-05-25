package usersmodelpackage;

import java.time.LocalDateTime;

import cinemamodelpackage.Cinemas;
import cinemamodelpackage.Films;
import cinemamodelpackage.Provoles;

public class ContentAdmins extends Users{

    public ContentAdmins() {
    }

    public ContentAdmins(String name, String username, String password) {
        super(name, username, password);
    }

    public Films insertFilm(Films filmToBeInserted) {
        //Will insert the film into the db
        System.out.println("Film inserted succesfully!");
        return filmToBeInserted;
    }

    public Films deleteFilm(Films filmToBeDeleted) {
        //Will delete the film off the db
        System.out.println("Film deleted succesfully!");
        return filmToBeDeleted;
    }

    public Provoles createNewProvoli(Films film, Cinemas cinema, LocalDateTime startTime) {
        Provoles provoli = new Provoles(film, cinema, startTime);
        //Insert this provoli to the db
        System.out.println("Created new provoli!");
        return provoli;
    }
}
