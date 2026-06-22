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

                seatsHtml.append("<form class=\"seat-form\" method=\"POST\" action=\"/reserve\">");

                seatsHtml.append(
                        "<input type=\"hidden\" name=\"seat\" value=\""
                );

                seatsHtml.append(
                        seat.getNumber()
                );

                seatsHtml.append("\">");

                seatsHtml.append(
                        "<input type=\"hidden\" name=\"screening\" value=\""
                );

                seatsHtml.append(
                        screeningId
                );

                seatsHtml.append("\">");

                seatsHtml.append(
                        "<input type=\"hidden\" name=\"name\" value=\"\">"
                );

                seatsHtml.append(
                        "<input type=\"hidden\" name=\"email\" value=\"\">"
                );

                seatsHtml.append(
                        "<button class=\"seat\">"
                );

                seatsHtml.append(
                        seat.getNumber()
                );

                seatsHtml.append("</button>");

                seatsHtml.append("</form>");

            } else {

                seatsHtml.append(
                        "<div class=\"seat reserved\">"
                );

                seatsHtml.append(
                        seat.getNumber()
                );

                seatsHtml.append("</div>");
            }
        }

        String template =
                templateService.loadTemplate(
                        "seats.html"
                );

        return templateService.replace(
                template,
                "seats",
                seatsHtml.toString()
        );
    }
}