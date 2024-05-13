package com.example.cinemasite.controllers;

import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
    public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!file.isEmpty()) {
                try {
                    String fileName = userDetails.getUsername() + "_profile.jpg";
                    String filePath = storagePath + File.separator + fileName;
                    File dest = new File(filePath);
                    file.transferTo(dest);
                    user.setProfilePicture(fileName);
                    usersRepository.save(user);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Manejar el error adecuadamente, quizás redirigiendo a una página de error
                }
            }
            return "redirect:/profile";
        } else {
            return "error";
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
