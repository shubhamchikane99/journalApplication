package net.google.journalApp.generatotp;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GenerateOtpCode {

	public static int generateOtpCode() {
		Random random = new Random();
		return 1000 + random.nextInt(9000); // Generates a number between 1000 and 9999
	}

}
