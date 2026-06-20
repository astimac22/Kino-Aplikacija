package foi.andrijastimac.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private final DatabaseService databaseService =
            new DatabaseService();

    public void initialize() {

        String sql;

        try {
            sql = Files.readString(
                    Path.of("src/main/resources/schema.sql")
            );
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (
                Connection connection =
                        databaseService.getConnection();

                Statement statement =
                        connection.createStatement()
        ) {

            for (String query : sql.split(";")) {

                String trimmed = query.trim();

                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }

            System.out.println("Baza podataka inicijalizirana.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
