package cinemamodelpackage;

public class Cinemas {
    private String cinemaId;
    public boolean cinemaIs3D;
    private int cinemaNumberOfSeats;

    public Cinemas() {
    }

    public Cinemas(String cinemaId, boolean cinemaIs3D, int cinemaNumberOfSeats) {
        this.cinemaId = cinemaId;
        this.cinemaIs3D = cinemaIs3D;
        this.cinemaNumberOfSeats = cinemaNumberOfSeats;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public int getCinemaNumberOfSeats() {
        return cinemaNumberOfSeats;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public void setCinemaNumberOfSeats(int cinemaNumberOfSeats) {
        this.cinemaNumberOfSeats = cinemaNumberOfSeats;
    }

}
