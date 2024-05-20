package com.example.cinemasite.repositores;

import com.example.cinemasite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByConfirmCode(String confirmCode);

    List<User> findAllByOrderByCreatedAtDesc();

    List<User> findAllByOrderByCreatedAtAsc();
}
