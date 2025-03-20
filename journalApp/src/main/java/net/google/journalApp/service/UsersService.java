package net.google.journalApp.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import net.google.journalApp.constant.Constant;
import net.google.journalApp.entity.EmailOtpErrorMessage;
import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.ErrorMessageForUser;
import net.google.journalApp.entity.GenerateOtp;
import net.google.journalApp.entity.OnlineOfflineStatus;
import net.google.journalApp.entity.Users;
import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.generatotp.GenerateOtpCode;
import net.google.journalApp.repository.UsersRepository;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@Service
@Slf4j
public class UsersService {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private GenerateOtpService generateOtpService;

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

		System.err.println("userName " + userName);

		users = userRepository.findByUserName(userName);

		if (!Objects.isNull(users)) {

			if (passwordEncoder.matches(password, users.getPassword())) {

				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				String auth = userName + ":" + password; // Replace with actual username and password
				String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
				headers.set("Authorization", "Basic " + encodedAuth);
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> entity = new HttpEntity<>(null, headers);

				ResponseEntity<String> response = restTemplate.exchange(
						Constant.backendUrl + "v1/chat-message/" + users.getId() + "/online", HttpMethod.GET, entity,
						String.class);

				OnlineOfflineStatus onlineOfflineStatus = new OnlineOfflineStatus();

				onlineOfflineStatus.setUserId(users.getId());
				onlineOfflineStatus.setActiveInActive(true);

				HttpEntity<OnlineOfflineStatus> entity2 = new HttpEntity<>(onlineOfflineStatus, headers);

				ResponseEntity<String> response2 = restTemplate.postForEntity(
						Constant.backendUrl + "v1/chat-message/online-offline-status", entity2, String.class);

				errorMessage.setError(false);
				errorMessage.setStatusCode(200);
				errorMessage.setErrorMessage("Success");
				errorMessage.setUsers(users);

			}
		}

		return errorMessage;
	}

	public EmailOtpErrorMessage sendOtp(String emailId) {
		// Send Otp By Email id

		GenerateOtp generateOtp = new GenerateOtp();
		EmailOtpErrorMessage errorMessage = new EmailOtpErrorMessage();
		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("Failed For Send Otp");

		Date currentDate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate); // Set the current date and time
		calendar.add(Calendar.MINUTE, 2); // Add 2 minutes
		Date updatedDate = calendar.getTime();

		int otp = GenerateOtpCode.generateOtpCode();

		generateOtp.setEmailId(emailId);
		generateOtp.setOtp(String.valueOf(otp));
		generateOtp.setSendDateTime(currentDate);
		generateOtp.setExpiredDateTime(updatedDate);

		GenerateOtp saveGenerateOtp = generateOtpService.saveGenerateOtp(generateOtp);

		if (!Objects.isNull(saveGenerateOtp)) {

			// Construct the email body
			String subject = "Your OTP Code for Verification";
			String body = "Dear User,\n\n" + "Your One-Time Password (OTP) is: " + otp + "\n\n"
					+ "Please use this OTP to complete your verification.\n\n" + "This OTP is valid for 5 minutes.\n\n"
					+ "Regards,\n" + "Your Google";

			emailService.sendEmail(emailId, subject, body);

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("OTP Send Successfully");
		}

		return errorMessage;
	}

	public EmailOtpErrorMessage validateOtp(String emailId, String otp) {
		// Validate OTP

		EmailOtpErrorMessage errorMessage = new EmailOtpErrorMessage();
		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("Invalid Otp");

		Date currentDate = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentFormatDate = dateFormat.format(currentDate);

		GenerateOtp generateOtp = generateOtpService.getvalidateOtpByEmailId(emailId, otp, currentFormatDate);

		if (!Objects.isNull(generateOtp)) {

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("Validate OTP Successfully");

		}

		return errorMessage;
	}

	public List<Users> usersGetAll() {
		// get all for chat window

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();

		return userRepository.getUserWithoutLogInPerson(userName);
	}

	public ErrorMessage getUsersActiveStatusUpdate(String id) {
		// get Update Users Active Status

		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("Filed to Update ");

		int result = userRepository.getUpdateActiveStatus(id, 0);

		if (result > 0) {

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("Update Sucessfully");
		}

		return errorMessage;
	}

}
