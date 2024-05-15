package com.example.cinemasite.repositores;

import com.example.cinemasite.models.Films;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FilmsRepository extends JpaRepository<Films, Long> {
    Optional<Films> findById(Long id);

    Page<Films> findAll(Pageable pageable);

    @Query("SELECT film FROM Films film " +
            "WHERE (:q IS NULL OR :q = '' OR UPPER(film.name) LIKE UPPER(CONCAT('%', :q, '%'))) " +
            "AND (:genre IS NULL OR :genre = '' OR :genre = 'ALL' OR film.type = :genre)")
    Page<Films> search(@Param("q") String query, @Param("genre") String genre, Pageable pageable);

}
