package com.blognews.controller;

import com.blognews.entity.Category;
import com.blognews.entity.Post;
import com.blognews.entity.User;
import com.blognews.service.CategoryService;
import com.blognews.service.PostService;
import com.blognews.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PostService postService;
    private final CategoryService categoryService;

    public AdminController(UserService userService,
                           PostService postService,
                           CategoryService categoryService) {
        this.userService = userService;
        this.postService = postService;
        this.categoryService = categoryService;
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // optional: check admin session
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("authorCount", userService.countPublishers());
        model.addAttribute("postCount", postService.countAll());
        model.addAttribute("categoryCount", categoryService.countAll());

        return "admin/dashboard";
    }

    // ADD POST (admin quick-add)
    @GetMapping("/add-post")
    public String addPostForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("publishers", userService.findAllPublishers());
        return "admin/add-post";
    }

    @PostMapping("/add-post")
    public String saveNewPost(@ModelAttribute Post post,
                              @RequestParam Long categoryId,
                              @RequestParam Long userId,
                              HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        Category category = categoryService.findById(categoryId);
        User author = userService.getById(userId);

        post.setCategory(category);
        post.setUser(author);
        postService.savePost(post);

        return "redirect:/admin/manage-posts";
    }

    // MANAGE POSTS
    @GetMapping("/manage-posts")
    public String managePosts(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        List<Post> posts = postService.getAll();
        model.addAttribute("posts", posts);
        return "admin/manage-posts";
    }

    @GetMapping("/edit-post/{id}")
    public String editPostForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        Post post = postService.getPostById(id);
        if (post == null) return "redirect:/admin/manage-posts";
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("publishers", userService.findAllPublishers());
        return "admin/edit-post";
    }

    @PostMapping("/update-post")
    public String updatePost(@ModelAttribute Post post,
                             @RequestParam Long categoryId,
                             @RequestParam Long userId,
                             HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        Category category = categoryService.findById(categoryId);
        User author = userService.getById(userId);

        post.setCategory(category);
        post.setUser(author);
        postService.savePost(post);
        return "redirect:/admin/manage-posts";
    }

    @GetMapping("/delete-post/{id}")
    public String deletePost(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        postService.deletePost(id);
        return "redirect:/admin/manage-posts";
    }

    // MANAGE PUBLISHERS
    @GetMapping("/manage-publishers")
    public String managePublishers(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        List<User> publishers = userService.findAllPublishers();
        Map<Long, Integer> postCounts = new HashMap<>();
        for (User p : publishers) {
            postCounts.put(p.getId(), postService.getPostsByUser(p).size());
        }
        model.addAttribute("publishers", publishers);
        model.addAttribute("postCounts", postCounts);
        return "admin/manage-publishers";
    }


    @GetMapping("/edit-publisher/{id}")
    public String editPublisherForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        User publisher = userService.getById(id);
        if (publisher == null) return "redirect:/admin/manage-publishers";
        model.addAttribute("publisher", publisher);
        model.addAttribute("postCount", postService.getPostsByUser(publisher).size());
        return "admin/edit-publisher";
    }

    @PostMapping("/update-publisher")
    public String updatePublisher(@ModelAttribute User publisher, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        userService.save(publisher);
        return "redirect:/admin/manage-publishers";
    }

    @GetMapping("/delete-publisher/{id}")
    public String deletePublisher(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        userService.delete(id);
        return "redirect:/admin/manage-publishers";
    }

    // MANAGE CATEGORIES
    @GetMapping("/manage-categories")
    public String manageCategories(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        model.addAttribute("categories", categoryService.findAll());
        return "admin/manage-categories";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute Category category, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        categoryService.save(category);
        return "redirect:/admin/manage-categories";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategoryForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        Category c = categoryService.findById(id);
        if (c == null) return "redirect:/admin/manage-categories";
        model.addAttribute("category", c);
        return "admin/edit-category";
    }

    @PostMapping("/update-category")
    public String updateCategory(@ModelAttribute Category category, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        categoryService.save(category);
        return "redirect:/admin/manage-categories";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        categoryService.delete(id);
        return "redirect:/admin/manage-categories";
    }


}
