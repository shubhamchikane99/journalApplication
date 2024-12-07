package net.google.journalApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ConfigJournalApp;
import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.repository.ConfigJournalAppRepository;

@Service
public class ConfigJournalAppService {

	@Autowired
	private ConfigJournalAppRepository configJournalAppRepository;

	public ConfigJournalApp saveConfigJournalApp(ConfigJournalApp configJournalApp) {
		// Save Journal Entry

		return configJournalAppRepository.save(configJournalApp);
	}

	public List<ConfigJournalApp> getAllConfigJournalApp() {
		// Get All Journal Entry By User

		return configJournalAppRepository.getAllConfigJournalApp();
	}

	public ConfigJournalApp configJournalAppById(String id) {
		// Journal Entry By Id

		Optional<ConfigJournalApp> configJournalAppOpt = configJournalAppRepository.findById(id);

		ConfigJournalApp configJournalApp = configJournalAppOpt
				.orElseThrow(() -> new RuntimeException("ConfigJournalApp not found for id: " + id));

		return configJournalApp;
	}

	public ErrorMessage deleteConfigJournalAppById(String id) {
		// Journal Entry Delete By Id

		ErrorMessage errorMessage = new ErrorMessage();

		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("failed to delete.");

		int result = configJournalAppRepository.deleteConfigJournalAppById(id);

		if (result > 0) {

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("Delete Successfully.");
		}

		return errorMessage;
	}

}
