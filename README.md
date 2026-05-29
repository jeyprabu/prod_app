JWT & OAuth2 Authentication API

A secure authentication and authorization system built with Java, Spring Boot, Spring Security, JWT, and OAuth2, providing stateless authentication, role-based access control, refresh token support, and third-party login integration.

Features
JWT-based authentication and authorization
OAuth2 Login integration (Google, GitHub, etc.)
Access Token & Refresh Token mechanism
Role-Based Access Control (RBAC)
Secure password encryption using BCrypt
Spring Security integration
RESTful API architecture
Exception handling and validation
Database persistence with JPA/Hibernate
Dockerized deployment support
Tech Stack
Technology	Purpose
Java 21	Core Programming Language
Spring Boot	Backend Framework
Spring Security	Authentication & Authorization
JWT	Stateless Authentication
OAuth2	Social Login
Spring Data JPA	Database Access
MySQL	Relational Database
Maven	Dependency Management
Docker	Containerization
Architecture
Client
   |
   v
Authentication APIs
   |
   +--> JWT Authentication
   |
   +--> OAuth2 Login
   |
   v
Spring Security
   |
   v
Business Services
   |
   v
MySQL Database
