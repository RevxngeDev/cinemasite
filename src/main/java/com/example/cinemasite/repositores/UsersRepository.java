package com.example.cinemasite.repositores;

import com.example.cinemasite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByConfirmCode(String confirmCode);

    List<User> findAllByOrderByCreatedAtDesc();

    List<User> findAllByOrderByCreatedAtAsc();

    @Query("SELECT u FROM User u " +
            "WHERE (:q IS NULL OR :q = '' OR UPPER(u.firstName) LIKE UPPER(CONCAT('%', :q, '%')) " +
            "OR UPPER(u.lastName) LIKE UPPER(CONCAT('%', :q, '%')))")
    List<User> searchUsersByName(@Param("q") String q);

    @Query("SELECT u FROM User u " +
            "WHERE (:q IS NULL OR :q = '' OR UPPER(u.email) LIKE UPPER(CONCAT('%', :q, '%')))")
    List<User> searchUsersByEmail(@Param("q") String q);
}
