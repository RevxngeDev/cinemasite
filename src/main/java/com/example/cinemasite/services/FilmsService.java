package com.example.cinemasite.services;

import com.example.cinemasite.dto.FilmsDto;
import com.example.cinemasite.models.Films;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmsService {

  Page<FilmsDto> getAllFilms(int page, int size);

  Page<FilmsDto> search (Integer page, Integer size, String query, String sortParametr, String direction);
}
