package com.java.dao;

import com.java.entity.Reservation;

public interface IReservationDAO {
    Reservation getReservationById(int reservationId);
    void createReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    void cancelReservation(int reservationId);
}
