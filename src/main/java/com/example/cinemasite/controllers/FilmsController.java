package com.example.cinemasite.controllers;

import com.example.cinemasite.models.*;
import com.example.cinemasite.repositores.FilmRatingRepository;
import com.example.cinemasite.repositores.FilmsRepository;
import com.example.cinemasite.repositores.SeatReservationRepository;
import com.example.cinemasite.repositores.UsersRepository;
import com.example.cinemasite.services.CommentService;
import com.example.cinemasite.services.FilmRatingService;
import com.example.cinemasite.services.FilmsService;
import com.example.cinemasite.services.MailService;
import org.apache.catalina.connector.Response;
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
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class FilmsController {

    @Autowired
    private FilmsRepository filmsRepository;

    @Autowired
    private FilmsService filmsService;

    @Autowired
    private FilmRatingService filmRatingService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FilmRatingRepository filmRatingRepository;

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MailService mailService;

    @Value("${storage.path}")
    private String storagePath;

    @GetMapping("/films")
    public String getAddFilmPage(Model model){
        model.addAttribute("filmsList", filmsService.getAllFilms());
        return "films";
    }
    @PostMapping("/addFilm")
    public ResponseEntity<Map<String, String>> addFilm(@RequestParam("name") String name,
                                                       @RequestParam("picture") MultipartFile picture,
                                                       @RequestParam("type") Type type,
                                                       @RequestParam("overview") String overview,
                                                       @RequestParam("youtubeLink") String youtubeLink,
                                                       @RequestParam("price") Double price) { // Nuevo parámetro
        Map<String, String> response = new HashMap<>();
        try {

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

    @PostMapping("/films/{id}/delete")
    public RedirectView deleteFilm(@PathVariable Long id) {
        try {
            filmsService.deleteSeatReservationsByFilmId(id);
            filmsRepository.deleteById(id);
            return new RedirectView("/films", true);
        } catch (Exception e) {
            // Manejo de errores
            return new RedirectView("/error", true); // Puedes redireccionar a una página de error
        }
    }
    @GetMapping("/getFilmDetails/{id}")
    public ResponseEntity<Map<String, Object>> getFilmDetails(@PathVariable Long id) {
        Optional<Films> optionalFilm = filmsRepository.findById(id);
        if (optionalFilm.isPresent()) {
            Films film = optionalFilm.get();
            Map<String, Object> response = new HashMap<>();
            response.put("id", film.getId());
            response.put("name", film.getName());
            response.put("type", film.getType());
            response.put("overview", film.getOverView());
            response.put("youtubeLink", film.getYoutubeLink());
            response.put("price", film.getPrice());
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
    @PostMapping("/updateFilm/{id}")
    public ResponseEntity<Map<String, String>> updateFilm(@PathVariable Long id,
                                                          @RequestParam("name") String name,
                                                          @RequestParam(value = "picture", required = false) MultipartFile picture,
                                                          @RequestParam("type") Type type,
                                                          @RequestParam("overview") String overview,
                                                          @RequestParam("youtubeLink") String youtubeLink,
                                                          @RequestParam("price") Double price) {
        Map<String, String> response = new HashMap<>();
        try {
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
                response.put("status", "success");
                response.put("message", "Film updated successfully");
                return ResponseEntity.ok().body(response);
            } else {
                response.put("status", "error");
                response.put("message", "Film not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error updating the film");
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

            List<Long> reservedSeats = seatReservationRepository.findReservedSeatsByFilmId(id);
            model.addAttribute("reservedSeats", reservedSeats);

            model.addAttribute("comments", commentService.getCommentsByFilm(film));

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

            return "filmPage";
        } else {
            return "redirect:/home";
        }
    }

    @PostMapping("/films/{id}/like")
    public String likeFilm(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            Long userId = optionalUser.get().getId();
            filmRatingService.rateFilm(id, userId, true);
        }

        return "redirect:/films/" + id;
    }

    @PostMapping("/films/{id}/dislike")
    public String dislikeFilm(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            Long userId = optionalUser.get().getId();
            filmRatingService.rateFilm(id, userId, false);
        }

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

    @PostMapping("/reserve-seats")
    public String reserveSeats(@RequestParam("filmId") Long filmId,
                               @RequestParam("userId") Long userId,
                               @RequestParam("seats") List<Long> seatIds) {

        Optional<Films> optionalFilm = filmsRepository.findById(filmId);
        Optional<User> optionalUser = usersRepository.findById(userId);

        if (optionalFilm.isPresent() && optionalUser.isPresent()) {
            Films film = optionalFilm.get();
            User user = optionalUser.get();

            SeatReservation reservation = SeatReservation.builder()
                    .film(film)
                    .user(user)
                    .seatIds(seatIds)
                    .build();
            seatReservationRepository.save(reservation);

            mailService.sendReservationEmail(user.getEmail(), film.getName(), seatIds);

            return "redirect:/films/" + filmId;
        } else {
            return "redirect:/error";
        }
    }
}
