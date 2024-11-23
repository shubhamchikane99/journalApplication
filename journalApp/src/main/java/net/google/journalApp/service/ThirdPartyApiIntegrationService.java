package net.google.journalApp.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.google.journalApp.constant.Constant;
import net.google.journalApp.entity.Weather;

@Service
public class ThirdPartyApiIntegrationService {

	public Weather weatherApiIntegration(String cityName) {
		// Weather API Integration

		RestTemplate restTemplate = new RestTemplate();

		String finalAPI = Constant.API.replace("CITY", cityName).replace("API_KEY", Constant.weatherApiKey);

		ResponseEntity<Weather> reponse = restTemplate.exchange(finalAPI, HttpMethod.GET, null, Weather.class);
		Weather weatherRes = reponse.getBody();

		return weatherRes;
	}

}
