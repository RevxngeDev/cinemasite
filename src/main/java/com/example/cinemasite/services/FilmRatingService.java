package com.example.cinemasite.services;

public interface FilmRatingService {

    void rateFilm(Long filmId, Long userId, boolean liked);
}
