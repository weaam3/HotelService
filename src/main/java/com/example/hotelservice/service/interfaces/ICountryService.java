package com.example.hotelservice.service.interfaces;

import com.example.hotelservice.model.Country;

import java.util.List;

public interface ICountryService {
    List<Country> getAllCountries();
    Country getCountryById(long id);
}
