package com.example.cinemasite.controllers;

import com.example.cinemasite.models.Films;
import com.example.cinemasite.repositores.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    FilmsRepository filmsRepository;

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/films-partial")
    public String getFilmsPartial(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "6") int size,
                                  Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Films> filmsPage = filmsRepository.findAll(pageable);
        model.addAttribute("films", filmsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", filmsPage.getTotalPages());
        return "films-partial";
    }
}
