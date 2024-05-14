package com.example.cinemasite.repositores;

import com.example.cinemasite.models.Films;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmsRepository extends JpaRepository<Films, Long> {
}
