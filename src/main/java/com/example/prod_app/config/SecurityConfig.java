package com.example.prod_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.prod_app.exception.CustomAccessDeniedHandler;
import com.example.prod_app.security.JwtAuthenticationFilter;
import com.example.prod_app.security.OAuth2LoginSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtFilter;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

	public SecurityConfig(JwtAuthenticationFilter jwtFilter, CustomAccessDeniedHandler accessDeniedHandler,
			OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
		this.jwtFilter = jwtFilter;
		this.accessDeniedHandler = accessDeniedHandler;
		this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/**", "/oauth2/**").permitAll()
						.anyRequest().authenticated())
				.oauth2Login(oauth -> oauth.successHandler(oAuth2LoginSuccessHandler))
				.exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}