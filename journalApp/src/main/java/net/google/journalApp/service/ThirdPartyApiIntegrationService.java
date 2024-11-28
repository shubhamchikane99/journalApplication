package net.google.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.google.journalApp.entity.Weather;

@Service
public class ThirdPartyApiIntegrationService {

	private static final String weatherApiKey = "7d7664073fb43abe8381fea1ff1b7a0e";

	private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

	@Autowired
	private RestTemplate restTemplate;

	public Weather weatherApiIntegration(String cityName) {
		// Weather API Integration

		String finalAPI = API.replace("CITY", cityName).replace("weatherApiKey", weatherApiKey);

		ResponseEntity<Weather> reponse = restTemplate.exchange(finalAPI, HttpMethod.GET, null, Weather.class);
		Weather weatherRes = reponse.getBody();

		return weatherRes;
	}

}
