# SecureAuthX - OAuth2 & JWT Authentication System

A production-style authentication and authorization platform built using **Java 21**, **Spring Boot 3**, **Spring Security 6**, **JWT**, **OAuth 2.0**, and **MySQL**. The application provides secure authentication using both traditional email/password login and Google OAuth2 login with Role-Based Access Control (RBAC), Refresh Tokens, and Method-Level Authorization.

---

## Features

* JWT Authentication & Authorization
* Google OAuth2 Login
* Access & Refresh Token Workflow
* Role-Based Access Control (RBAC)
* Method-Level Security
* Spring Security Filter Chain
* DTO Validation
* Centralized Exception Handling
* BCrypt Password Encryption
* MySQL Persistence
* Stateless Security Architecture
* Docker Ready

---

## Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security 6
* Spring Data JPA
* Hibernate

### Authentication

* JWT (JSON Web Token)
* OAuth 2.0 (Google Login)
* Refresh Tokens
* RBAC

### Database

* MySQL

### Validation & Security

* Bean Validation
* BCrypt Password Encoding

### Build Tools

* Maven
* Docker

---

## Architecture

```text
Client
   |
   +----------------+
   |                |
JWT Login      OAuth2 Login
   |                |
   +-------+--------+
           |
   Spring Security
           |
      JWT Filter
           |
     Authorization
           |
      Service Layer
           |
        MySQL
```

---

## Project Structure

```text
src/main/java/com/example/oauthjwtapp

├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
├── service
└── handler
```

---

## Security Features

### JWT Authentication

* Access Token Generation
* Token Validation
* Stateless Authorization
* Protected REST APIs

### OAuth2 Authentication

* Google OAuth2 Login
* Automatic User Provisioning
* JWT Generation After Successful Login

### Authorization

* Role-Based Access Control
* USER and ADMIN Roles
* Service-Layer Method Authorization

---



## API Endpoints

### Authentication

#### Register

```http
POST /auth/register
```

Request

```json
{
  "name": "John Doe",
  "email": "john@gmail.com",
  "password": "password123"
}
```

---

#### Login

```http
POST /auth/login
```

Request

```json
{
  "email": "john@gmail.com",
  "password": "password123"
}
```

Response

```json
{
  "accessToken": "JWT_TOKEN",
  "refreshToken": "REFRESH_TOKEN"
}
```

---

#### Refresh Token

```http
POST /auth/refresh
```

Request

```json
{
  "refreshToken": "REFRESH_TOKEN"
}
```

---

#### Logout

```http
POST /auth/logout
```

---

### OAuth2 Login

```http
GET /oauth2/authorization/google
```

---

### Protected APIs

#### User Access

```http
GET /notes/user
```

#### Admin Access

```http
GET /notes/admin
```

---

## Running Locally

### Clone Repository

```bash
git clone https://github.com/your-username/secure-auth-x.git
cd secure-auth-x
```

### Create Database

```sql
CREATE DATABASE auth_db;
```

### Run Application

```bash
mvn clean install
mvn spring-boot:run
```

---

## Google OAuth2 Configuration

1. Create OAuth Client in Google Cloud Console
2. Enable Google Identity Services
3. Configure OAuth Consent Screen
4. Add Redirect URI

```text
http://localhost:8080/login/oauth2/code/google
```

5. Add credentials to `application.yml`

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_CLIENT_ID
            client-secret: YOUR_CLIENT_SECRET
```

---

## Key Concepts Implemented

* JWT Authentication
* OAuth2 Authorization Code Flow
* Refresh Token Strategy
* Stateless Security
* Role-Based Access Control
* Service-Layer Authorization
* Spring Security Filter Chain
* Custom Authentication Handlers
* DTO Validation
* Global Exception Handling
* Layered Architecture
