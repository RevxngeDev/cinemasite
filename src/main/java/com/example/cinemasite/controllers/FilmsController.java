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
import java.util.List;
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
    private SeatReservationRepository seatReservationRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MailService mailService;

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
                                                       @RequestParam("youtubeLink") String youtubeLink,
                                                       @RequestParam("price") Double price) { // Nuevo parámetro
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
                    .price(price) // Asignar el precio
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

            // Obtener los asientos reservados para la película
            List<Long> reservedSeats = seatReservationRepository.findReservedSeatsByFilmId(id);
            model.addAttribute("reservedSeats", reservedSeats);

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

    @PostMapping("/reserve-seats")
    public String reserveSeats(@RequestParam("filmId") Long filmId,
                               @RequestParam("userId") Long userId,
                               @RequestParam("seats") List<Long> seatIds) {
        // Buscar la película por su ID
        Optional<Films> optionalFilm = filmsRepository.findById(filmId);
        // Buscar el usuario por su ID
        Optional<User> optionalUser = usersRepository.findById(userId);

        if (optionalFilm.isPresent() && optionalUser.isPresent()) {
            Films film = optionalFilm.get();
            User user = optionalUser.get();

            // Crear una nueva reserva de asientos
            SeatReservation reservation = SeatReservation.builder()
                    .film(film)
                    .user(user)
                    .seatIds(seatIds)
                    .build();
            seatReservationRepository.save(reservation);

            // Enviar correo de confirmación de reserva
            mailService.sendReservationEmail(user.getEmail(), film.getName(), seatIds);

            // Redirige a alguna página después de la reserva
            return "redirect:/films/" + filmId; // Cambia esto a la página que desees mostrar después de la reserva
        } else {
            // Redirige a una página de error si la película o el usuario no existen
            return "redirect:/error"; // Cambia esto a la página de error adecuada
        }
    }


}
