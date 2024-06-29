package app.services;

import app.config.ConfigReader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrepareDatabase {
    private static final ConfigReader configReader = new ConfigReader();
    private final String DB_URL = configReader.getProperty("DB_URL");
    private final String USER = configReader.getProperty("DB_USER");
    private final String PASS = configReader.getProperty("DB_PASS");

    public void initializeDB() {
        Connection conn = null;
        try {
            // Register PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Check if table exists and create if it doesn't
            if (!isTableExists(conn, "vehicles")) {
                createTable(conn);
            } else {
                System.out.println("Table already exists.");
            }

            // Fill the table with initial data
            fillDB(conn);

        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            // Finally block to close resources
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private boolean isTableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet rs = dbMetaData.getTables(null, null, tableName, null);
        return rs.next();
    }

    private void createTable(Connection conn) {
        PreparedStatement stmt = null;
        try {
            System.out.println("Creating table in given database...");
            String createTableSQL = "CREATE TABLE vehicles (" +
                    "id SERIAL PRIMARY KEY," +
                    "make VARCHAR(50)," +
                    "model VARCHAR(50)," +
                    "year INT)";
            stmt = conn.prepareStatement(createTableSQL);
            stmt.executeUpdate();
            System.out.println("Table created successfully");
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private void fillDB(Connection conn) {
        PreparedStatement stmt = null;
        try {
            String insertSQL = "INSERT INTO vehicles (make, model, year) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1, "Ford");
            stmt.setString(2, "Focus");
            stmt.setInt(3, 2022);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new vehicle was inserted successfully!");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
