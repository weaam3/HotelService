package com.example.hotelservice.service.implementation;

import com.example.hotelservice.model.Country;
import com.example.hotelservice.model.Hotel;
import com.example.hotelservice.service.interfaces.IHotelService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class HotelServiceImpl implements IHotelService {

    private final WebClient databaseWebClient;

    public HotelServiceImpl(WebClient databaseWebClient) {
        this.databaseWebClient = databaseWebClient;
    }

    @Override
    public List<Hotel> getAllHotels() {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotel/getAllHotels")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Hotel>>() {
                })
                .block();
    }

    @Override
    public List<Hotel> getAllHotelsBasedOnTheCountry(Country country) {
        return getHotels(country);
    }

    @Override
    public Hotel getHotelById(long hotelId) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotel/getHotelById")
                        .queryParam("hotelId", hotelId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Hotel.class)
                .block();
    }

    private List<Hotel> getHotels(Country country) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotel/getAllHotelsBasedOnTheCountry")
                        .queryParam("countryId", country.getId())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Hotel>>() {
                })
                .block();
    }
}
