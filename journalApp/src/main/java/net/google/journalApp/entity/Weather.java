package net.google.journalApp.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {

	private Current current;
	
	private Location location;
	
	@Getter
	@Setter
	public static class Location {
		  
		@JsonProperty("name")
		private String name;
		
		@JsonProperty("timezone_id")
		private String timezoneId;
		
		@JsonProperty("localtime")
		private String localtime;
		
	}

	@Getter
	@Setter
	public static class Current {

		private int temperature;

		@JsonProperty("weather_code")
		private int weatherCode;

		@JsonProperty("weather_descriptions")
		private List<String> weatherDescriptions;

		@JsonProperty("wind_speed")
		private int windSpeed;

		private int feelslike;

		@JsonProperty("is_day")
		private String isDay;
	}
}
