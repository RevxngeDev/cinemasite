package com.example.cinemasite.services;

public interface FilmRatingService {

    void rateFilm(Long filmId, Long userId, boolean liked);

    boolean hasUserLikedFilm(Long filmId, Long userId);

    Long countLikesByFilmId(Long filmId);
}
