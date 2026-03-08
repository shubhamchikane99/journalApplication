package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.UserMessageUsage;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.UserMessageUsageService;

@RestController
@RequestMapping("v1/user-message-usage")
public class UserMessageUsageController {

	@Autowired
	private UserMessageUsageService userMessageUsageService;

	@PostMapping
	public ServiceResponse saveUserMessageUsage(@RequestBody UserMessageUsage userMessageUsage) {

		return ServiceResponse.asSuccess(userMessageUsageService.saveUserMessageUsage(userMessageUsage));
	}

	@GetMapping("/{senderId}/{receiverId}")
	public ServiceResponse getUserMessageUsageByUserId(@PathVariable("senderId") String senderId,
			@PathVariable("receiverId") String receiverId) {

		return ServiceResponse.asSuccess(userMessageUsageService.getUserMessageUsageByUserId(senderId, receiverId));

	}
}
