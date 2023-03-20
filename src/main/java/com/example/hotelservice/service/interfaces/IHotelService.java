package com.example.hotelservice.service.interfaces;

import com.example.hotelservice.model.Country;
import com.example.hotelservice.model.Hotel;

import java.util.List;

public interface IHotelService {
    List<Hotel> getAllHotels();

    List<Hotel> getAllHotelsBasedOnTheCountry(Country country);

    Hotel getHotelById(long hotelId);
}
