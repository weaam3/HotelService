package com.example.hotelservice.service.implementation;

import com.example.hotelservice.model.Country;
import com.example.hotelservice.service.interfaces.ICountryService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class CountryServiceImpl implements ICountryService {
    private final WebClient databaseWebClient;

    public CountryServiceImpl(WebClient databaseWebClient) {
        this.databaseWebClient = databaseWebClient;
    }

    @Override
    public List<Country> getAllCountries() {

        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/country/getAllCountries")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Country>>() {
                })
                .block();
    }

    @Override
    public Country getCountryById(long countryId) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/country/getCountryById")
                        .queryParam("countryId", countryId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
    }
}

