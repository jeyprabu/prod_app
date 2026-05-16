package com.example.prod_app.service;

import com.example.prod_app.dto.AuthResponse;
import com.example.prod_app.entity.RefreshToken;
import com.example.prod_app.entity.User;
import com.example.prod_app.exception.BadRequestException;
import com.example.prod_app.repository.RefreshTokenRepository;
import com.example.prod_app.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

	private final long refreshTokenExpiration;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	public RefreshTokenService(@Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration,
			RefreshTokenRepository refreshTokenRepository, JwtService jwtService,
			UserDetailsService userDetailsService) {
		this.refreshTokenExpiration = refreshTokenExpiration;
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Transactional
	public RefreshToken createRefreshToken(User user) {
		refreshTokenRepository.deleteByUser(user);
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUser(user);
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
		return refreshTokenRepository.save(refreshToken);
	}

	public RefreshToken verifyRefreshToken(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
				.orElseThrow(() -> new BadRequestException("Invalid refresh token"));
		if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
			refreshTokenRepository.delete(refreshToken);
			throw new BadRequestException("Refresh token expired");
		}
		return refreshToken;
	}

	public AuthResponse refreshAccessToken(String token) {
		RefreshToken refreshToken = verifyRefreshToken(token);
		User user = refreshToken.getUser();
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
		String accessToken = jwtService.generateToken(userDetails);
		return new AuthResponse(accessToken, refreshToken.getToken());
	}
	
}