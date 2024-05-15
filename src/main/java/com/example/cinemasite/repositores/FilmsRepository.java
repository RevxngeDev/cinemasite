package com.example.cinemasite.repositores;

import com.example.cinemasite.models.Films;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FilmsRepository extends JpaRepository<Films, Long> {
    Optional<Films> findById( Long id);

    Page<Films> findAll(Pageable pageable);

    @Query("select film from Films film  where (:q = 'empty' or UPPER(film.name) like UPPER(concat('%', :q, '%')))")
    Page<Films> search(@Param("q") String q, Pageable pageable);

}
