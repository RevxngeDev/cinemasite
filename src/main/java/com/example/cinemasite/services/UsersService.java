package com.example.cinemasite.services;

import com.example.cinemasite.dto.UserDto;
import com.example.cinemasite.models.User;


import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsers();
    boolean deleteUserById(Long id);

    List<User> getAllUsersOrderedByCreatedAtDesc();

    List<User> getAllUsersOrderedByCreatedAtAsc();
}
