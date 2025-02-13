package net.google.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.repository.ChatMessageRepository;
import net.google.journalApp.repository.UsersRepository;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private UsersRepository usersRepository;

	public void updateUserStatus(String userId, int status) {
		// Update the Active And InActive Status

		int result = usersRepository.getUpdateActiveStatus(userId, status);

	}

	public int getUserStatus(String userId) {
		// get current User Status

		return usersRepository.getCurrentUserStatus(userId);
	}

}
