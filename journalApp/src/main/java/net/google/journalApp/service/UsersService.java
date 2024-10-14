package net.google.journalApp.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.ErrorMessageForUser;
import net.google.journalApp.entity.Users;
import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository userRepository;  

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ErrorMessageForUser saveUsers(Users users) {
		// Save Journal Entry

		users.setPassword(passwordEncoder.encode(users.getPassword()));
		ErrorMessageForUser errorMessage = new ErrorMessageForUser();
		Users saveUsers = new Users();

		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("User Name Already Exist.");
		errorMessage.setUsers(users);

		Users findUserName = userRepository.findByUserName(users.getUserName());

		if (Objects.isNull(findUserName)) {

			saveUsers = userRepository.save(users);

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("User Register Successfully.");
			errorMessage.setUsers(saveUsers);
		}

		return errorMessage;
	}

	public Users updateUser(Users users) {
		// Update UserName And Password

		Users saveUsers = new Users();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Users findUserName = userRepository.findByUserName(userName);
		findUserName.setUserName(users.getUserName());
		findUserName.setPassword(passwordEncoder.encode(users.getPassword()));
		saveUsers = userRepository.save(findUserName);

		return saveUsers;
	}

	public Users findUsersById(String id) throws ResourceNotFoundException {

		Optional<Users> usersOpt = userRepository.findById(id);

		Users Users = usersOpt.orElseThrow(() -> new ResourceNotFoundException("Users Not found with id " + id));

		return Users;
	}

	public ErrorMessage DeleteUsersById(String id) {
		// Delete by Id

		ErrorMessage errorMessage = new ErrorMessage();

		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("failed to delete.");

		int result = userRepository.deleteByIdUsers(id);

		if (result > 0) {

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("Delete Successfully.");
		}

		return errorMessage;
	}

	public Users findUsersByUserName(String userName) {
		// Find User By User Name

		return userRepository.findByUserName(userName);
	}
	
	
	public List<Users> getAllUsers() {
		//Get All User For Admin

		return userRepository.getAllUsers();
	}

}
