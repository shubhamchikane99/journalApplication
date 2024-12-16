package net.google.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.ErrorMessageForUser;
import net.google.journalApp.entity.JournalEntry;
import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.UsersRepository;

@Service
public class AdminService {

	@Autowired
	private UsersService userService;

	@Autowired
	private JournalEntryService journalEntryService;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private EmailService emailService;

	public ErrorMessageForUser createAdminUser(Users users) {

		return userService.saveUsers(users);
	}

	public List<Users> getAllUsers() {
		// Get All User For Admin

		return userService.getAllUsers();
	}

	public ErrorMessage deleteAdminById(String id) {
		// Delete Admin BY Id

		ErrorMessage errorMessage = userService.deleteUsersById(id);

		return errorMessage;
	}

	public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
		// Save Journal Entry

		return journalEntryService.saveJournalEntry(journalEntry);
	}

	public List<Users> sendSentimate() {
		// Send Sentiment

		return usersRepository.sendSentimentAnalysis();
	}

	public String sendSentimate(String toMail, String subject, String body) {
		// Send Mail To User

		emailService.sendEmail(toMail, subject, body);

		return "Send Mail Successfully ! ";
	}

	public Object getAgetAllJournalEntryllUsers() {
		// TODO Auto-generated method stub
		return null;
	}
}
