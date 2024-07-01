package app.services;

import app.config.ConfigReader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrepareDatabase {
    private static final ConfigReader configReader = new ConfigReader();
    private final String DB_URL = configReader.getProperty("DB_URL");
    private final String USER = configReader.getProperty("DB_USER");
    private final String PASS = configReader.getProperty("DB_PASS");

    public Connection initializeDB() throws SQLException, ClassNotFoundException {
        // Register PostgreSQL JDBC driver
        Class.forName("org.postgresql.Driver");

        // Open a connection
        System.out.println("Connecting to database...");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public boolean isTableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet rs = dbMetaData.getTables(null, null, tableName, null);
        return rs.next();
    }
}
