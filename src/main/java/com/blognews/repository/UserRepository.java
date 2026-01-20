package com.blognews.repository;

import com.blognews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByRole(String role);
    long countByRole(String role);
    Optional<User> findById(Long id);
}
