package com.example.hotelservice.service.implementation;

import com.example.hotelservice.model.Hotel;
import com.example.hotelservice.model.Room;
import com.example.hotelservice.service.interfaces.IRoomService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService {
    private final WebClient databaseWebClient;

    public RoomServiceImpl(WebClient databaseWebClient) {
        this.databaseWebClient = databaseWebClient;
    }

    @Override
    public List<Room> getRoomsByHotelId(Hotel hotel) {
        return null;
    }

    @Override
    public List<Room> getRoomsByCountryAndRoomTypeIds(long countryId, long roomTypeId) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/room/getRoomsByCountryAndRoomTypeIds")
                        .queryParam("countryId", countryId)
                        .queryParam("roomTypeId", roomTypeId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Room>>() {
                })
                .block();
    }

    @Override
    public Room getRoomById(long roomId) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/room/getById")
                        .queryParam("roomId", roomId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Room.class)
                .block();
    }

    private List<Room> getRooms(Hotel hotel) {
        return databaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/room/getRoomsByHotelId")
                        .queryParam("hotelId", hotel.getId())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Room>>() {
                })
                .block();
    }
}
