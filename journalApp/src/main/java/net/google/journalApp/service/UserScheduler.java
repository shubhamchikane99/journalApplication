package net.google.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.JournalEntry;
import net.google.journalApp.entity.Users;

@Service
public class UserScheduler {

	@Autowired
	private AdminService adminService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private JournalEntryService journalEntryService;

	//@Scheduled(cron = "0 * * ? * *")
	public void fetchUserAndSendEmail() {

		List<Users> users = adminService.sendSentimate();
		List<JournalEntry> journalEntry = journalEntryService.getAllJournalEntry();

		for (Users u : users) {

			String join = " ";

			for (JournalEntry j : journalEntry) {

				if (u.getId().equals(j.getUserId())) {

					join = String.join(" ", j.getContent());

				}
			}

			emailService.sendEmail(u.getEmail(), "Sentiment For Last", join);
		}
	}
}
