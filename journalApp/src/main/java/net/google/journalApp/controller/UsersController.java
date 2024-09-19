package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.Users;
import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.UsersService;

@RestController
@RequestMapping("v1/users")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@PostMapping("/update")
	public ServiceResponse updateUser(@RequestBody Users users) {

		return ServiceResponse.asSuccess(usersService.updateUser(users));
	}

	@GetMapping("/{id}")
	public ServiceResponse findUsersById(@PathVariable("id") String id) throws ResourceNotFoundException {
		return ServiceResponse.asSuccess(usersService.findUsersById(id));
	}

	@DeleteMapping("/{id}")
	public ServiceResponse DeleteUsersById(@PathVariable("id") String id) {

		return ServiceResponse.asSuccess(usersService.DeleteUsersById(id));
	}

}
