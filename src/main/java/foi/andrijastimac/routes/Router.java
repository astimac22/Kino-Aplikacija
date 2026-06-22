package foi.andrijastimac.routes;

import foi.andrijastimac.controllers.*;

public class Router {

    public String route(
            String method,
            String path,
            Integer movieId,
            Integer screeningId,
            String seatNumber,
            String name,
            String email,
            Integer reservationId
    ) {

        if (method.equals("GET") && path.equals("/style.css")) {
            return new CssController().style();
        }

        if (method.equals("GET") && path.equals("/")) {
            return new HomeController().index();
        }

        if (method.equals("GET") && path.equals("/movies")) {
            return new HomeController().index();
        }

        if (method.equals("GET") && path.equals("/screenings")) {
            return new ScreeningController().index(movieId);
        }

        if (method.equals("GET") && path.equals("/seats")) {

            if (screeningId == null) {
                return "404";
            }

            return new SeatController().index(screeningId);
        }

        if (method.equals("POST") && path.equals("/reserve")) {
            return new ReservationController()
                    .reserve(seatNumber, screeningId, name, email);
        }

        if (method.equals("GET") && path.equals("/reservations")) {
            return new ReservationController().myReservations(email);
        }

        if (method.equals("GET") && path.equals("/reservation/change")) {

            if (reservationId == null) {
                return "404";
            }

            return new ReservationController().changeForm(reservationId);
        }

        if (method.equals("DELETE") && path.equals("/reservation")) {

            if (reservationId == null) {
                return "404";
            }

            return new ReservationController().cancel(reservationId, email);
        }

        if (method.equals("PUT") && path.equals("/reservation")) {

            if (reservationId == null || seatNumber == null) {
                return "404";
            }

            return new ReservationController().changeSeat(reservationId, seatNumber);
        }

        return "404";
    }
}
