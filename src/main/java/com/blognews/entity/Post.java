package com.blognews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

//    @Column(length = 500)
//    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    // MANY posts belong to ONE user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // MANY posts belong to ONE category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
