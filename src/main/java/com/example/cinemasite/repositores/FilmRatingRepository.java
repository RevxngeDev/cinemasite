package com.example.cinemasite.repositores;

import com.example.cinemasite.models.FilmRating;
import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmRatingRepository extends JpaRepository<FilmRating, Long> {


    Optional<FilmRating> findByFilmAndUser(Films film, User user);


}
