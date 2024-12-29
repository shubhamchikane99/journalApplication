package net.google.journalApp.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.ErrorMessageForUser;
import net.google.journalApp.entity.Users;
import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.repository.UsersRepository;

@Service
@Slf4j
public class UsersService {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Used When Not Configured in pom file
	// private static final Logger logger =
	// LoggerFactory.getLogger(JournalEntryService.class);

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

//		log.error("error occurred");
//		log.info("hahahhahhahhahahahhahahahhah");
//		log.warn("hahahhahhahhahahahhahahahhah");
//		log.debug("hahahhahhahhahahahhahahahhah");
//		log.trace("hahahhahhahhahahahhahahahhah");

		return errorMessage;
	}

	public Users updateUser(Users users) {
		// Update UserName And Password

		Users saveUsers = new Users();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
//		Users findUserName = userRepository.findByUserName(userName);
//		findUserName.setUserName(users.getUserName());
//		findUserName.setPassword(passwordEncoder.encode(users.getPassword()));
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		saveUsers = userRepository.save(users);

		return saveUsers;
	}

	public Users findUsersById(String id) throws ResourceNotFoundException {

		Optional<Users> usersOpt = userRepository.findById(id);

		Users Users = usersOpt.orElseThrow(() -> new ResourceNotFoundException("Users Not found with id " + id));

		return Users;
	}

	public ErrorMessage deleteUsersById(String id) {
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
		// Get All User For Admin

		System.err.println("in API");
		return userRepository.getAllUsers();
	}

	public ErrorMessage logInUser(String userName, String password) {
		// login API

		ErrorMessage errorMessage = new ErrorMessage();
		Users users = new Users();
		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("Invalid User Name And Password");

		users = userRepository.findByUserName(userName);

		if (!Objects.isNull(users)) {

			if (passwordEncoder.matches(password, users.getPassword())) {

				errorMessage.setError(false);
				errorMessage.setStatusCode(200);
				errorMessage.setErrorMessage("Success");
				errorMessage.setUsers(users);

			}
		}

		return errorMessage;
	}

}
