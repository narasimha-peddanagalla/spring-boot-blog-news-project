package com.blognews.service;

import com.blognews.entity.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User save(User user);
    List<User> getAll();
    User getById(Long id);
    void delete(Long id);

    // helpers used in AdminController
    long countPublishers();               // count users with role=PUBLISHER
    List<User> findAllPublishers();       // list of publishers
}
