package foi.andrijastimac.controllers;

import foi.andrijastimac.models.Reservation;
import foi.andrijastimac.services.ReservationService;
import foi.andrijastimac.services.TemplateService;

public class ReservationController {

    private final ReservationService reservationService =
            new ReservationService();

    private final TemplateService templateService =
            new TemplateService();

    public String reserve(String seatNumber, int screeningId) {

        Reservation reservation =
                reservationService.reserve(seatNumber, screeningId);

        String template =
                templateService.loadTemplate("confirmation.html");

        template = templateService.replace(
                template,
                "seat",
                reservation.getSeatNumber()
        );

        template = templateService.replace(
                template,
                "screeningTime",
                reservation.getScreeningTime()
        );

        return template;
    }
}