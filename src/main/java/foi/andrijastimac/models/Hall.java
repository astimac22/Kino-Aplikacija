package foi.andrijastimac.models;

import java.util.List;

public class Hall {
    private List<Seat> seats;

    public Hall(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
