package com.example.hotelservice.service.interfaces;

import com.example.hotelservice.model.User;

public interface IUserService {
    User save(User user);
    User getByUserName(String username,String password);
}
