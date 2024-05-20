package com.example.cinemasite.controllers;

import com.example.cinemasite.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public String getUsersPage(@RequestParam(name = "sort", required = false, defaultValue = "desc") String sort,
                               @RequestParam(name = "searchType", required = false) String searchType,
                               @RequestParam(name = "q", required = false) String query,
                               Model model) {
        if (query != null && !query.isEmpty()) {
            if ("email".equals(searchType)) {
                model.addAttribute("usersList", usersService.searchUsersByEmail(query));
            } else {
                model.addAttribute("usersList", usersService.searchUsersByName(query));
            }
        } else {
            if ("asc".equals(sort)) {
                model.addAttribute("usersList", usersService.getAllUsersOrderedByCreatedAtAsc());
            } else {
                model.addAttribute("usersList", usersService.getAllUsersOrderedByCreatedAtDesc());
            }
        }
        return "users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = usersService.deleteUserById(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Usuario eliminado exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario.");
        }
        return "redirect:/users";
    }
}
