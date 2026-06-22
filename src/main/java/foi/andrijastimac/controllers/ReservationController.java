package foi.andrijastimac.controllers;

import foi.andrijastimac.models.Reservation;
import foi.andrijastimac.models.Seat;
import foi.andrijastimac.services.HallService;
import foi.andrijastimac.services.ReservationService;
import foi.andrijastimac.services.TemplateService;

import java.util.List;

public class ReservationController {

    private final ReservationService reservationService =
            new ReservationService();

    private final HallService hallService =
            new HallService();

    private final TemplateService templateService =
            new TemplateService();

    public String reserve(
            String seatNumber,
            int screeningId,
            String customerName,
            String customerEmail
    ) {

        Reservation reservation =
                reservationService.reserve(
                        seatNumber,
                        screeningId,
                        customerName,
                        customerEmail
                );

        if (reservation == null) {
            return "<h1>Greška pri rezervaciji</h1>";
        }

        return buildConfirmation(reservation);
    }

    public String myReservations(String email) {

        String template =
                templateService.loadTemplate("reservations.html");

        if (email == null || email.isBlank()) {
            template = templateService.replace(template, "email", "");
            template = templateService.replace(template, "reservations", "");
            return template;
        }

        template = templateService.replace(template, "email", email);

        List<Reservation> reservations =
                reservationService.findByEmail(email);

        if (reservations.isEmpty()) {

            template = templateService.replace(
                    template,
                    "reservations",
                    "<p class=\"no-reservations\">Nema rezervacija za ovu adresu e-pošte.</p>"
            );

            return template;
        }

        StringBuilder html = new StringBuilder();

        for (Reservation r : reservations) {

            html.append("<div class=\"reservation-card\">");

            html.append("<div class=\"reservation-info\">");
            html.append("<p class=\"reservation-movie\">")
                    .append(r.getMovieTitle())
                    .append("</p>");
            html.append("<p>Termin: ").append(r.getScreeningTime()).append("</p>");
            html.append("<p>Sjedalo: <strong>")
                    .append(r.getSeatNumber())
                    .append("</strong></p>");
            html.append("<p class=\"reservation-id\">Rezervacija #")
                    .append(r.getId())
                    .append("</p>");
            html.append("</div>");

            html.append("<div class=\"reservation-actions\">");

            html.append("<a class=\"button button-secondary\" href=\"/reservation/change?id=")
                    .append(r.getId())
                    .append("\">Promijeni sjedalo</a>");

            html.append("<form method=\"POST\" action=\"/reservation\">");
            html.append("<input type=\"hidden\" name=\"_method\" value=\"DELETE\">");
            html.append("<input type=\"hidden\" name=\"id\" value=\"")
                    .append(r.getId()).append("\">");
            html.append("<input type=\"hidden\" name=\"email\" value=\"")
                    .append(r.getCustomerEmail()).append("\">");
            html.append("<button type=\"submit\" class=\"button button-danger\">Otkaži</button>");
            html.append("</form>");

            html.append("</div>");
            html.append("</div>");
        }

        template = templateService.replace(template, "reservations", html.toString());

        return template;
    }

    public String cancel(int reservationId, String email) {

        reservationService.cancel(reservationId);
        return myReservations(email);
    }

    public String changeForm(int reservationId) {

        Reservation reservation =
                reservationService.findById(reservationId);

        if (reservation == null) {
            return "404";
        }

        List<Seat> seats =
                hallService.getSeatsByScreening(
                        reservation.getScreeningId()
                );

        StringBuilder seatsHtml = new StringBuilder();

        for (Seat seat : seats) {

            if (seat.getNumber().equals(reservation.getSeatNumber())) {

                seatsHtml.append(
                        "<div class=\"seat current-seat\" title=\"Vaše trenutno sjedalo\">"
                );
                seatsHtml.append(seat.getNumber());
                seatsHtml.append("</div>");

            } else if (!seat.isReserved()) {

                seatsHtml.append("<form method=\"POST\" action=\"/reservation\">");
                seatsHtml.append("<input type=\"hidden\" name=\"_method\" value=\"PUT\">");
                seatsHtml.append("<input type=\"hidden\" name=\"id\" value=\"")
                        .append(reservationId).append("\">");
                seatsHtml.append("<input type=\"hidden\" name=\"seat\" value=\"")
                        .append(seat.getNumber()).append("\">");
                seatsHtml.append("<button class=\"seat\">")
                        .append(seat.getNumber())
                        .append("</button>");
                seatsHtml.append("</form>");

            } else {

                seatsHtml.append("<div class=\"seat reserved\">")
                        .append(seat.getNumber())
                        .append("</div>");
            }
        }

        String template =
                templateService.loadTemplate("change-seat.html");

        template = templateService.replace(
                template, "reservationId", String.valueOf(reservationId)
        );
        template = templateService.replace(
                template, "currentSeat", reservation.getSeatNumber()
        );
        template = templateService.replace(
                template, "screeningTime", reservation.getScreeningTime()
        );
        template = templateService.replace(
                template, "movieTitle", reservation.getMovieTitle()
        );
        template = templateService.replace(
                template, "seats", seatsHtml.toString()
        );

        return template;
    }

    public String changeSeat(int reservationId, String newSeatNumber) {

        boolean success =
                reservationService.changeSeat(reservationId, newSeatNumber);

        if (!success) {
            return "<div style=\"text-align:center;margin:40px\">"
                    + "<h1>Sjedalo nije dostupno</h1>"
                    + "<a href=\"/reservation/change?id=" + reservationId
                    + "\">Pokušaj ponovo</a></div>";
        }

        Reservation reservation =
                reservationService.findById(reservationId);

        if (reservation == null) {
            return "<h1>Greška</h1>";
        }

        return buildConfirmation(reservation);
    }

    private String buildConfirmation(Reservation reservation) {

        String template =
                templateService.loadTemplate("confirmation.html");

        template = templateService.replace(
                template, "id", String.valueOf(reservation.getId())
        );
        template = templateService.replace(
                template, "movieTitle", reservation.getMovieTitle()
        );
        template = templateService.replace(
                template, "seat", reservation.getSeatNumber()
        );
        template = templateService.replace(
                template, "screeningTime", reservation.getScreeningTime()
        );
        template = templateService.replace(
                template, "name", reservation.getCustomerName()
        );
        template = templateService.replace(
                template, "email", reservation.getCustomerEmail()
        );

        return template;
    }
}
