package foi.andrijastimac.services;

import foi.andrijastimac.models.Seat;
import foi.andrijastimac.models.Hall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HallService {

    private final DatabaseService databaseService =
            new DatabaseService();

    public Hall getHall() {

        List<Seat> seats =
                new ArrayList<>();

        try (
                Connection connection =
                        databaseService.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT seat_number,
                                       reserved
                                FROM seats
                                """
                        );

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                String seatNumber =
                        resultSet.getString(
                                "seat_number"
                        );

                boolean reserved =
                        resultSet.getInt(
                                "reserved"
                        ) == 1;

                seats.add(
                        new Seat(
                                seatNumber,
                                reserved
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return new Hall(seats);
    }

    public List<Seat> getSeatsByScreening(int screeningId) {

        List<Seat> seats =
                new ArrayList<>();

        try (
                Connection connection =
                        databaseService.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT seat_number, reserved
                                FROM seats
                                WHERE screening_id = ?
                                """
                        )
        ) {

            statement.setInt(1, screeningId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                seats.add(
                        new Seat(
                                resultSet.getString("seat_number"),
                                resultSet.getInt("reserved") == 1
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seats;
    }
}
