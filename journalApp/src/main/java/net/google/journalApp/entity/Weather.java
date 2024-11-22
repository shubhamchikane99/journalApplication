package net.google.journalApp.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Weather {

	private Current current;

	@Data
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
