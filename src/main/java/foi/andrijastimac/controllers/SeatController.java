package foi.andrijastimac.controllers;

import foi.andrijastimac.models.Seat;
import foi.andrijastimac.services.HallService;

public class SeatController {
    public String index() {

        HallService hallService = new HallService();

        StringBuilder html = new StringBuilder();

        html.append("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Sjedala</title>
                    <link rel="stylesheet" href="/css/style.css">
                </head>
                <body>
                <div class="container">
                <h1>Dvorana</h1>
                <div class="seats">
                """);

        for (Seat seat : hallService.getHall().getSeats()) {

            String cssClass = "seat";

            if (seat.isReserved()) {

                cssClass = "seat reserved";

                html.append("""
                <div class="seat-wrapper">
                """);

                html.append("<div class=\"")
                        .append(cssClass)
                        .append("\">")
                        .append(seat.getNumber())
                        .append("</div>");

                html.append("""
                </div>
                """);

            } else {

                html.append("""
                <div class="seat-wrapper">
                <form method="POST" action="/reserve">
                """);

                html.append("<input type=\"hidden\" name=\"seat\" value=\"")
                        .append(seat.getNumber())
                        .append("\">");

                html.append("<button class=\"seat\">")
                        .append(seat.getNumber())
                        .append("</button>");

                html.append("""
                </form>
                </div>
                """);
            }
        }

        html.append("""
                </div>
                <a class="button" href="/">Početna</a>
                </div>
                </body>
                </html>
                """);

        return html.toString();
    }
}
