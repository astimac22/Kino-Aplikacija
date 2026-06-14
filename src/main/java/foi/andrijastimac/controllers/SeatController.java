package foi.andrijastimac.controllers;

import foi.andrijastimac.models.Seat;
import foi.andrijastimac.services.HallService;
import foi.andrijastimac.services.TemplateService;

public class SeatController {
    public String index() {

        HallService hallService =
                new HallService();

        TemplateService templateService =
                new TemplateService();

        String template = templateService.loadTemplate("seats.html");

        StringBuilder seatsHtml = new StringBuilder();

        for (Seat seat : hallService.getHall().getSeats()) {

            if (seat.isReserved()) {

                seatsHtml.append("<div class=\"seat reserved\">");
                seatsHtml.append(seat.getNumber());
                seatsHtml.append("</div>");

            } else {
                seatsHtml.append("<form method=\"POST\" action=\"/reserve\">");
                seatsHtml.append("<input type=\"hidden\" name=\"seat\" value=\"");
                seatsHtml.append(seat.getNumber());
                seatsHtml.append("\">");
                seatsHtml.append("<button class=\"seat\">");
                seatsHtml.append(seat.getNumber());
                seatsHtml.append("</button>");
                seatsHtml.append("</form>");
            }
        }

        return templateService.replace(
                template,
                "seats",
                seatsHtml.toString()
        );
    }
}
