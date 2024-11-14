package net.google.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.ErrorMessageForUser;
import net.google.journalApp.entity.JournalEntry;
import net.google.journalApp.entity.Users;

@Service
public class AdminService {

	@Autowired
	private UsersService userService;

	@Autowired
	private JournalEntryService journalEntryService;

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
}
