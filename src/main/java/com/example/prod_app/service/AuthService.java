package com.example.prod_app.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.prod_app.dto.*;
import com.example.prod_app.entity.*;
import com.example.prod_app.exception.BadRequestException;
import com.example.prod_app.repository.UserRepository;
import com.example.prod_app.security.JwtService;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;
	private final RefreshTokenService refreshTokenService;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtService jwtService,
			CustomUserDetailsService userDetailsService, RefreshTokenService refreshTokenService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.refreshTokenService = refreshTokenService;
	}

	public void register(RegisterRequest request) {

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new BadRequestException("Email already exists");
		}
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.ROLE_USER);
		user.setProvider(AuthProvider.LOCAL);
		userRepository.save(user);
	}

	public AuthResponse login(LoginRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String accessToken = jwtService.generateToken(userDetails);
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		String refreshToken = refreshTokenService.createRefreshToken(user).getToken();
		return new AuthResponse(accessToken, refreshToken);
	}
	
	public AuthResponse refreshToken(RefreshTokenRequest request) {
		return refreshTokenService.refreshAccessToken(request.getRefreshToken());
	}
	
}