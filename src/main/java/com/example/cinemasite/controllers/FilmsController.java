package com.example.cinemasite.controllers;

import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.Type;
import com.example.cinemasite.repositores.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
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
                                                       @RequestParam("overview") String overview) {
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
    public String getFilmPage(@PathVariable Long id, Model model) {
        Optional<Films> optionalFilm = filmsRepository.findById(id);
        if (optionalFilm.isPresent()) {
            Films film = optionalFilm.get();
            model.addAttribute("film", film);
            return "filmPage"; // Cambia "filmPage" al nombre de tu plantilla para la página de la película
        } else {
            return "redirect:/home"; // O a otra página de error si no se encuentra la película
        }
    }


}
