package com.example.prod_app.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userProfile() {
		return "User Profile";
	}

	@PreAuthorize("hasRole('ADMIN')")
	public String adminDashboard() {
		return "Admin Dashboard";
	}

	@PreAuthorize("#email == authentication.name")
	public String ownData(String email) {
		return "Private data for " + email;
	}

	@PreAuthorize("hasRole('ADMIN') or #email == authentication.name")
	public String adminOrOwner(String email) {
		return "Admin or Owner Access";
	}
	
}