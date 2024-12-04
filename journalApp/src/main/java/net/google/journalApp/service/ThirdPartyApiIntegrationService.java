package net.google.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.google.journalApp.entity.Weather;

@Service
public class ThirdPartyApiIntegrationService {

	
	@Value("${weather_api_key}")
	private String weatherApiKey;

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
