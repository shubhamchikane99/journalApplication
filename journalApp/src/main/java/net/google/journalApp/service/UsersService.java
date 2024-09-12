package net.google.journalApp.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	public ErrorMessageForUser saveUsers(Users users) {
		// Save Journal Entry

		ErrorMessageForUser errorMessage = new ErrorMessageForUser();
		Users saveUsers = new Users();

		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("User Name Already Exist.");
		errorMessage.setUsers(users);

		Users findUserName = userRepository.findUserByUserName(users.getUserName());

		if (Objects.isNull(findUserName)) {

			saveUsers = userRepository.save(users);

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("User Register Successfully.");
			errorMessage.setUsers(saveUsers);
		}

		return errorMessage;
	}

	public List<Users> getAllUsers() {
		// get All Journal Entry

		return userRepository.findAll();
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

		return userRepository.findUserByUserName(userName);
	}

}
