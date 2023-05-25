package cinemamodelpackage;

import java.time.Duration;

public class Films {
    private String filmId;
    private String filmTitle;
    private String filmCategory;
    private String filmDescription;
    private Duration filmDuration;

    public Films() {
    }

    public Films(String filmId, String filmTitle, String filmCategory, String filmDescription, Duration filmDuration) {
        this.filmId = filmId;
        this.filmTitle = filmTitle;
        this.filmCategory = filmCategory;
        this.filmDescription = filmDescription;
        this.filmDuration = filmDuration;
    }

    public String getFilmId() {
        return filmId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public String getFilmCategory() {
        return filmCategory;
    }

    public String getFilmDescription() {
        return filmDescription;
    }

    public Duration getFilmDuration() {
        return filmDuration;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public void setFilmCategory(String filmCategory) {
        this.filmCategory = filmCategory;
    }

    public void setFilmDescription(String filmDescription) {
        this.filmDescription = filmDescription;
    }

    public void setFilmDuration(Duration filmDuration) {
        this.filmDuration = filmDuration;
    }
}
