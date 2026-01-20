package com.blognews.service.impl;

import com.blognews.entity.User;
import com.blognews.repository.UserRepository;
import com.blognews.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public long countPublishers() {
        return userRepository.countByRole("PUBLISHER");
    }

    @Override
    public List<User> findAllPublishers() {
        return userRepository.findByRole("PUBLISHER");
    }
}
