package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.Users;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.AdminService;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/create-admin-user")
	public ServiceResponse createAdminUser(@RequestBody Users users) {

		return ServiceResponse.asSuccess(adminService.createAdminUser(users));

	}

	@GetMapping("/all-users")
	public ServiceResponse getAllUsers() {

		return ServiceResponse.asSuccess(adminService.getAllUsers());

	}
}
