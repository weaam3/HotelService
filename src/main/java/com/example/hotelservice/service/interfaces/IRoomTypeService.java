package com.example.hotelservice.service.interfaces;

import com.example.hotelservice.model.RoomType;

import java.util.List;

public interface IRoomTypeService {
    List<RoomType> getAllRoomTypes();

    List<RoomType> getAllRoomTypesByHotelID(long hotelId);

    RoomType getRoomTypeById(long roomTypeId);
}
