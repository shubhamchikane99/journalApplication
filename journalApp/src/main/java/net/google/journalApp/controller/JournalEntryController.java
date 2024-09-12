package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.JournalEntry;
import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.JournalEntryService;

@RestController
@RequestMapping("v1/journal")
public class JournalEntryController {

	@Autowired
	private JournalEntryService journalEntryService;

	@PostMapping("/{userName}")
	public ServiceResponse saveJournalEntry(@RequestBody JournalEntry journalEntry,
			@PathVariable("userName") String userName) {

		return ServiceResponse.asSuccess(journalEntryService.saveJournalEntry(journalEntry, userName));
	}

	@GetMapping
	public ServiceResponse getAllJournalEntry() {

		return ServiceResponse.asSuccess(journalEntryService.getJournalEntry());
	}

	@GetMapping("/{id}")
	public ServiceResponse findJournalEntryById(@PathVariable("id") String id) throws ResourceNotFoundException {
		return ServiceResponse.asSuccess(journalEntryService.findJournalEntryById(id));
	}

	@DeleteMapping("/{id}")
	public ServiceResponse DeleteJournalEntryById(@PathVariable("id") String id) {

		return ServiceResponse.asSuccess(journalEntryService.DeleteJournalEntryById(id));
	}

}
