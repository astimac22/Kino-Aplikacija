package foi.andrijastimac.services;

import foi.andrijastimac.models.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private final DatabaseService databaseService = new DatabaseService();

    public List<Movie> getMovies() {
        List<Movie> movies =
                new ArrayList<>();

        try (
                Connection connection =
                        databaseService.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT id,
                                       title
                                FROM movies
                                """
                        );

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                movies.add(
                        new Movie(
                                resultSet.getInt("id"),
                                resultSet.getString("title")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
}
