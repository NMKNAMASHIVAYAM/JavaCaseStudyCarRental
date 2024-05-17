package com.java.dao;

import com.java.entity.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class VehicleDAOImpl implements IVehicleDAO {

    private Connection connection;

    public VehicleDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Vehicle getVehicleById(int vehicleId) {
        String query = "SELECT * FROM Vehicle WHERE vehicleID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractVehicleFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO Vehicle (model, make, year, color, registrationNumber, availability, dailyRate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vehicle.getModel());
            statement.setString(2, vehicle.getMake());
            statement.setInt(3, vehicle.getYear());
            statement.setString(4, vehicle.getColor());
            statement.setString(5, vehicle.getRegistrationNumber());
            statement.setBoolean(6, vehicle.isAvailability());
            statement.setDouble(7, vehicle.getDailyRate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        String query = "UPDATE Vehicle SET model = ?, make = ?, year = ?, color = ?, registrationNumber = ?, " +
                "availability = ?, dailyRate = ? WHERE vehicleID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vehicle.getModel());
            statement.setString(2, vehicle.getMake());
            statement.setInt(3, vehicle.getYear());
            statement.setString(4, vehicle.getColor());
            statement.setString(5, vehicle.getRegistrationNumber());
            statement.setBoolean(6, vehicle.isAvailability());
            statement.setDouble(7, vehicle.getDailyRate());
            statement.setInt(8, vehicle.getVehicleID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeVehicle(int vehicleId) {
        String query = "DELETE FROM Vehicle WHERE vehicleID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getTotalVehicles() throws SQLException {
        int totalVehicles = 0;
        String query = "SELECT COUNT(*) FROM vehicle";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                totalVehicles = resultSet.getInt(1);
            }
        }
        return totalVehicles;
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new LinkedList<>(); // Using LinkedList
        String query = "SELECT * FROM vehicle";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleID(resultSet.getInt("VehicleID"));
                vehicle.setModel(resultSet.getString("Model"));
                vehicle.setMake(resultSet.getString("Make"));
                vehicle.setYear(resultSet.getInt("Year"));
                vehicle.setColor(resultSet.getString("Color"));
                vehicle.setRegistrationNumber(resultSet.getString("RegistrationNumber"));
                vehicle.setAvailability(resultSet.getBoolean("Availability"));
                vehicle.setDailyRate(resultSet.getDouble("DailyRate"));
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

    private Vehicle extractVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID(resultSet.getInt("vehicleID"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setRegistrationNumber(resultSet.getString("registrationNumber"));
        vehicle.setAvailability(resultSet.getBoolean("availability"));
        vehicle.setDailyRate(resultSet.getDouble("dailyRate"));
        return vehicle;
    }

	
	
}
