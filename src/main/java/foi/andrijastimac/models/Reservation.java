package foi.andrijastimac.models;

public class Reservation {

    private String seatNumber;
    private String screeningTime;

    public Reservation(String seatNumber, String screeningTime) {
        this.seatNumber = seatNumber;
        this.screeningTime = screeningTime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getScreeningTime() {
        return screeningTime;
    }
}