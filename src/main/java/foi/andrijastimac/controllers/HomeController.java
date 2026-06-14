package foi.andrijastimac.controllers;

import foi.andrijastimac.services.TemplateService;



public class HomeController {
    public String index() {

        TemplateService templateService =
                new TemplateService();

        String html =
                templateService.loadTemplate(
                        "index.html"
                );

        html =
                templateService.replace(
                        html,
                        "title",
                        "Kino aplikacija"
                );

        html =
                templateService.replace(
                        html,
                        "description",
                        "Dobrodošli u sustav za rezervaciju sjedala."
                );

        return html;
    }
}
