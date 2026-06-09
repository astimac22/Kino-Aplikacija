package foi.andrijastimac.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CssController {
    public String style() {

        try {
            return Files.readString(
                    Path.of("src/main/resources/css/style.css")
            );
        } catch (IOException e) {
            return "";
        }
    }
}
