package com.example.hotelservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Room {
    private long id;
    private long hotelId;
    private String number;
    private long typeId;
    private int bedCount;
}