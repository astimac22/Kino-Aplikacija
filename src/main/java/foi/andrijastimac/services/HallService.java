package foi.andrijastimac.services;

import foi.andrijastimac.models.Seat;
import foi.andrijastimac.models.Hall;

import java.util.ArrayList;
import java.util.List;

public class HallService {
    private static Hall hall;

    static {

        List<Seat> seats = new ArrayList<>();

        seats.add(new Seat("A1", false));
        seats.add(new Seat("A2", false));
        seats.add(new Seat("A3", true));
        seats.add(new Seat("A4", false));
        seats.add(new Seat("A5", true));
        seats.add(new Seat("A6", false));
        seats.add(new Seat("A7", false));

        hall = new Hall(seats);
    }

    public Hall getHall() {
        return hall;
    }

    public void reserveSeat(String seatNumber) {

        for (Seat seat : hall.getSeats()) {

            if (seat.getNumber().equals(seatNumber)) {

                seat.setReserved(true);
                return;
            }
        }
    }
}
