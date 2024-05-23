package com.example.cinemasite.services;

import com.example.cinemasite.models.Films;
import com.example.cinemasite.repositores.FilmsRepository;
import com.example.cinemasite.repositores.SeatReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;


@Component
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    private FilmsRepository filmsRepository;

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    public Page<Films> getAllFilms(int page, int size) {
        return filmsRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Films> getAllFilms() {
        return filmsRepository.findAll();
    }

    @Override
    public Films getFilmById(Long id) {
        return filmsRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFilmById(Long id) {
        filmsRepository.deleteById(id);
    }

    public Page<Films> searchFilms(String query, String genre, int page, int size) {
        return filmsRepository.search(query, genre, PageRequest.of(page, size));
    }

    @Transactional
    public void deleteSeatReservationsByFilmId(Long filmId) {
        seatReservationRepository.deleteByFilmId(filmId);
    }

    @Override
    public List<Films> getTop3FilmsByLikes() {
        return filmsRepository.findTop3ByOrderByLikesCountDesc();
    }
}