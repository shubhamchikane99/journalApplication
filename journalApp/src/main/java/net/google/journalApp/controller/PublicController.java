package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.cache.AppCache;
import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.Users;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.UsersDetailsServiceImpl;
import net.google.journalApp.service.UsersService;
import net.google.journalApp.utilis.JwtUtil;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private AppCache appCache;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsersDetailsServiceImpl usersDetailsServiceImpl;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/log-in")
	public ServiceResponse logInUser(@RequestParam("userName") String userName,
			@RequestParam("password") String password) {

		String jwt = "";
		try {

			ErrorMessage errorMessage = usersService.logInUser(userName, password);
			if (errorMessage.getStatusCode() == 200) {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
				UserDetails userDetails = usersDetailsServiceImpl.loadUserByUsername(userName);
				jwt = "Bearer " + jwtUtil.generateToken(userName);

			} else {
				jwt = "Unauthorized";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ServiceResponse.asSuccess(jwt);
	}

	@PostMapping("/create-user")
	public ServiceResponse saveUsers(@RequestBody Users users) {

		return ServiceResponse.asSuccess(usersService.saveUsers(users));
	}

	@GetMapping("/send-otp")
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

	@GetMapping("/check-username")
	public ServiceResponse getCheckUserName(@RequestParam("userName") String userName) {

		return ServiceResponse.asSuccess(usersService.getCheckUserName(userName));
	}

	@GetMapping("/check-email")
	public ServiceResponse getCheckEmailId(@RequestParam("emailId") String emailId) {

		return ServiceResponse.asSuccess(usersService.getCheckEmailId(emailId));
	}

	@GetMapping("/get-name")
	public String getName(@RequestParam("name") String name) {

		return "My Name Is " + name;
	}

}
