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
            //NA GINEI KAPWS ALLIWS, OPWS KAI STA YPOLOIPA
        } else {
            System.out.println("Film failed to insert ");
        }
    }

    public Films deleteFilm(Films filmToBeDeleted) throws SQLException {
        //Will delete the film off the db
        System.out.println("Film deleted succesfully!");
        return filmToBeDeleted;
    }

    public Provoles createNewProvoli(Films film, Cinemas cinema, LocalDateTime startTime) throws SQLException {
        Provoles provoli = new Provoles(film, cinema, startTime);
        provoli.setProvoliNumberOfReservations(0);
        //Να φτιάξουμε συνάρτηση στη DbHelper class που να αποθηκεύει την provoli στη βάση.
        System.out.println("Created new provoli!");
        return provoli;
    }
}
