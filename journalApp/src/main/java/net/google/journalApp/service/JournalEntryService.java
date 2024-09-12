package net.google.journalApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.JournalEntry;
import net.google.journalApp.entity.Users;
import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;

	@Autowired
	private UsersService usersService;

	public JournalEntry saveJournalEntry(JournalEntry journalEntry, String userName) {
		// Save Journal Entry

		Users users = new Users();
		JournalEntry saveJournalEntry = new JournalEntry();

		users = usersService.findUsersByUserName(userName);
		saveJournalEntry = journalEntryRepository.save(journalEntry);

		users.setJournalEntryIds(saveJournalEntry.getId());
		System.err.println("users " + users);
		usersService.saveUsers(users);

		return saveJournalEntry;
	}

	public List<JournalEntry> getJournalEntry() {
		// get All Journal Entry

		return journalEntryRepository.findAll();
	}

	public JournalEntry findJournalEntryById(String id) throws ResourceNotFoundException {

		Optional<JournalEntry> journalEntryOpt = journalEntryRepository.findById(id);

		JournalEntry journalEntry = journalEntryOpt
				.orElseThrow(() -> new ResourceNotFoundException("Journal Entry Not found with id " + id));

		return journalEntry;
	}

	public ErrorMessage DeleteJournalEntryById(String id) {
		// Delete by Id

		ErrorMessage errorMessage = new ErrorMessage();

		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("failed to delete.");

		int result = journalEntryRepository.deleteByIdJournalEntry(id);

		if (result > 0) {

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("Delete Successfully.");
		}

		return errorMessage;
	}

}
