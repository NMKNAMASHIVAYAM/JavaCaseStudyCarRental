package com.java.dao;


import com.java.entity.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReservationDAOImpl implements IReservationDAO {

    private Connection connection;

    public ReservationDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        String query = "SELECT * FROM Reservation WHERE reservationID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractReservationFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createReservation(Reservation reservation) {
        String query = "INSERT INTO Reservation (customerID, vehicleID, startDate, endDate, totalCost, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getCustomerID());
            statement.setInt(2, reservation.getVehicleID());
            statement.setString(3, reservation.getStartDate());
            statement.setString(4, reservation.getEndDate());
            statement.setDouble(5, reservation.getTotalCost());
            statement.setString(6, reservation.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {
        String query = "UPDATE Reservation SET customerID = ?, vehicleID = ?, startDate = ?, endDate = ?, " +
                "totalCost = ?, status = ? WHERE reservationID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getCustomerID());
            statement.setInt(2, reservation.getVehicleID());
            statement.setString(3, reservation.getStartDate());
            statement.setString(4, reservation.getEndDate());
            statement.setDouble(5, reservation.getTotalCost());
            statement.setString(6, reservation.getStatus());
            statement.setInt(7, reservation.getReservationID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelReservation(int reservationId) {
        String query = "DELETE FROM Reservation WHERE reservationID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getTotalReservations() throws SQLException {
        int totalReservations = 0;
        String query = "SELECT COUNT(*) FROM reservation";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                totalReservations = resultSet.getInt(1);
            }
        }
        return totalReservations;
    }
    
    


    private Reservation extractReservationFromResultSet(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationID(resultSet.getInt("reservationID"));
        reservation.setCustomerID(resultSet.getInt("customerID"));
        reservation.setVehicleID(resultSet.getInt("vehicleID"));
        reservation.setStartDate(resultSet.getString("startDate"));
        reservation.setEndDate(resultSet.getString("endDate"));
        reservation.setTotalCost(resultSet.getDouble("totalCost"));
        reservation.setStatus(resultSet.getString("status"));
        return reservation;
    }
}
