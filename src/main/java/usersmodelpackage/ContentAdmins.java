package usersmodelpackage;

import java.time.LocalDateTime;
import cinemamodelpackage.Films;
import cinemamodelpackage.Provoles;
import helperclasses.DbHelper;

public class ContentAdmins extends Users{

    public ContentAdmins(int id, String name, String username, String password) {
        super(id, name, username, password);
    }

    public ContentAdmins(String name, String username, String password) {
        super(name, username, password);
    }

    public ContentAdmins(Users user) {
        this(user.id, user.name, user.username, user.password);
    }

    public boolean insertFilm(Films filmToBeInserted) {
        //Uses the DbHelper class to insert the film into the db, displays the appropriate message and returns the film that was inserted.
        if (DbHelper.addNewFilm(filmToBeInserted)) {
            System.out.println("Film \"" + filmToBeInserted.getFilmTitle() + "\" inserted successfully by " + username);
            return true;
        } else {
            System.out.println("Failed to insert film \"" + filmToBeInserted.getFilmTitle() + "\".");
            System.out.println("Insert action by " + username);
            return false;
        }
    }

    public boolean updateFilm(int filmId, String title, String category, String description, int duration) {
        if (DbHelper.editFilm(filmId, title, category, description, duration)) {
            System.out.println("Film \"" + title + "\" updated successfully by " + username);
            return true;
        } else {
            System.out.println("Failed to update film \"" + title + "\".");
            System.out.println("Update action by " + username);
            return false;
        }
    }

    public boolean deleteFilm(int filmId) {
        //Will delete the film off the db
       if (DbHelper.removeFilm(filmId)) {
           System.out.println("Film \"" + filmId + "\" deleted successfully by " + username);
           return true;
       } else {
           System.out.println("Failed to delete film \"" + filmId + "\".");
           System.out.println("Delete action by " + username);
           return false;
       }
    }

    public boolean insertProvoli(Provoles provoli) {
        // Add the new Provoli object in the db using the DbHelper class
        if (DbHelper.addProvoli(provoli)) {
            System.out.println(username + " created a new provoli");
            return true;
        } else {
            System.out.println("Failed to insert provoli.");
            System.out.println("Insert action by " + username);
            return false;
        }
    }

    public boolean updateProvoli(int provoliId, int filmId, int cinemaId, LocalDateTime start_date) {
        if (DbHelper.editProvoli(provoliId, filmId, cinemaId, start_date)) {
            System.out.println(username + " updated provoli with ID " + provoliId);
            return true;
        } else {
            System.out.println("Failed to update provoli with ID " + provoliId);
            System.out.println("Update action by " + username);
            return false;
        }
    }

    public boolean deleteProvoli(int provoliId) {
        if (DbHelper.removeProvoli(provoliId)) {
            System.out.println(username + " deleted provoli with ID " + provoliId);
            return true;
        } else {
            System.out.println("Failed to delete provoli with ID " + provoliId);
            System.out.println("Delete action by " + username);
            return false;
        }
    }
}
