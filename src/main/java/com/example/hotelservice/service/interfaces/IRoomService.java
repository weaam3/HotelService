package com.example.hotelservice.service.interfaces;

import com.example.hotelservice.model.Hotel;
import com.example.hotelservice.model.Room;

import java.util.List;

public interface IRoomService {
    List<Room> getRoomsByHotelId(Hotel hotel);
    List<Room> getRoomsByCountryAndRoomTypeIds(long countryId,long roomTypeId);
    Room getRoomById(long roomId);
}
