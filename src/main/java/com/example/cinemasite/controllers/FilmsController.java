package com.example.cinemasite.controllers;

import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.Type;
import com.example.cinemasite.repositores.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
}
