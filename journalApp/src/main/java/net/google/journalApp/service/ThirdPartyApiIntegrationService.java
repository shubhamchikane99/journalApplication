package net.google.journalApp.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.google.journalApp.cache.AppCache;
import net.google.journalApp.entity.Weather;

@Service
public class ThirdPartyApiIntegrationService {

	@Value("${weather_key}")
	private String weatherKey;

	// private static String API =
	// "http://api.weatherstack.com/current?access_key=weatherKey&query=city";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AppCache appCache;

	@Autowired
	private RedisService redisService;

	public Weather weatherApiIntegration(String cityName) throws JsonMappingException, JsonProcessingException {
		// Weather API Integration

		Weather weatherRes = new Weather();
		Weather weather = redisService.get("weather_of_" + cityName, Weather.class);

		if (!Objects.isNull(weather)) {
			

			return weather;

		} else {

			System.err.println("In else ");
			String finalAPI = appCache.APP_CACHE.get("weather_api").replace("<city>", cityName).replace("<weatherKey>",
					weatherKey);
			ResponseEntity<Weather> reponse = restTemplate.exchange(finalAPI, HttpMethod.GET, null, Weather.class);
			weatherRes = reponse.getBody();
			System.err.println("weatherRes " + weatherRes);

			if (!Objects.isNull(weatherRes)) {

				redisService.set("weather_of_" + cityName, weatherRes, (long) 3001);
			}
		}

		return weatherRes;
	}

}
