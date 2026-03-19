package net.google.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.UserAccessFeature;
import net.google.journalApp.repository.UserAccessFeatureRepository;

@Service
public class UserAccessFeatureService {

	@Autowired
	private UserAccessFeatureRepository userAccessFeatureRepository;

	public List<UserAccessFeature> getUserAccessFeatureAll() {
		// getUserAccessFeatureAll

		return userAccessFeatureRepository.getUserAccessFeatureAll();
	}
}
