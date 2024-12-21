package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.ThirdPartyApiIntegrationService;

@RestController
@RequestMapping("v1/api-integration")
public class ThirdPartyApiIntegrationController {

	@Autowired
	private ThirdPartyApiIntegrationService thirdPartyApiIntegrationService;

	@GetMapping("/weather-api")
	public ServiceResponse weatherApiIntegration(@RequestParam("cityName") String cityName)
			throws ResourceNotFoundException, JsonMappingException, JsonProcessingException {
		return ServiceResponse.asSuccess(thirdPartyApiIntegrationService.weatherApiIntegration(cityName));
	}
}
