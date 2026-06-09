package foi.andrijastimac.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SeatController {
    public String index() {

        try {
            return Files.readString(
                    Path.of("src/main/resources/html/seats.html")
            );
        } catch (IOException e) {
            return "<h1>Error loading page</h1>";
        }
    }
}
