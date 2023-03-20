package com.example.hotelservice.controller;

import com.example.hotelservice.model.*;
import com.example.hotelservice.service.interfaces.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

@Controller
public class HotelController {
    private final ICountryService countryService;
    private final IRoomTypeService roomTypeService;
    private final IRoomService roomService;
    private final IReservationService reservationService;
    private final IUserService userService;
    private final IHotelService hotelService;

    public HotelController(ICountryService countryService, IRoomTypeService roomTypeService, IRoomService roomService, IReservationService reservationService, IUserService userService, IHotelService hotelService) {
        this.countryService = countryService;
        this.roomTypeService = roomTypeService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.userService = userService;
        this.hotelService = hotelService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Country> countries = countryService.getAllCountries();
        List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
        model.addAttribute("countries", countries);
        model.addAttribute("roomTypes", roomTypes);
        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam("countryId") long countryId, @RequestParam("roomTypeId") long roomTypeId, Model model) {
        List<Room> rooms = roomService.getRoomsByCountryAndRoomTypeIds(countryId, roomTypeId);
        model.addAttribute("rooms", rooms);
        model.addAttribute("countryName", getCountryName(countryId));
        model.addAttribute("roomType", getRoomTypeById(roomTypeId));
        return "roomList";
    }

    @PostMapping("/reserve")
    public String reserve(@RequestParam("roomId") long roomId, @RequestParam("roomTypeId") long roomTypeId, Model model) {
        Timestamp old = new Timestamp(System.currentTimeMillis());
        ZonedDateTime zonedDateTime = old.toInstant().atZone(ZoneId.of("UTC"));
        Timestamp newDate = Timestamp.from(zonedDateTime.plus(14, ChronoUnit.DAYS).toInstant());
        RoomType roomType = roomTypeService.getRoomTypeById(roomTypeId);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = this.userService.getByUserName(username, "").getId();
        Reservation reservation = reservationService.makeReservation(new Reservation(-1, userId, roomId, roomType.getPrice(), old, newDate));

        model.addAttribute("reservation", reservation);

        return "reservation-confirmation";
    }

    @GetMapping("/reservations")
    public String showReservations(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = this.userService.getByUserName(username, "").getId();
        List<Reservation> reservations = reservationService.getRoomReservationsByUserId(userId);
        HashMap<Long, String> hotels = new HashMap<>();
        HashMap<Long, String> rooms = new HashMap<>();
        HashMap<Long, String> countries = new HashMap<>();
        Room room;
        Hotel hotel;
        Country country;
        for (Reservation reservation : reservations) {
            room = roomService.getRoomById(reservation.getRoomId());
            hotel = hotelService.getHotelById(room.getHotelId());
            country = countryService.getCountryById(hotel.getCountryId());
            rooms.put(reservation.getId(), room.getNumber());
            hotels.put(reservation.getId(), hotel.getName());
            countries.put(reservation.getId(), country.getName());
        }
        model.addAttribute("reservations", reservations);
        model.addAttribute("rooms", rooms);
        model.addAttribute("hotels", hotels);
        model.addAttribute("countries", countries);

        return "reservations";
    }

    private String getCountryName(long countryId) {
        return countryService.getCountryById(countryId).getName();
    }

    private RoomType getRoomTypeById(long roomType) {
        return roomTypeService.getRoomTypeById(roomType);
    }

}
