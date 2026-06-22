package foi.andrijastimac.controllers;

import foi.andrijastimac.services.HallService;
import foi.andrijastimac.services.TemplateService;

import foi.andrijastimac.models.Seat;

import java.util.List;

public class SeatController {

    private final HallService hallService =
            new HallService();

    private final TemplateService templateService =
            new TemplateService();

    public String index(int screeningId) {

        List<Seat> seats =
                hallService.getSeatsByScreening(
                        screeningId
                );

        StringBuilder seatsHtml =
                new StringBuilder();

        for (Seat seat : seats) {

            if (!seat.isReserved()) {

                seatsHtml.append("<label class=\"seat-wrapper\">");
                seatsHtml.append("<input type=\"checkbox\" class=\"seat-checkbox\" name=\"seat\" value=\"");
                seatsHtml.append(seat.getNumber());
                seatsHtml.append("\">");
                seatsHtml.append("<span class=\"seat available\">");
                seatsHtml.append(seat.getNumber());
                seatsHtml.append("</span>");
                seatsHtml.append("</label>");

            } else {

                seatsHtml.append("<div class=\"seat reserved\">");
                seatsHtml.append(seat.getNumber());
                seatsHtml.append("</div>");
            }
        }

        String template =
                templateService.loadTemplate(
                        "seats.html"
                );

        template = templateService.replace(
                template, "screeningId", String.valueOf(screeningId)
        );

        return templateService.replace(
                template, "seats", seatsHtml.toString()
        );
    }
}