package com.example.cinemasite.repositores;

import com.example.cinemasite.models.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {
    // Puedes agregar métodos personalizados según tus necesidades
}
