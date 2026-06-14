package foi.andrijastimac.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private static final String URL =
            "jdbc:sqlite:kino.db";

    public Connection getConnection()
            throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
