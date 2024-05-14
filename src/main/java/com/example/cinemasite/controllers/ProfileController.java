package com.example.cinemasite.controllers;

import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class ProfileController {

    @Autowired
    UsersRepository usersRepository;

    @Value("${storage.path}")
    private String storagePath;

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal UserDetails userDetails){
        Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("userName", user.getEmail());
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());
            model.addAttribute("profilePicture", user.getProfilePicture()); // Agrega la imagen al modelo
        } else {
            return "error";
        }

        return "profile";
    }

    @PostMapping("/uploadProfilePicture")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadProfilePicture(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> response = new HashMap<>();
        String fileName = userDetails.getUsername() + "_profile.jpg";
        String filePath = storagePath + File.separator + fileName;
        try {
            file.transferTo(new File(filePath));
            User user = usersRepository.findByEmail(userDetails.getUsername()).orElse(null);
            if (user == null) {
                response.put("status", "error");
                response.put("message", "Usuario no autorizado.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            user.setProfilePicture(fileName);
            usersRepository.save(user);
            response.put("status", "success");
            response.put("fileName", fileName);
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al subir la imagen.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @GetMapping("/profilePicture/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        Path path = Paths.get(storagePath).resolve(fileName);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
