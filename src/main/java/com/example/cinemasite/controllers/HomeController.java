package com.example.cinemasite.controllers;

import com.example.cinemasite.dto.FilmsDto;
import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import com.example.cinemasite.services.FilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private FilmsService filmsService;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/home")
    public String getHomePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("userName", user.getFirstName());
                model.addAttribute("profilePicture", user.getProfilePicture()); // Agrega la imagen al modelo
            }
        }
        return "home";
    }


    @GetMapping("/films-partial")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getFilmsPartial(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "6") int size) {
        Page<Films> filmsPage = filmsService.getAllFilms(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("films", filmsPage.getContent());
        response.put("totalPages", filmsPage.getTotalPages());
        response.put("currentPage", filmsPage.getNumber());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/film/search")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchFilms(@RequestParam(value = "q", required = false, defaultValue = "") String query,
                                                           @RequestParam(value = "genre", required = false, defaultValue = "ALL") String genre,
                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "6") int size) {
        Page<Films> filmsPage = filmsService.searchFilms(query, genre, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("films", filmsPage.getContent());
        response.put("totalPages", filmsPage.getTotalPages());
        response.put("currentPage", filmsPage.getNumber());

        return ResponseEntity.ok(response);
    }
}

