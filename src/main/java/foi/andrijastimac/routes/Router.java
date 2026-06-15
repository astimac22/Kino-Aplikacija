package foi.andrijastimac.routes;

import foi.andrijastimac.controllers.*;

public class Router {
    public String route(String method, String path, String body) {

        if (method.equals("GET") && path.equals("/")) {
            return new HomeController().index();
        }

        if (method.equals("GET") && path.equals("/seats")) {
            return new SeatController().index();
        }

        if (method.equals("POST") && path.equals("/reserve")) {
            return new ReservationController().reserve(body);
        }

        if (path.equals("/css/style.css")) {
            return new CssController().style();
        }

        if (method.equals("GET") && path.equals("/movies")) {
            return new MovieController().index();
        }

        if (method.equals("GET") && path.equals("/screenings")) {
            return new ScreeningController().index(1);
        }

        return "404";
    }
}
