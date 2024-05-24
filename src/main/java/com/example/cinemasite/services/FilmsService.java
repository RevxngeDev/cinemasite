package com.example.cinemasite.services;


import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FilmsService {

  Page<Films> searchFilms(String query, String genre, int page, int size);

  Page<Films> getAllFilms(int page, int size);

  List<Films> getAllFilms();

  Films getFilmById(Long id);

  void deleteFilmById(Long id);

  void deleteSeatReservationsByFilmId(Long filmId);

  List<Films> getTop3FilmsByLikes();

  void addFilm(String name, MultipartFile picture, Type type, String overview, String youtubeLink, Double price, String storagePath) throws IOException;

  void updateFilm(Long id, String name, MultipartFile picture, Type type, String overview, String youtubeLink, Double price, String storagePath) throws IOException;

}
