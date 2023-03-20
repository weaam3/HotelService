package com.example.hotelservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Hotel {
    private long id;
    private String name;
    private long managerId;
    private long countryId;
}
