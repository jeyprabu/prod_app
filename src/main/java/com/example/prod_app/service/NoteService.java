package com.example.prod_app.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String getSecretNote() {
		return """
				This is a protected note.
				Only authenticated users can see this.
				""";
	}

	@PreAuthorize("hasRole('ADMIN')")
	public String getAdminSecret() {
		return """
				This is an admin secret.
				Only admins can access this.
				""";
	}
	
}