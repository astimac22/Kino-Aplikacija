package foi.andrijastimac.models;

public class Screening {
    private int id;
    private int movieId;
    private String screeningTime;

    public Screening(
            int id,
            int movieId,
            String screeningTime
    ) {
        this.id = id;
        this.movieId = movieId;
        this.screeningTime = screeningTime;
    }

    public int getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getScreeningTime() {
        return screeningTime;
    }
}
