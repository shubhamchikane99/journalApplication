package net.google.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate redisTemplate;

	public String redis() {
		// This API For Test Redis DB

		redisTemplate.opsForValue().set("email", "shubhamchikane41216@gmail.com");

		Object email = redisTemplate.opsForValue().get("salary"); 

		//System.err.println("email " + email);

		String eml = email.toString();

		return eml;
	}

}
