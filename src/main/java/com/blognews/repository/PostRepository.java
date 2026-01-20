package com.blognews.repository;

import com.blognews.entity.Post;
import com.blognews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    List<Post> findByCategoryId(Long categoryId);
    // JpaRepository already provides long count();
}
