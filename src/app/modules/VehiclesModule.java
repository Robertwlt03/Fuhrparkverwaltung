package app.modules;

import app.models.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiclesModule {
    public void createTable(Connection conn) {
        PreparedStatement stmt = null;
        try {
            System.out.println("Creating table in given database...");
            String createTableSQL = "CREATE TABLE IF NOT EXISTS vehicles (" +
                    "id SERIAL PRIMARY KEY," +
                    "license_plate VARCHAR(50) UNIQUE," +
                    "brand VARCHAR(50)," +
                    "model VARCHAR(50)," +
                    "type VARCHAR(50)," +
                    "color VARCHAR(50)," +
                    "year INT," +
                    "description VARCHAR(200)," +
                    "image_path VARCHAR(255))";
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

    private boolean isLicensePlateExists(Connection conn, String licensePlate) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT COUNT(*) FROM vehicles WHERE license_plate = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, licensePlate);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return false;
    }

    public boolean insertVehicle(Connection conn, String licensePlate, String brand, String model, String type, String color, Integer year, String description, String imagePath) {
        // Check whether the licencePlate already exists
        if (isLicensePlateExists(conn, licensePlate)) {
            return false;
        }

        PreparedStatement stmt = null;
        try {
            String insertSQL = "INSERT INTO vehicles (license_plate, brand, model, type, color, year, description, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1, licensePlate);
            stmt.setString(2, brand);
            stmt.setString(3, model);
            stmt.setString(4, type);
            stmt.setString(5, color);
            stmt.setInt(6, year);
            stmt.setString(7, description);
            stmt.setString(8, imagePath);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ein neues Fahrzeug wurde erfolgreich eingefügt!");
            }
        } catch (SQLException | NumberFormatException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return true;
    }

    public List<Vehicle> getAllVehicles(Connection conn) {
        List<Vehicle> vehicles = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT id, license_plate, brand, model, type, color, year, description, image_path FROM vehicles";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setType(rs.getString("type"));
                vehicle.setColor(rs.getString("color"));
                vehicle.setYear(rs.getInt("year"));
                vehicle.setDescription(rs.getString("description"));
                vehicle.setImagePath(rs.getString("image_path"));
                vehicles.add(vehicle);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return vehicles;
    }


    public Vehicle getVehicleById(Connection conn, int id) {
        Vehicle vehicle = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT id, license_plate, brand, model, type, color, year, description, image_path FROM vehicles WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setType(rs.getString("type"));
                vehicle.setColor(rs.getString("color"));
                vehicle.setYear(rs.getInt("year"));
                vehicle.setDescription(rs.getString("description"));
                vehicle.setImagePath(rs.getString("image_path"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return vehicle;
    }

    public boolean updateVehicle(Connection conn, int vehicleId, String licensePlate, String brand, String model, String type, String color, int year, String description, String imagePath) {
        PreparedStatement stmt = null;
        try {
            String query = "UPDATE vehicles SET license_plate=?, brand=?, model=?, type=?, color=?, year=?, description=?, image_path=? WHERE id=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, licensePlate);
            stmt.setString(2, brand);
            stmt.setString(3, model);
            stmt.setString(4, type);
            stmt.setString(5, color);
            stmt.setInt(6, year);
            stmt.setString(7, description);
            stmt.setString(8, imagePath);
            stmt.setInt(9, vehicleId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public boolean deleteVehicleById(Connection conn, int id) {
        PreparedStatement stmt = null;
        try {
            String query = "DELETE FROM vehicles WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public List<Vehicle> searchVehicles(Connection conn, String licensePlate, String brand, String model, String type, String color, Integer year) {
        List<Vehicle> vehicles = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            StringBuilder query = new StringBuilder("SELECT id, license_plate, brand, model, type, color, year, description, image_path FROM vehicles WHERE 1=1");

            if (licensePlate != null && !licensePlate.isEmpty()) {
                query.append(" AND license_plate LIKE ?");
            }
            if (brand != null && !brand.isEmpty()) {
                query.append(" AND brand LIKE ?");
            }
            if (model != null && !model.isEmpty()) {
                query.append(" AND model LIKE ?");
            }
            if (type != null && !type.isEmpty()) {
                query.append(" AND type LIKE ?");
            }
            if (color != null && !color.isEmpty()) {
                query.append(" AND color LIKE ?");
            }
            if (year != null) {
                query.append(" AND year = ?");
            }

            stmt = conn.prepareStatement(query.toString());

            int paramIndex = 1;
            if (licensePlate != null && !licensePlate.isEmpty()) {
                stmt.setString(paramIndex++, "%" + licensePlate + "%");
            }
            if (brand != null && !brand.isEmpty()) {
                stmt.setString(paramIndex++, "%" + brand + "%");
            }
            if (model != null && !model.isEmpty()) {
                stmt.setString(paramIndex++, "%" + model + "%");
            }
            if (type != null && !type.isEmpty()) {
                stmt.setString(paramIndex++, "%" + type + "%");
            }
            if (color != null && !color.isEmpty()) {
                stmt.setString(paramIndex++, "%" + color + "%");
            }
            if (year != null) {
                stmt.setInt(paramIndex++, year);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setType(rs.getString("type"));
                vehicle.setColor(rs.getString("color"));
                vehicle.setYear(rs.getInt("year"));
                vehicle.setDescription(rs.getString("description"));
                vehicle.setImagePath(rs.getString("image_path"));
                vehicles.add(vehicle);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return vehicles;
    }
}
