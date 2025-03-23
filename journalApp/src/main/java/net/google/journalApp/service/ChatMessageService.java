package net.google.journalApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ChatMessage;
import net.google.journalApp.entity.OnlineOfflineStatus;
import net.google.journalApp.repository.ChatMessageRepository;
import net.google.journalApp.repository.UsersRepository;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private UsersRepository usersRepository;

	private static Set<String> onlineUsers = ConcurrentHashMap.newKeySet(); // Store online users

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

	public void updateTheSeenStatus(String senderId, String receiverId) {
		// Update The Msg Seen Status

		int result = chatMessageRepository.getUpdateMessagesStatus(senderId, receiverId);
	}

	public List<String> getOnlineUsersStatus(OnlineOfflineStatus onlineOfflineStatus) {
		// online offline user

		if (onlineOfflineStatus.getActiveInActive()) {
			// Add user to online set

		} else {

			onlineUsers.remove(onlineOfflineStatus.getUserId()); // Remove user if offline
		}

		return new ArrayList<>(onlineUsers);
	}

	public int getUnreadMsgOfUser(String userId) {
		// get User Unread messages

		return chatMessageRepository.getUnreadMsgOfUserByUserId(userId);
	}
}
