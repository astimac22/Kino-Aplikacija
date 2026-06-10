package foi.andrijastimac.controllers;

import foi.andrijastimac.services.HallService;

public class ReservationController {
    public String reserve(String body) {

        String seatNumber =
                body.replace("seat=", "");

        HallService service =
                new HallService();

        service.reserveSeat(seatNumber);

        return new SeatController().index();
    }
}
