package net.google.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.JournalEntry;
import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.JournalEntryRepository;
import net.google.journalApp.repository.UsersRepository;

@Service
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;

	@Autowired
	private UsersRepository userRepository;

	public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
		// Save Journal Entry

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Users findUserName = userRepository.findUserByUserName(userName);

		journalEntry.setUserId(findUserName.getId());

		return journalEntryRepository.save(journalEntry);
	}

	public List<JournalEntry> getAllJournalEntryByUser() {
		// Get All Journal Entry By User

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Users findUserName = userRepository.findUserByUserName(userName);

		return journalEntryRepository.journalEntryByUserId(findUserName.getId());
	}

	public JournalEntry journalEntryById(String id) {
		// Journal Entry By Id

		return journalEntryRepository.journalEntryById(id);
	}

	public ErrorMessage deleteJournalEntryById(String id) {
		// Journal Entry Delete By Id

		ErrorMessage errorMessage = new ErrorMessage();

		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("failed to delete.");

		int result = journalEntryRepository.deleteJournalEntryById(id);

		if (result > 0) {

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("Delete Successfully.");
		}

		return errorMessage;
	}

}
