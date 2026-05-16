package com.example.prod_app.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.prod_app.dto.AuthResponse;
import com.example.prod_app.dto.LoginRequest;
import com.example.prod_app.dto.RefreshTokenRequest;
import com.example.prod_app.dto.RegisterRequest;
import com.example.prod_app.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public String register(@Valid @RequestBody RegisterRequest request) {
		authService.register(request);
		return "User registered";
	}

	@PostMapping("/login")
	public AuthResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}

	@PostMapping("/refresh")
	public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
		return authService.refreshToken(request);
	}

}