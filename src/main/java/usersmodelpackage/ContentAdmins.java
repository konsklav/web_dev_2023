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

    public void createNewProvoli(int filmId, int cinemaId, LocalDateTime startTime) throws SQLException {
        Cinemas cinema = DbHelper.getCinema(cinemaId);
        Films film = DbHelper.getFilm(filmId);

        if (cinema == null || film == null) {
            System.out.println("Couldn't create provoli, because the cinema OR film were not found");
            return;
        }

        //Να φτιάξουμε συνάρτηση στη DbHelper class που να αποθηκεύει την provoli στη βάση.
        if (DbHelper.addProvoli(new Provoles(film, cinema, startTime))) {
            System.out.println(getUsername() + " created a new provoli");
        } else {
            System.out.println("Failed to add provoli to database");
        }
    }
}
