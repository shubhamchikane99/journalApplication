package net.google.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.google.journalApp.cache.AppCache;
import net.google.journalApp.entity.Weather;

@Service
public class ThirdPartyApiIntegrationService {

	
	@Value("${weather_key}")
	private String weatherKey;
	

	//private static  String API = "http://api.weatherstack.com/current?access_key=weatherKey&query=city";

	@Autowired
	private RestTemplate restTemplate; 

	@Autowired
	private AppCache appCache;
	
	public Weather weatherApiIntegration(String cityName) {
		// Weather API Integration

		String finalAPI = appCache.APP_CACHE.get("weather_api").replace("city", cityName).replace("weatherKey", weatherKey);

		ResponseEntity<Weather> reponse = restTemplate.exchange(finalAPI, HttpMethod.GET, null, Weather.class);
		Weather weatherRes = reponse.getBody();

		System.err.println("weatherRes " + weatherRes);
		return weatherRes; 
	}

}
