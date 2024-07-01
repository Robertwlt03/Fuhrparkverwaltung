package app;

import app.modules.VehiclesModule;
import app.services.CreatePage;
import app.services.FontManager;
import app.services.PrepareDatabase;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        PrepareDatabase prepareDatabase = new PrepareDatabase();
        VehiclesModule vehiclesModule = new VehiclesModule();

        // Setup Database
        Connection conn = prepareDatabase.initializeDB();

        // Check if table exists
        if (!prepareDatabase.isTableExists(conn, "vehicles")) {
            vehiclesModule.createTable(conn);
        } else {
            System.out.println("Table already exists.");
        }

        // Set project font
        FontManager fontManager = new FontManager();
        fontManager.setDefaultFont(fontManager.DEFAULT_FONT);

        // Load frame
        CreatePage frame = new CreatePage();
        frame.createFrame(conn);
        frame.setVisible(true);
    }
}
