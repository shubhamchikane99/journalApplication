package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.AdminService;

@RestController
@RequestMapping("v1/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping
	public ServiceResponse getAllUsers() {

		return ServiceResponse.asSuccess(adminService.getAllUsers());

	}
}
