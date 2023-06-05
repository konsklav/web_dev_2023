package cinemamodelpackage;

public class Cinemas {
    private int cinemaId;
    public boolean cinemaIs3D;
    private int cinemaNumberOfSeats;

    public Cinemas() {
    }

    public Cinemas(int cinemaId, boolean cinemaIs3D, int cinemaNumberOfSeats) {
        this.cinemaId = cinemaId;
        this.cinemaIs3D = cinemaIs3D;
        this.cinemaNumberOfSeats = cinemaNumberOfSeats;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public int getCinemaNumberOfSeats() {
        return cinemaNumberOfSeats;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public void setCinemaNumberOfSeats(int cinemaNumberOfSeats) {
        this.cinemaNumberOfSeats = cinemaNumberOfSeats;
    }

}
