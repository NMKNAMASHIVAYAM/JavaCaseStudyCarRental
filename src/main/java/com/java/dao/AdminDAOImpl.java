package com.java.dao;

import com.java.entity.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements IAdminDAO {

    private Connection connection;

    public AdminDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Admin getAdminById(int adminId) {
        String query = "SELECT * FROM Admin WHERE adminID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAdminFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Admin getAdminByUsername(String username) {
        String query = "SELECT * FROM Admin WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAdminFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void registerAdmin(Admin admin) {
        String query = "INSERT INTO Admin (firstName, lastName, email, phoneNumber, username, password, role, joinDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getPhoneNumber());
            statement.setString(5, admin.getUsername());
            statement.setString(6, admin.getPassword());
            statement.setString(7, admin.getRole());
            statement.setString(8, admin.getJoinDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAdmin(Admin admin) {
        String query = "UPDATE Admin SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, " +
                "username = ?, password = ?, role = ?, joinDate = ? WHERE adminID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getPhoneNumber());
            statement.setString(5, admin.getUsername());
            statement.setString(6, admin.getPassword());
            statement.setString(7, admin.getRole());
            statement.setString(8, admin.getJoinDate());
            statement.setInt(9, admin.getAdminID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAdmin(int adminId) {
        String query = "DELETE FROM Admin WHERE adminID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, adminId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getTotalAdmins() throws SQLException {
        int totalAdmins = 0;
        String query = "SELECT COUNT(*) FROM admin";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                totalAdmins = resultSet.getInt(1);
            }
        }
        return totalAdmins;
    }

    private Admin extractAdminFromResultSet(ResultSet resultSet) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminID(resultSet.getInt("adminID"));
        admin.setFirstName(resultSet.getString("firstName"));
        admin.setLastName(resultSet.getString("lastName"));
        admin.setEmail(resultSet.getString("email"));
        admin.setPhoneNumber(resultSet.getString("phoneNumber"));
        admin.setUsername(resultSet.getString("username"));
        admin.setPassword(resultSet.getString("password"));
        admin.setRole(resultSet.getString("role"));
        admin.setJoinDate(resultSet.getString("joinDate"));
        return admin;
    }
}
