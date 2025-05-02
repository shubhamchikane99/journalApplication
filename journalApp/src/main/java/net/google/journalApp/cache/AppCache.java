package net.google.journalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.google.journalApp.entity.ConfigJournalApp;
import net.google.journalApp.repository.ConfigJournalAppRepository;

@Component
public class AppCache { 

	@Autowired
	private ConfigJournalAppRepository configJournalAppRepository;

	public Map<String, String> APP_CACHE; 
 
	@PostConstruct
	public void init() {

		APP_CACHE = new HashMap<>(); 

		//List<ConfigJournalApp> all = new ArrayList<ConfigJournalApp>();
			//configJournalAppRepository.getAllConfigJournalApp();

		//for (ConfigJournalApp obj : all) {
		//APP_CACHE.put(obj.getJournalKey(), obj.getJournalValue());
		//}
	}
}
