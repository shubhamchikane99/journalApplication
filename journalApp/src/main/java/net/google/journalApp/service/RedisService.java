package net.google.journalApp.service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate redisTemplate;

	public String redis() {
		// This API For Test Redis DB

		redisTemplate.opsForValue().set("email", "shubhamchikane41216@gmail.com");

		Object email = redisTemplate.opsForValue().get("salary");
		String eml = email.toString();

		return eml;
	}

	public <T> T get(String key, Class<T> entityClass) throws JsonMappingException, JsonProcessingException {
		// Get Key From Redis

		Object obj = redisTemplate.opsForValue().get(key);
		ObjectMapper mapper = new ObjectMapper();
		if (!Objects.isNull(obj)) {
			return mapper.readValue(obj.toString(), entityClass);
		} else {
			return null;
		}
	}

	public void set(String key, Object obj, Long ttl) throws JsonMappingException, JsonProcessingException {
		// Get Key From Redis

		redisTemplate.opsForValue().set(key, obj.toString(), ttl, TimeUnit.SECONDS);
	}

}
