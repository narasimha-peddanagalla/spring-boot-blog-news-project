package com.blognews.controller;

import com.blognews.entity.User;
import com.blognews.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // LOGIN PAGE
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }

    // LOGIN SUBMIT
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User loginUser,
                            Model model,
                            HttpSession session) {

        User dbUser = userService.findByUsername(loginUser.getUsername());

        if (dbUser == null || !dbUser.getPassword().equals(loginUser.getPassword())) {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        }

        // SAVE USER IN SESSION
        session.setAttribute("loggedInUser", dbUser);

        // REDIRECT BASED ON ROLE
        if ("ADMIN".equalsIgnoreCase(dbUser.getRole())) {
            return "redirect:/admin/dashboard";
        } else if ("PUBLISHER".equalsIgnoreCase(dbUser.getRole())) {
            return "redirect:/publisher/dashboard";
        } else {
            return "redirect:/";
        }
    }

    // REGISTER PAGE
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // REGISTER SUBMIT
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,
                               Model model) {

        user.setRole("PUBLISHER"); // Default Role

        userService.save(user);

        model.addAttribute("success", "Account created successfully. Please login.");
        return "auth/login";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
