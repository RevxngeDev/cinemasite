package com.example.cinemasite.controllers;

import com.example.cinemasite.models.FilmRating;
import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.Type;
import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.FilmRatingRepository;
import com.example.cinemasite.repositores.FilmsRepository;
import com.example.cinemasite.repositores.UsersRepository;
import com.example.cinemasite.services.CommentService;
import com.example.cinemasite.services.FilmRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class FilmsController {

    @Autowired
    private FilmsRepository filmsRepository;

    @Autowired
    private FilmRatingService filmRatingService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FilmRatingRepository filmRatingRepository;

    @Autowired
    private CommentService commentService;

    @Value("${storage.path}")
    private String storagePath;

    @GetMapping("/films")
    public String getSignInPage(){
        return "films";
    }

    @PostMapping("/addFilm")
    public ResponseEntity<Map<String, String>> addFilm(@RequestParam("name") String name,
                                                       @RequestParam("picture") MultipartFile picture,
                                                       @RequestParam("type") Type type,
                                                       @RequestParam("overview") String overview,
                                                       @RequestParam("youtubeLink") String youtubeLink) {
        Map<String, String> response = new HashMap<>();
        try {
            // Guardar la imagen
            String fileName = picture.getOriginalFilename();
            String filePath = storagePath + File.separator + fileName;
            File dest = new File(filePath);
            picture.transferTo(dest);

            // Crear y guardar la película
            Films film = Films.builder()
                    .name(name)
                    .picture(fileName)
                    .type(type)
                    .overView(overview)
                    .youtubeLink(youtubeLink)
                    .build();
            filmsRepository.save(film);

            response.put("status", "success");
            response.put("message", "Film added successfully");
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error adding the film");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(storagePath).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @GetMapping("/films/{id}")
    public String getFilmPage(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Films> optionalFilm = filmsRepository.findById(id);
        if (optionalFilm.isPresent()) {
            Films film = optionalFilm.get();
            model.addAttribute("film", film);

            // Obtener los comentarios para la película
            model.addAttribute("comments", commentService.getCommentsByFilm(film));

            // Verificar si el usuario ha dado "like" a esta película
            boolean userLikedFilm = false;
            if (userDetails != null) {
                Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());
                if (optionalUser.isPresent()) {
                    Long userId = optionalUser.get().getId();
                    userLikedFilm = filmRatingService.hasUserLikedFilm(id, userId);
                    model.addAttribute("currentUser", optionalUser.get());
                }
            }
            model.addAttribute("userLikedFilm", userLikedFilm);

            return "filmPage"; // Cambia "filmPage" al nombre de tu plantilla para la página de la película
        } else {
            return "redirect:/home"; // O a otra página de error si no se encuentra la película
        }
    }

    @PostMapping("/films/{id}/like")
    public String likeFilm(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            Long userId = optionalUser.get().getId();
            filmRatingService.rateFilm(id, userId, true);
        }
        // Redirige de vuelta a la misma página de la película
        return "redirect:/films/" + id;
    }

    @PostMapping("/films/{id}/dislike")
    public String dislikeFilm(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            Long userId = optionalUser.get().getId();
            filmRatingService.rateFilm(id, userId, false); // Cambiar a "false" para dislike
        }
        // Redirige de vuelta a la misma página de la película
        return "redirect:/films/" + id;
    }

    @PostMapping("/films/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam("text") String text, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());
            Optional<Films> optionalFilm = filmsRepository.findById(id);

            if (optionalUser.isPresent() && optionalFilm.isPresent()) {
                User user = optionalUser.get();
                Films film = optionalFilm.get();
                commentService.addComment(film, user, text);
            }
        }
        return "redirect:/films/" + id;
    }

}
