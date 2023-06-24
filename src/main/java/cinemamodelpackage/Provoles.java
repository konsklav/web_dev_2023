package cinemamodelpackage;
import java.time.LocalDateTime;

public class Provoles {
    private int id;
    private Films film;
    private Cinemas cinema;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int nrOfReservations;
    public boolean isAvailable;

    // Constructor appropriate for when getting an already existing Provoli
    public Provoles(int id, Films film, Cinemas cinema, LocalDateTime startDateTime, int nrOfReservations) {
        this.id = id;
        this.film = film;
        this.cinema = cinema;
        this.startDateTime = startDateTime;
        this.endDateTime = startDateTime.plus(film.getFilmDuration());	//Calculates the end time based on the films duration
        this.nrOfReservations = nrOfReservations;
        this.isAvailable = cinema.getCinemaNumberOfSeats() > nrOfReservations;
    }

    // Constructor appropriate for when creating a brand new Provoli
    public Provoles (Films film, Cinemas cinema, LocalDateTime startDateTime) {
        this(0, film, cinema, startDateTime, 0);
    }

    public int getId() {
        return id;
    }

    public Films getFilm() {
        return film;
    }

    public Cinemas getProvoliCinema() {
        return cinema;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public int getNrOfReservations() {
        return nrOfReservations;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFilm(Films film) {
        this.film = film;
    }

    public void setProvoliCinema(Cinemas provoliCinema) {
        this.cinema = provoliCinema;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setNrOfReservations(int nrOfReservations) {
        this.nrOfReservations = nrOfReservations;
    }
}
