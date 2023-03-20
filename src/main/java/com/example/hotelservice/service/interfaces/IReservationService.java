package com.example.hotelservice.service.interfaces;

import com.example.hotelservice.model.Reservation;

import java.util.List;

public interface IReservationService {

    List<Reservation> getRoomReservationsByUserId(long userId);

    Reservation makeReservation(Reservation reservation);
}
