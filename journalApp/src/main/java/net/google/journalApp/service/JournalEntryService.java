package net.google.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
		System.err.println("UserName Is " + userName);
		Users findUserName = userRepository.findUserByUserName(userName);

		journalEntry.setUserId(findUserName.getId());

		return journalEntryRepository.save(journalEntry);
	}

}
