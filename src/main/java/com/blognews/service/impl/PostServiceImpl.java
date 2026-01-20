package com.blognews.service.impl;

import com.blognews.entity.Post;
import com.blognews.entity.User;
import com.blognews.repository.PostRepository;
import com.blognews.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getPostsByUser(User user) {
        return postRepository.findByUser(user);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void savePost(Post post) {
        if (post.getCreatedAt() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


    @Override
    public List<Post> getAllPublishedPosts() {
        List<Post> posts = postRepository.findAll();
        //Sorting to get latest first
        posts.sort((a, b) -> {
            //To Prevent Null pointer Exception
            if (a.getCreatedAt() == null && b.getCreatedAt() == null) return 0;
            if (a.getCreatedAt() == null) return 1;   // null = treat as oldest
            if (b.getCreatedAt() == null) return -1;
            return b.getCreatedAt().compareTo(a.getCreatedAt()); // newest first
        });

        return posts;
    }



    @Override
    public List<Post> getPostsByCategoryId(Long categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }

    @Override
    public long countAll() {
        return postRepository.count();
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }


}
