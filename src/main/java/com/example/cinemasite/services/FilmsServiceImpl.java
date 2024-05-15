package com.example.cinemasite.services;

import com.example.cinemasite.dto.FilmsDto;
import com.example.cinemasite.models.Films;
import com.example.cinemasite.repositores.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class FilmsServiceImpl implements FilmsService{
    @Autowired

    private FilmsRepository filmsRepository;


    @Override
    public Page<FilmsDto> getAllFilms(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return filmsRepository.findAll(pageRequest).map(FilmsDto::of);
    }

    @Override
    public Page<FilmsDto> searchFilms(int page, int size, String query) {
        Pageable pageable = PageRequest.of(page, size);
        return filmsRepository.search(query, pageable).map(FilmsDto::of);
    }

}
