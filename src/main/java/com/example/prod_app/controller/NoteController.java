package com.example.prod_app.controller;

import com.example.prod_app.service.NoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NoteController {

	private final NoteService noteService;

	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	@GetMapping("/user")
	public String userNote() {
		return noteService.getSecretNote();
	}

	@GetMapping("/admin")
	public String adminNote() {
		return noteService.getAdminSecret();
	}
	
}