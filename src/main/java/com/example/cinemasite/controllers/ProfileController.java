package com.example.cinemasite.controllers;

import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        } else {
            return "error";
        }

        return "profile";
    }

    @PostMapping("/uploadProfilePicture")
    public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
        // Get the user
        Optional<User> optionalUser = usersRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Check if the file is empty
            if (file.isEmpty()) {
                return "redirect:/profile?error=empty"; // Redirect back with error message
            }

            // Save the file to the file system
            try {
                String fileName = userDetails.getUsername() + "_profile.jpg"; // You can use any logic to generate the file name
                String filePath = storagePath + File.separator + fileName; // Construct the full file path
                File dest = new File(filePath);
                file.transferTo(dest);

                // Save the file path to the user profile
                user.setProfilePicture(fileName);
                usersRepository.save(user);

                return "redirect:/profile?success=true"; // Redirect back with success message
            } catch (IOException e) {
                return "redirect:/profile?error=true"; // Redirect back with error message
            }
        } else {
            return "redirect:/profile?error=userNotFound"; // Redirect back with error message
        }
    }

}