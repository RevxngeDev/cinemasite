package com.example.cinemasite.services;

import com.example.cinemasite.dto.UserDto;
import com.example.cinemasite.repositores.SeatReservationRepository;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.cinemasite.dto.UserDto.userList;

@Component
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userList(usersRepository.findAll());
    }

    @Transactional
    public boolean deleteUserById(Long id) {
        if (usersRepository.existsById(id)) {
            seatReservationRepository.deleteByUserId(id);
            usersRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
