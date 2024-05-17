package com.example.cinemasite.services;

import com.example.cinemasite.dto.UserDto;


import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsers();
    boolean deleteUserById(Long id);
}
