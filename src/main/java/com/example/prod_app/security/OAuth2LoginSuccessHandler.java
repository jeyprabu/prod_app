package com.example.prod_app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.example.prod_app.service.CustomUserDetailsService;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;
	
	public OAuth2LoginSuccessHandler(JwtService jwtService, CustomUserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.Authentication authentication) throws IOException, ServletException {

		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		String email = oauthToken.getPrincipal().getAttribute("email");
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		String jwt = jwtService.generateToken(userDetails);
		response.setContentType("application/json");
		response.getWriter().write("""
				{
				    "accessToken": "%s"
				}
				""".formatted(jwt));
	}
	
}