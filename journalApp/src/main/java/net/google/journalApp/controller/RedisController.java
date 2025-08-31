package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.RedisService;

@RestController
@RequestMapping("v1/redis")
public class RedisController {

//	@Autowired
//	private RedisService redisService;
//
//	@GetMapping("/redis-test")
//	public ServiceResponse String() {
//
//		return ServiceResponse.asSuccess(redisService.redis());
//	}

}
