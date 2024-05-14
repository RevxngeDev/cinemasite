package com.example.cinemasite.dto;


import com.example.cinemasite.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;


    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .build();
    }

    public static List<UserDto> userList(List<User> users){
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}