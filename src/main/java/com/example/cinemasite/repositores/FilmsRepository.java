package com.example.cinemasite.repositores;

import com.example.cinemasite.models.Films;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmsRepository extends JpaRepository<Films, Long> {
    Optional<Films> findById( Long id);

    Page<Films> findAll(Pageable pageable);
}
