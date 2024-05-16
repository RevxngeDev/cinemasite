package com.example.cinemasite.services;

import com.example.cinemasite.models.Films;
import com.example.cinemasite.repositores.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


@Component
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    private FilmsRepository filmsRepository;

    public Page<Films> getAllFilms(int page, int size) {
        return filmsRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Films> searchFilms(String query, String genre, int page, int size) {
        return filmsRepository.search(query, genre, PageRequest.of(page, size));
    }
}