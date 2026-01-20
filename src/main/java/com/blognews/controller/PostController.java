package com.blognews.controller;

import com.blognews.entity.Post;
import com.blognews.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // =============================
    // VIEW SINGLE POST (Visitor)
    // =============================
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {

        Post post = postService.getPostById(id);

        // If not found → send to home
        if (post == null) {
            return "redirect:/";
        }

        model.addAttribute("post", post);
        return "home/post-view"; // The template that displays single post
    }

}
