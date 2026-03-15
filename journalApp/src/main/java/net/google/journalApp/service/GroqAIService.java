package net.google.journalApp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class GroqAIService {

	@Value("${groq.api.key}")
	private String apiKey;

	@Value("${groq.api.url}")
	private String apiUrl;

	public String askAI(String userMessage) {
		RestTemplate restTemplate = new RestTemplate();

		// Request body — OpenAI format (Groq uses same format)
		Map<String, Object> message = new HashMap<>();
		message.put("role", "user");
		message.put("content", userMessage);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("model", "llama-3.3-70b-versatile");
		requestBody.put("messages", List.of(message));
		requestBody.put("max_tokens", 1024);

		// Auth header — Bearer token (different from Gemini)
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apiKey); // ← key difference from Gemini

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		try {
			ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
			Map body = response.getBody();

			// Parse: body -> choices[0] -> message -> content
			List<Map> choices = (List<Map>) body.get("choices");
			Map firstChoice = choices.get(0);
			Map msg = (Map) firstChoice.get("message");
			return (String) msg.get("content");

		} catch (org.springframework.web.client.HttpClientErrorException e) {
			System.err.println(">>> Groq HTTP error: " + e.getStatusCode());
			System.err.println(">>> Groq error body: " + e.getResponseBodyAsString());
			return "AI assistant is currently unavailable. Please try again later.";
		} catch (Exception e) {
			System.err.println(">>> Groq unknown error: " + e.getClass() + " - " + e.getMessage());
			return "AI assistant is currently unavailable. Please try again later.";
		}
	}
}
