package com.example.prod_app.config;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import com.example.prod_app.entity.*;
import com.example.prod_app.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	public CustomOAuth2UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest request) {
		OAuth2User oauthUser = super.loadUser(request);
		String email = oauthUser.getAttribute("email");
		String name = oauthUser.getAttribute("name");
		userRepository.findByEmail(email).orElseGet(() -> {
			User user = new User();
			user.setEmail(email);
			user.setName(name);
			user.setProvider(AuthProvider.GOOGLE);
			user.setRole(Role.ROLE_USER);
			return userRepository.save(user);
		});
		return oauthUser;
	}
	
}