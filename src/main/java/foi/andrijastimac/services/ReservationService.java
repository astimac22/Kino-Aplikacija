package foi.andrijastimac.services;

import foi.andrijastimac.models.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private final DatabaseService databaseService =
            new DatabaseService();

    public List<Reservation> reserve(
            List<String> seatNumbers,
            int screeningId,
            String customerName,
            String customerEmail
    ) {

        List<Reservation> result = new ArrayList<>();

        for (String seatNumber : seatNumbers) {

            int generatedId = -1;

            try (
                    Connection connection =
                            databaseService.getConnection();

                    PreparedStatement statement =
                            connection.prepareStatement(
                                    """
                                    INSERT INTO reservations
                                        (seat_number, screening_id, customer_name, customer_email)
                                    VALUES (?, ?, ?, ?)
                                    """,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                statement.setString(1, seatNumber);
                statement.setInt(2, screeningId);
                statement.setString(3, customerName);
                statement.setString(4, customerEmail);
                statement.executeUpdate();

                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    generatedId = keys.getInt(1);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

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

            Reservation reservation = findById(generatedId);
            if (reservation != null) {
                result.add(reservation);
            }
        }

        return result;
    }

    public Reservation findById(int reservationId) {

        try (
                Connection connection =
                        databaseService.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT r.id, r.seat_number, r.screening_id,
                                       r.customer_name, r.customer_email,
                                       s.screening_time, m.title AS movie_title
                                FROM reservations r
                                JOIN screenings s ON r.screening_id = s.id
                                JOIN movies m ON s.movie_id = m.id
                                WHERE r.id = ?
                                """
                        )
        ) {

            statement.setInt(1, reservationId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Reservation> findByEmail(String email) {

        List<Reservation> list = new ArrayList<>();

        try (
                Connection connection =
                        databaseService.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT r.id, r.seat_number, r.screening_id,
                                       r.customer_name, r.customer_email,
                                       s.screening_time, m.title AS movie_title
                                FROM reservations r
                                JOIN screenings s ON r.screening_id = s.id
                                JOIN movies m ON s.movie_id = m.id
                                WHERE r.customer_email = ?
                                ORDER BY r.id DESC
                                """
                        )
        ) {

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void cancel(int reservationId) {

        Reservation reservation = findById(reservationId);
        if (reservation == null) return;

        try (Connection connection = databaseService.getConnection()) {

            PreparedStatement freeSeats =
                    connection.prepareStatement(
                            """
                            UPDATE seats
                            SET reserved = 0
                            WHERE seat_number = ?
                            AND screening_id = ?
                            """
                    );
            freeSeats.setString(1, reservation.getSeatNumber());
            freeSeats.setInt(2, reservation.getScreeningId());
            freeSeats.executeUpdate();
            freeSeats.close();

            PreparedStatement deleteRow =
                    connection.prepareStatement(
                            "DELETE FROM reservations WHERE id = ?"
                    );
            deleteRow.setInt(1, reservationId);
            deleteRow.executeUpdate();
            deleteRow.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean changeSeat(int reservationId, String newSeatNumber) {

        Reservation reservation = findById(reservationId);
        if (reservation == null) return false;

        try (Connection connection = databaseService.getConnection()) {

            PreparedStatement checkSeat =
                    connection.prepareStatement(
                            """
                            SELECT reserved
                            FROM seats
                            WHERE seat_number = ?
                            AND screening_id = ?
                            """
                    );
            checkSeat.setString(1, newSeatNumber);
            checkSeat.setInt(2, reservation.getScreeningId());
            ResultSet rs = checkSeat.executeQuery();

            if (!rs.next() || rs.getInt("reserved") == 1) {
                checkSeat.close();
                return false;
            }
            checkSeat.close();

            PreparedStatement freeOld =
                    connection.prepareStatement(
                            """
                            UPDATE seats
                            SET reserved = 0
                            WHERE seat_number = ?
                            AND screening_id = ?
                            """
                    );
            freeOld.setString(1, reservation.getSeatNumber());
            freeOld.setInt(2, reservation.getScreeningId());
            freeOld.executeUpdate();
            freeOld.close();

            PreparedStatement reserveNew =
                    connection.prepareStatement(
                            """
                            UPDATE seats
                            SET reserved = 1
                            WHERE seat_number = ?
                            AND screening_id = ?
                            """
                    );
            reserveNew.setString(1, newSeatNumber);
            reserveNew.setInt(2, reservation.getScreeningId());
            reserveNew.executeUpdate();
            reserveNew.close();

            PreparedStatement updateRow =
                    connection.prepareStatement(
                            "UPDATE reservations SET seat_number = ? WHERE id = ?"
                    );
            updateRow.setString(1, newSeatNumber);
            updateRow.setInt(2, reservationId);
            updateRow.executeUpdate();
            updateRow.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Reservation mapRow(ResultSet rs) throws SQLException {

        return new Reservation(
                rs.getInt("id"),
                rs.getString("seat_number"),
                rs.getInt("screening_id"),
                rs.getString("screening_time"),
                rs.getString("movie_title"),
                rs.getString("customer_name"),
                rs.getString("customer_email")
        );
    }
}
