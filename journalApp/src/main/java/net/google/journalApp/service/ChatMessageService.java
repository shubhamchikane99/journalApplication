package net.google.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ChatMessage;
import net.google.journalApp.repository.ChatMessageRepository;
import net.google.journalApp.repository.UsersRepository;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private UsersRepository usersRepository;

	public void saveChatMessage(ChatMessage chatMessage) {
		// Save ChatMessage

		int userStatus = getUserStatus(chatMessage.getReceiverId());

		if (userStatus == 1) {

			chatMessage.setStatus("DELIVERED");
		}

		chatMessageRepository.save(chatMessage);
	}

	public void updateUserStatus(String userId, int status) {
		// Update the Active And InActive Status and update all user sent msg delivered

		if (status == 1) {
			int deliveredAllSentMsg = chatMessageRepository.getDeliveredAllSentMsges(userId);
			System.err.println("deliveredAllSentMsg " + deliveredAllSentMsg);
		}

		int result = usersRepository.getUpdateActiveStatus(userId, status);

	}

	public int getUserStatus(String userId) {
		// get current User Status

		return usersRepository.getCurrentUserStatus(userId);

	}

	public List<ChatMessage> getDeliveredMessages(String userId) {
		// get All User Delivered Messages

		return chatMessageRepository.getDeliveredMessages(userId);
	}
}
