package com.blognews.controller;

import com.blognews.entity.Category;
import com.blognews.entity.Post;
import com.blognews.service.CategoryService;
import com.blognews.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String homePage(Model model) {
        List<Post> posts = postService.getAllPublishedPosts();
        List<Category> categories = categoryService.findAll();

        model.addAttribute("posts", posts);
        model.addAttribute("categories", categories);

        return "home/index";
    }



    // POSTS BY CATEGORY
    @GetMapping("/category/{id}")
    public String postsByCategory(@PathVariable Long id, Model model) {
        List<Post> posts = postService.getPostsByCategoryId(id);
        List<Category> categories = categoryService.findAll();
        Category selectedCategory = categoryService.findById(id);

        model.addAttribute("posts", posts);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", selectedCategory);

        return "home/category-posts";
    }

    @GetMapping("/about")
    public String about() {
        return "home/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "home/contact";
    }
}
