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
    public String getProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
        } else {
            return "error";
        }

        return "profile";
    }

    @PostMapping("/uploadProfilePicture")
    @ResponseBody
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
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
                    return ResponseEntity.ok(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return ResponseEntity.badRequest().body("File is empty");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/profile-picture")
    @ResponseBody
    public ResponseEntity<Resource> getProfilePicture(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String fileName = user.getProfilePicture();
            if (fileName != null) {
                try {
                    Path path = Paths.get(storagePath, fileName);
                    Resource resource = new UrlResource(path.toUri());
                    if (resource.exists() && resource.isReadable()) {
                        return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG)
                                .body(resource);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

}
