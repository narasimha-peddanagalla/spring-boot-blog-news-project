package com.blognews.service;

import com.blognews.entity.Post;
import com.blognews.entity.User;

import java.util.List;

public interface PostService {
    List<Post> getPostsByUser(User user);
    Post getPostById(Long id);
    void savePost(Post post);
    void deletePost(Long id);
    List<Post> getAllPublishedPosts();
    List<Post> getPostsByCategoryId(Long categoryId);
    long countAll();
    List<Post> getAll(); // admin manage posts
}
