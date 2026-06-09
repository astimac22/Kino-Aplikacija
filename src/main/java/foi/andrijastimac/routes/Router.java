package foi.andrijastimac.routes;

import foi.andrijastimac.controllers.HomeController;
import foi.andrijastimac.controllers.SeatController;
import foi.andrijastimac.controllers.CssController;

public class Router {
    public String route(String path) {
        if (path.equals("/")) {
            return new HomeController().index();
        }

        if (path.equals("/seats")) {
            return new SeatController().index();
        }

        if (path.equals("/css/style.css")) {
            return new CssController().style();
        }

        return "404";
    }
}
