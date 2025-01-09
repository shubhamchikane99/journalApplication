package net.google.journalApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.GenerateOtp;
import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.repository.GenerateOtpRepository;

@Service
public class GenerateOtpService {

	@Autowired
	private GenerateOtpRepository generateOtpRepository;

	public GenerateOtp saveGenerateOtp(GenerateOtp GenerateOtp) {
		// Save Generate OTP

		return generateOtpRepository.save(GenerateOtp);
	}

	public List<GenerateOtp> getAllGenerateOtpByUser() {
		// Get All Journal Entry By User

		return generateOtpRepository.findAll();
	}

	public GenerateOtp GenerateOtpById(String id) throws ResourceNotFoundException {
		// Journal Entry By Id

		Optional<GenerateOtp> generateOtpOpt = generateOtpRepository.findById(id);

		GenerateOtp generateOtp = generateOtpOpt
				.orElseThrow(() -> new ResourceNotFoundException("Generate Not found with id " + id));

		return generateOtp;
	}

	public ErrorMessage deleteGenerateOtpById(String id) {
		// Journal Entry Delete By Id

		ErrorMessage errorMessage = new ErrorMessage();

		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("failed to delete.");

		int result = generateOtpRepository.deleteGenerateOtpById(id);

		if (result > 0) {

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("Delete Successfully.");
		}

		return errorMessage;
	}

	public GenerateOtp getvalidateOtpByEmailId(String emailId, String otp, String currentFormatDateTime,
			String add2MinutesInDateTime) {
		// Validate OTP By Email Id

		return generateOtpRepository.getvalidateOtpByEmailId(emailId, otp, currentFormatDateTime,
				add2MinutesInDateTime);
	}
}
