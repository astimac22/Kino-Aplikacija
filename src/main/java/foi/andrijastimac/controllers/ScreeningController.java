package foi.andrijastimac.controllers;

import foi.andrijastimac.models.Screening;
import foi.andrijastimac.services.ScreeningService;
import foi.andrijastimac.services.TemplateService;

import java.util.List;

public class ScreeningController {
    private final ScreeningService screeningService =
            new ScreeningService();

    private final TemplateService templateService =
            new TemplateService();

    public String index(int movieId) {

        List<Screening> screenings =
                screeningService.getScreenings(
                        movieId
                );

        StringBuilder screeningsHtml =
                new StringBuilder();

        for (Screening screening : screenings) {

            screeningsHtml.append(
                    "<div class=\"screening-card\">"
            );

            screeningsHtml.append(
                    screening.getScreeningTime()
            );

            screeningsHtml.append(
                    "</div>"
            );
        }

        String template =
                templateService.loadTemplate(
                        "screenings.html"
                );

        return templateService.replace(
                template,
                "screenings",
                screeningsHtml.toString()
        );
    }
}
