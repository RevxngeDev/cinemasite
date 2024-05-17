package com.example.cinemasite.services;

import com.example.cinemasite.dto.FilmsDto;
import com.example.cinemasite.models.Films;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FilmsService {

  Page<Films> searchFilms(String query, String genre, int page, int size);

  Page<Films> getAllFilms(int page, int size);

  List<Films> getAllFilms();

  Films getFilmById(Long id);

  void deleteFilmById(Long id);

  void deleteSeatReservationsByFilmId(Long filmId);
}
