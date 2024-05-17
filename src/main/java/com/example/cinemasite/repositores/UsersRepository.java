package com.example.cinemasite.repositores;

import com.example.cinemasite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByConfirmCode(String confirmCode);
}
