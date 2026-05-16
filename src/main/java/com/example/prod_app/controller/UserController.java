package com.example.prod_app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("/profile")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String profile() {
		return "User Profile";
	}
	
}