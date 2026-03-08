package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.UserChatBackgrounds;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.UserChatBackgroundsService;

@RestController
@RequestMapping("v1/user-chat-backgrounds")
public class UserChatBackgroundsController {

	@Autowired
	private UserChatBackgroundsService userChatBackgroundsService;

	@PostMapping
	public ServiceResponse saveUserChatBackgrounds(@RequestBody UserChatBackgrounds userChatBackgrounds) {

		return ServiceResponse.asSuccess(userChatBackgroundsService.saveUserChatBackgrounds(userChatBackgrounds));
	}

	@GetMapping("/by-user")
	public ServiceResponse getUserChatBackgroundsByUsers(@RequestParam("userId") String userId,
			@RequestParam("selectUserId") String selectUserId) {

		return ServiceResponse
				.asSuccess(userChatBackgroundsService.getUserChatBackgroundsByUsers(userId, selectUserId));
	}

}
