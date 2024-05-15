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
    public Page<FilmsDto> search(Integer page, Integer size, String query, String sortParametr, String directionParametr) {
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "id");

        if (directionParametr != null) {
            direction = Sort.Direction.fromString(directionParametr);
        }

        if (sortParametr != null) {
            sort = Sort.by(direction, sortParametr);
        }

        if (query == null) {
            query = "empty";
        }

        if (size == null) {
            size = 3;
        }
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Films> papersPage = filmsRepository.search(query,pageRequest);
        Page<FilmsDto> filmsDtoPage = papersPage.map(FilmsDto::of);
        return filmsDtoPage;
    }
}
