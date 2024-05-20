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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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
    public String getProfile(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                // Usuario autenticado desde la base de datos
                UserDetails userDetails = (UserDetails) principal;
                Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());

                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    model.addAttribute("userName", user.getEmail());
                    model.addAttribute("firstName", user.getFirstName());
                    model.addAttribute("lastName", user.getLastName());
                    model.addAttribute("profilePicture", user.getProfilePicture());
                } else {
                    return "error";
                }
            } else if (principal instanceof OidcUser) {
                // Usuario autenticado con Google OAuth
                OidcUser oidcUser = (OidcUser) principal;
                String email = oidcUser.getEmail();

                Optional<User> optionalUser = usersRepository.findByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    model.addAttribute("userName", user.getEmail());
                    model.addAttribute("firstName", user.getFirstName());
                    model.addAttribute("lastName", user.getLastName());
                    model.addAttribute("profilePicture", user.getProfilePicture());
                } else {
                    return "error";
                }
            } else {
                return "error";
            }

            return "profile";
        }
        return "error";
    }

    @PostMapping("/uploadProfilePicture")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadProfilePicture(@RequestParam("file") MultipartFile file, Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("status", "error");
            response.put("message", "Usuario no autorizado.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Object principal = authentication.getPrincipal();
        String email = null;

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else if (principal instanceof OidcUser) {
            email = ((OidcUser) principal).getEmail();
        }

        if (email != null) {
            String fileName = email + "_profile.jpg";
            String filePath = storagePath + File.separator + fileName;
            try {
                file.transferTo(new File(filePath));
                User user = usersRepository.findByEmail(email).orElse(null);
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

        response.put("status", "error");
        response.put("message", "Usuario no autorizado.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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
