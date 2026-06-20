package foi.andrijastimac.routes;

import foi.andrijastimac.controllers.*;

public class Router {

    public String route(
            String method,
            String path,
            Integer movieId,
            Integer screeningId,
            String seatNumber
    ) {

        if (method.equals("GET") && path.equals("/style.css")) {
            return new CssController().style();
        }


        if (method.equals("GET") && path.equals("/")) {
            return new HomeController().index();
        }

        if (
                method.equals("GET")
                        &&
                        path.equals("/movies")
        ) {

            return new HomeController()
                    .index();
        }

        if (
                method.equals("GET")
                        &&
                        path.equals("/screenings")
        ) {

            return new ScreeningController()
                    .index(movieId);
        }

        if (
                method.equals("GET")
                        &&
                        path.equals("/seats")
        ) {

            if (screeningId == null) {
                return "404";
            }

            return new SeatController()
                    .index(screeningId);
        }

        if (
                method.equals("POST")
                        &&
                        path.equals("/reserve")
        ) {

            return new ReservationController()
                    .reserve(
                            seatNumber,
                            screeningId
                    );
        }

        return "404";
    }
}