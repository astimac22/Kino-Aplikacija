package foi.andrijastimac.services;

import foi.andrijastimac.models.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReservationService {

    private final DatabaseService databaseService =
            new DatabaseService();

    public Reservation reserve(String seatNumber, int screeningId) {

        try (
                Connection connection =
                        databaseService.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE seats
                                SET reserved = 1
                                WHERE seat_number = ?
                                AND screening_id = ?
                                """
                        )
        ) {

            statement.setString(1, seatNumber);
            statement.setInt(2, screeningId);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String screeningTime =
                getScreeningTime(screeningId);

        return new Reservation(seatNumber, screeningTime);
    }

    private String getScreeningTime(int screeningId) {

        try (
                Connection connection =
                        databaseService.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT screening_time
                                FROM screenings
                                WHERE id = ?
                                """
                        )
        ) {

            statement.setInt(1, screeningId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("screening_time");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}