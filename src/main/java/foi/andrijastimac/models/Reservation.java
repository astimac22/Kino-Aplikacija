package foi.andrijastimac.models;

public class Reservation {

    private final int id;
    private final String seatNumber;
    private final int screeningId;
    private final String screeningTime;
    private final String movieTitle;
    private final String customerName;
    private final String customerEmail;

    public Reservation(
            int id,
            String seatNumber,
            int screeningId,
            String screeningTime,
            String movieTitle,
            String customerName,
            String customerEmail
    ) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.screeningId = screeningId;
        this.screeningTime = screeningTime;
        this.movieTitle = movieTitle;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public int getId() { return id; }
    public String getSeatNumber() { return seatNumber; }
    public int getScreeningId() { return screeningId; }
    public String getScreeningTime() { return screeningTime; }
    public String getMovieTitle() { return movieTitle; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
}
