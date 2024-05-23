package com.example.cinemasite.services;

import com.example.cinemasite.models.FilmRating;
import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.FilmRatingRepository;
import com.example.cinemasite.repositores.FilmsRepository;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FilmRatingServiceImpl implements FilmRatingService{

    @Autowired
    private FilmRatingRepository filmRatingRepository;

    @Autowired
    private FilmsRepository filmsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void rateFilm(Long filmId, Long userId, boolean liked) {
        Optional<Films> optionalFilm = filmsRepository.findById(filmId);
        Optional<User> optionalUser = usersRepository.findById(userId);

        if (optionalFilm.isPresent() && optionalUser.isPresent()) {
            Films film = optionalFilm.get();
            User user = optionalUser.get();

            // Buscar si ya existe una calificación para esta película y usuario
            Optional<FilmRating> existingRating = filmRatingRepository.findByFilmAndUser(film, user);

            if (existingRating.isPresent()) {
                // Si ya existe una calificación, verifica si es igual a la nueva calificación
                FilmRating rating = existingRating.get();
                if (rating.isLiked() == liked) {
                    // Si la calificación es igual a la nueva calificación, eliminarla
                    filmRatingRepository.delete(rating);
                } else {
                    // Si la calificación es diferente a la nueva calificación, actualiza el valor
                    rating.setLiked(liked);
                    filmRatingRepository.save(rating); // Actualiza la calificación existente
                }
            } else {
                // Si no existe una calificación, crea una nueva
                FilmRating rating = new FilmRating();
                rating.setFilm(film);
                rating.setUser(user);
                rating.setLiked(liked);
                filmRatingRepository.save(rating);
            }
        }
    }

    @Override
    public boolean hasUserLikedFilm(Long filmId, Long userId) {
        Optional<Films> optionalFilm = filmsRepository.findById(filmId);
        Optional<User> optionalUser = usersRepository.findById(userId);

        if (optionalFilm.isPresent() && optionalUser.isPresent()) {
            Films film = optionalFilm.get();
            User user = optionalUser.get();

            // Buscar si ya existe una calificación para esta película y usuario
            Optional<FilmRating> existingRating = filmRatingRepository.findByFilmAndUser(film, user);

            return existingRating.isPresent() && existingRating.get().isLiked();
        }

        return false;
    }

    @Override
    public Long countLikesByFilmId(Long filmId) {
        return filmRatingRepository.countLikesByFilmId(filmId);
    }

    @Override
    public void incrementLikesCount(Long filmId) {
        Optional<Films> optionalFilm = filmsRepository.findById(filmId);
        if (optionalFilm.isPresent()) {
            Films film = optionalFilm.get();
            Long currentLikes = film.getLikesCount() != null ? film.getLikesCount() : 0L;
            film.setLikesCount(currentLikes + 1);
            filmsRepository.save(film);
        }
    }

    @Override
    public void decrementLikesCount(Long filmId) {
        Optional<Films> optionalFilm = filmsRepository.findById(filmId);
        if (optionalFilm.isPresent()) {
            Films film = optionalFilm.get();
            Long currentLikes = film.getLikesCount() != null ? film.getLikesCount() : 0L;
            film.setLikesCount(currentLikes > 0 ? currentLikes - 1 : 0);
            filmsRepository.save(film);
        }
    }
}
