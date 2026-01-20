package com.blognews.controller;

import com.blognews.entity.Category;
import com.blognews.entity.Post;
import com.blognews.entity.User;
import com.blognews.service.CategoryService;
import com.blognews.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/publisher")
public class PublisherController {

    private final PostService postService;
    private final CategoryService categoryService;

    public PublisherController(PostService postService,
                               CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    // -------------------- DASHBOARD --------------------
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"PUBLISHER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        List<Post> myPosts = postService.getPostsByUser(user);

        model.addAttribute("postCount", myPosts.size());
        model.addAttribute("myPosts", myPosts);

        // SEND USERNAME to Publisher Dashboard
        model.addAttribute("username", user.getFullName() != null ? user.getFullName() : user.getUsername());

        return "publisher/dashboard";
    }


    // -------------------- LIST MY POSTS --------------------
    @GetMapping("/posts")
    public String myPosts(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"PUBLISHER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("posts", postService.getPostsByUser(user));

        return "publisher/posts";
    }

    // -------------------- ADD POST FORM --------------------
    @GetMapping("/add-post")
    public String addPostForm(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"PUBLISHER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryService.findAll());

        return "publisher/add-post";
    }

    // -------------------- SAVE NEW POST --------------------
    @PostMapping("/add-post")
    public String saveNewPost(@ModelAttribute Post post,
                              @RequestParam Long categoryId,
                              HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"PUBLISHER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        Category category = categoryService.findById(categoryId);

        post.setUser(user);
        post.setCategory(category);
        postService.savePost(post);

        return "redirect:/publisher/posts";
    }

    // -------------------- EDIT POST FORM --------------------
    @GetMapping("/edit-post/{id}")
    public String editPostForm(@PathVariable Long id,
                               HttpSession session,
                               Model model) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"PUBLISHER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        Post post = postService.getPostById(id);
        if (post == null) return "redirect:/publisher/posts";

        model.addAttribute("post", post);
        model.addAttribute("categories", categoryService.findAll());

        return "publisher/edit-post";
    }

    // -------------------- UPDATE POST --------------------
    @PostMapping("/update-post")
    public String updatePost(@ModelAttribute Post post,
                             @RequestParam Long categoryId,
                             HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"PUBLISHER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        Category category = categoryService.findById(categoryId);

        post.setUser(user);
        post.setCategory(category);

        postService.savePost(post);

        return "redirect:/publisher/posts";
    }

    // -------------------- DELETE POST --------------------
    @GetMapping("/delete-post/{id}")
    public String deletePost(@PathVariable Long id,
                             HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"PUBLISHER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        postService.deletePost(id);

        return "redirect:/publisher/posts";
    }
}
