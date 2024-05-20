package com.example.cinemasite.repositores;

import com.example.cinemasite.models.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {

    @Query("SELECT DISTINCT seat FROM SeatReservation sr JOIN sr.seatIds seat WHERE sr.film.id = :filmId")
    List<Long> findReservedSeatsByFilmId(Long filmId);

    void deleteByFilmId(Long filmId);

    @Transactional
    void deleteByUserId(Long userId);
}
