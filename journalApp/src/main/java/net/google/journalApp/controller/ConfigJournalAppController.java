package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.ConfigJournalApp;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.ConfigJournalAppService;

@RestController
@RequestMapping("v1/config-journal-app")
public class ConfigJournalAppController {

	@Autowired
	private ConfigJournalAppService configJournalAppService;

	@PostMapping
	public ServiceResponse saveConfigJournalApp(@RequestBody ConfigJournalApp configJournalApp) {

		return ServiceResponse.asSuccess(configJournalAppService.saveConfigJournalApp(configJournalApp));

	}

	@GetMapping("/{id}")
	public ServiceResponse findConfigJournalAppById(@PathVariable("id") String id) {

		return ServiceResponse.asSuccess(configJournalAppService.configJournalAppById(id));

	}

	@DeleteMapping("/{id}")
	public ServiceResponse deleteConfigJournalAppById(@PathVariable("id") String id) {

		return ServiceResponse.asSuccess(configJournalAppService.deleteConfigJournalAppById(id));

	}

}
