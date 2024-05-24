package com.example.cinemasite.services;

import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.Type;
import com.example.cinemasite.repositores.FilmsRepository;
import com.example.cinemasite.repositores.SeatReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


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

    @Override
    public void addFilm(String name, MultipartFile picture, Type type, String overview, String youtubeLink, Double price, String storagePath) throws IOException {
        String fileName = picture.getOriginalFilename();
        String filePath = storagePath + File.separator + fileName;
        File dest = new File(filePath);
        picture.transferTo(dest);

        Films film = Films.builder()
                .name(name)
                .picture(fileName)
                .type(type)
                .overView(overview)
                .youtubeLink(youtubeLink)
                .price(price)
                .likesCount(0L)
                .build();
        filmsRepository.save(film);
    }

    @Override
    public void updateFilm(Long id, String name, MultipartFile picture, Type type, String overview, String youtubeLink, Double price, String storagePath) throws IOException {
        Optional<Films> optionalFilm = filmsRepository.findById(id);
        if (optionalFilm.isPresent()) {
            Films film = optionalFilm.get();
            film.setName(name);
            film.setType(type);
            film.setOverView(overview);
            film.setYoutubeLink(youtubeLink);
            film.setPrice(price);

            if (picture != null && !picture.isEmpty()) {
                String fileName = picture.getOriginalFilename();
                String filePath = storagePath + File.separator + fileName;
                File dest = new File(filePath);
                picture.transferTo(dest);
                film.setPicture(fileName);
            }

            filmsRepository.save(film);
        }
    }
}