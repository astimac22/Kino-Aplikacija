package foi.andrijastimac.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TemplateService {
    public String loadTemplate(String fileName) {

        try {
            return Files.readString(
                    Path.of("src/main/resources/html/" + fileName)
            );
        } catch (IOException e) {
            return "<h1>Template error</h1>";
        }
    }

    public String replace(
            String template,
            String variable,
            String value
    ) {

        return template.replace(
                "{{" + variable + "}}",
                value
        );
    }
}
