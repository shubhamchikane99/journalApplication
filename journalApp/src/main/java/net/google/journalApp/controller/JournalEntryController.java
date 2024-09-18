package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.JournalEntry;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.JournalEntryService;

@RestController
@RequestMapping("v1/journal")
public class JournalEntryController {

	@Autowired
	private JournalEntryService journalEntryService;

	@PostMapping
	public ServiceResponse saveJournalEntry(@RequestBody JournalEntry journalEntry) {

		return ServiceResponse.asSuccess(journalEntryService.saveJournalEntry(journalEntry));

	}

}
