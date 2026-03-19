package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.UserAccessFeatureService;

@RestController
@RequestMapping("v1/user_access_feature")
public class UserAccessFeatureController {

	@Autowired
	private UserAccessFeatureService userAccessFeatureService;

	@GetMapping("/get-all")
	public ServiceResponse getUserAccessFeatureAll() {

		return ServiceResponse.asSuccess(userAccessFeatureService.getUserAccessFeatureAll());
	}

}
