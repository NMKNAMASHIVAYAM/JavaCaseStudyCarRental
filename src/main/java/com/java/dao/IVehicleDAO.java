package com.java.dao;

import com.java.entity.Vehicle;

public interface IVehicleDAO {
    Vehicle getVehicleById(int vehicleId);
    void addVehicle(Vehicle vehicle);
    void updateVehicle(Vehicle vehicle);
    void removeVehicle(int vehicleId);
}
