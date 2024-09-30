package net.google.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ErrorMessageForUser;
import net.google.journalApp.entity.Users;

@Service
public class AdminService {

	@Autowired
	private UsersService userService;

	public ErrorMessageForUser createAdminUser(Users users) {

		return userService.saveUsers(users);
	}

	public List<Users> getAllUsers() {
		// Get All User For Admin 

		return userService.getAllUsers();
	}
}
