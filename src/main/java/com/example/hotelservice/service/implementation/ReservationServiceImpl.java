package com.example.hotelservice.service.implementation;

import com.example.hotelservice.model.Reservation;
import com.example.hotelservice.service.interfaces.IReservationService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {

    private final WebClient databaseWebClient;

    public ReservationServiceImpl(WebClient databaseWebClient) {
        this.databaseWebClient = databaseWebClient;
    }

    @Override
    public List<Reservation> getRoomReservationsByUserId(long userId) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Reservation/getRoomReservationsByUserId")
                        .queryParam("userId", userId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Reservation>>() {
                })
                .block();
    }

    @Override
    public Reservation makeReservation(Reservation reservation) {
        return databaseWebClient.post()
                .uri("/Reservation/save")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(reservation), Reservation.class)
                .retrieve()
                .bodyToMono(Reservation.class)
                .block();
    }
}
