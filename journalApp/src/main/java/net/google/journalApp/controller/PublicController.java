package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.cache.AppCache;
import net.google.journalApp.entity.Users;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.UsersService;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UsersService usersService; 

	@Autowired
	private AppCache appCache;

	@GetMapping("/log-in")
	public ServiceResponse logInUser(@RequestParam("userName") String userName,
			@RequestParam("password") String password) {

		return ServiceResponse.asSuccess(usersService.logInUser(userName, password));
	}

	@PostMapping("/create-user") 
	public ServiceResponse saveUsers(@RequestBody Users users) {

		return ServiceResponse.asSuccess(usersService.saveUsers(users)); 
	}

	@GetMapping("/send-opt")
	public ServiceResponse sendOtp(@RequestParam("emailId") String emailId) {

		return ServiceResponse.asSuccess(usersService.sendOtp(emailId));
	}

	@GetMapping("/validate-otp")
	public ServiceResponse validateOtp(@RequestParam("emailId") String emailId, @RequestParam("otp") String otp) {

		return ServiceResponse.asSuccess(usersService.validateOtp(emailId, otp)); 
	}

	@GetMapping("/get-all")
	public ServiceResponse getAll() {  

		return ServiceResponse.asSuccess(usersService.getAllUsers());
	}

	@GetMapping("/health-check")

	public String healthCheck() {

		return "OK";
	}

	@GetMapping("/clear-app-cache")

	public void clearAppCache() {

		appCache.init();
	}

}
