# Spring Boot Blog News Application

BlogNews is a full-stack web application built using Spring Boot that supports
multiple user roles such as Admin, Publisher, and Visitor.
The application allows visitors to read blog posts, publishers to manage their
own content, and admins to control users, categories, and all posts.

This project is designed as a learning-oriented but real-world blogging system,
demonstrating how backend, frontend, and database layers work together.

---

## Objective

The main objective of the BlogNews project is to build a complete Spring Boot
MVC-based web application with:

- Role-based authentication and authorization
- CRUD operations for blog posts, categories, and publishers
- Integration of Thymeleaf templates with backend controllers
- Clean and responsive UI using Bootstrap 5

This project helps in understanding full-stack development using modern
Spring Boot technologies.

---

## Problem Statement

Beginners often struggle to build a complete web application that includes
authentication, multiple roles, and CRUD functionality.
BlogNews solves this by providing a simple blogging platform where different
users (Admin, Publisher, Visitor) access features based on their roles.

---

## Features

### Visitor
- View latest blog posts
- Browse posts by category
- Read full post content
- Redirected to login for restricted actions

### Publisher
- Login and dashboard access
- Create new blog posts
- Edit and delete own posts
- View personal post list

### Admin
- Full system control
- Manage publishers
- Manage categories
- Manage all blog posts
- View overall dashboard with statistics

---

## Project Flow

1. User visits the home page and views available posts
2. Visitors can browse and read posts without logging in
3. Login system validates user role (Admin / Publisher)
4. User is redirected to the respective dashboard
5. CRUD operations are performed based on role permissions
6. Data is stored and managed using Spring Data JPA
7. Thymeleaf dynamically renders pages
8. Session handling manages login and logout

---

## Technologies Used

### Backend
- Java 21
- Spring Boot 3.5+
- Spring MVC
- Spring Data JPA (Hibernate)
- MySQL
- Java Time API

### Frontend
- Thymeleaf
- HTML5
- CSS3
- JavaScript
- Bootstrap 5

### Tools & Environment
- IntelliJ IDEA
- Maven
- MySQL Workbench
- Postman
- Windows OS

---

## System Architecture

The application follows a layered MVC architecture:

- **Controller Layer**  
  Handles HTTP requests and routes data to views

- **Service Layer**  
  Contains business logic and validations

- **Repository Layer**  
  Performs database operations using JPA repositories

- **Entity Layer**  
  Represents database tables using Java classes

---

## Database Design

### Tables
- `users` – stores Admin and Publisher details
- `posts` – stores blog post information
- `categories` – stores post categories

### Relationships
- One User → Many Posts
- One Category → Many Posts

User roles (Admin / Publisher) are managed using a role field in the users table.

---

## Result

The BlogNews application runs successfully and provides a fully functional
multi-role blogging system with the following features:

- Role-based login and redirection
- Post, category, and publisher management
- Responsive UI using Thymeleaf and Bootstrap
- Secure session handling
- Clean MVC-based application architecture

---

## Conclusion

BlogNews is a complete Spring Boot web application that demonstrates
real-world full-stack development concepts.

It effectively showcases:
- Authentication and authorization
- MVC architecture
- CRUD operations
- Dynamic UI rendering using modern Java technologies

This project is well-suited for learning purposes, technical interviews,
and resume showcasing.

---

## Author

**Peddanagalla Narasimha**

- GitHub: https://github.com/narasimha-peddanagalla  
- LinkedIn: https://www.linkedin.com/in/peddanagalla-narasimha
