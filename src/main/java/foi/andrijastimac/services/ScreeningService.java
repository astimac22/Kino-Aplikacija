package foi.andrijastimac.services;

import foi.andrijastimac.models.Screening;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ScreeningService {

    private final DatabaseService databaseService =
            new DatabaseService();

    public List<Screening> getScreenings(
            int movieId
    ) {

        List<Screening> screenings =
                new ArrayList<>();

        try (
                Connection connection =
                        databaseService.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT id,
                                       movie_id,
                                       screening_time
                                FROM screenings
                                WHERE movie_id = ?
                                """
                        )
        ) {

            statement.setInt(
                    1,
                    movieId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                screenings.add(
                        new Screening(
                                resultSet.getInt("id"),
                                resultSet.getInt("movie_id"),
                                resultSet.getString("screening_time")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return screenings;
    }
}