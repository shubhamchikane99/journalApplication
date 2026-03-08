package net.google.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.UserChatBackgrounds;
import net.google.journalApp.entity.Users;
import net.google.journalApp.repository.UserChatBackgroundsRepository;
import net.google.journalApp.repository.UsersRepository;

@Service
public class UserChatBackgroundsService {

	@Autowired
	private UserChatBackgroundsRepository userChatBackgroundsRepository;

	@Autowired
	private UsersRepository userRepository;

	public UserChatBackgrounds saveUserChatBackgrounds(UserChatBackgrounds userChatBackgrounds) {
		// save user chat background

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Users findUserName = userRepository.findByUserName(userName);
		userChatBackgrounds.setInsertedUserId(findUserName.getId());

		return userChatBackgroundsRepository.save(userChatBackgrounds);
	}

	public UserChatBackgrounds getUserChatBackgroundsByUsers(String userId, String selectUserId) {
		// get chat background for selected user

		return userChatBackgroundsRepository.getUserChatBackgroundsByUsers(userId, selectUserId);
	}
}
