package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.Users;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.UsersService;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UsersService usersService;

	@PostMapping("/create-user")
	public ServiceResponse saveUsers(@RequestBody Users users) {

		return ServiceResponse.asSuccess(usersService.saveUsers(users));
	}

	@GetMapping("/health-check")
	public String healthCheck() {

		return "OK";
	}

	@GetMapping("/check")
	public ServiceResponse Check() {

		return ServiceResponse.asSuccess("Hello");
	}
}
