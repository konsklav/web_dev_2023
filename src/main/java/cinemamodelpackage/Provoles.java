package cinemamodelpackage;

import java.time.LocalDateTime;

public class Provoles {
    private String provoliId;
    private Films provoliFilm;
    private Cinemas provoliCinema;
    private LocalDateTime provoliStartDate;
    private LocalDateTime provoliEndDate;
    private int provoliNumberOfReservations;
    public boolean provoliIsAvailable;

    public Provoles() {
    }

    public Provoles(Films provoliFilm, Cinemas provoliCinema, LocalDateTime provoliStartDate) {
        this.provoliFilm = provoliFilm;
        this.provoliCinema = provoliCinema;
        this.provoliStartDate = provoliStartDate;
        this.provoliEndDate = provoliStartDate.plus(provoliFilm.getFilmDuration());	//Calculates the end time based on the films duration
        this.provoliNumberOfReservations = 0;
        this.provoliIsAvailable = true;
    }

    public String getProvoliId() {
        return provoliId;
    }

    public Films getProvoliFilm() {
        return provoliFilm;
    }

    public Cinemas getProvoliCinema() {
        return provoliCinema;
    }

    public LocalDateTime getProvoliStartDate() {
        return provoliStartDate;
    }

    public LocalDateTime getProvoliEndDate() {
        return provoliEndDate;
    }

    public int getProvoliNumberOfReservations() {
        return provoliNumberOfReservations;
    }

    public void setProvoliId(String provoliId) {
        this.provoliId = provoliId;
    }

    public void setProvoliFilm(Films provoliFilm) {
        this.provoliFilm = provoliFilm;
    }

    public void setProvoliCinema(Cinemas provoliCinema) {
        this.provoliCinema = provoliCinema;
    }

    public void setProvoliStartDate(LocalDateTime provoliStartDate) {
        this.provoliStartDate = provoliStartDate;
    }

    public void setProvoliEndDate(LocalDateTime provoliEndDate) {
        this.provoliEndDate = provoliEndDate;
    }

    public void setProvoliNumberOfReservations(int provoliNumberOfReservations) {
        this.provoliNumberOfReservations = provoliNumberOfReservations;
    }
}
