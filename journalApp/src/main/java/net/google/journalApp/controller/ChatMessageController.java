package net.google.journalApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.ChatMessage;
import net.google.journalApp.entity.DTOChatMessage;
import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.Notifications;
import net.google.journalApp.entity.OnlineOfflineStatus;
import net.google.journalApp.entity.TypingStatus;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.repository.ChatMessageRepository;
import net.google.journalApp.service.ChatMessageService;
import net.google.journalApp.service.NotificationsService;

@RestController
@RequestMapping("v1/chat-message")
public class ChatMessageController {

	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private ChatMessageService chatMessageService;

	@Autowired
	private NotificationsService notificationsService;

	public ChatMessageController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	// Send Private Message to user
	@MessageMapping("/private-message")
	public void sendPrivateMessage(@Payload ChatMessage chatMessage) {

		ChatMessage saveChatMessage = new ChatMessage();
		// Save Chat's
		saveChatMessage = chatMessageService.saveChatMessage(chatMessage);

		Notifications notifications = new Notifications();
		notifications.setSenderId(chatMessage.getSenderId());
		notifications.setReceiverId(chatMessage.getReceiverId());
		notifications.setType(1);

		notificationsService.saveNotifications(notifications);

		// unread notification count
		int unreadNotification = notificationsService.notificationUnreadCount(chatMessage.getReceiverId());

		DTOChatMessage message = new DTOChatMessage();

		message = chatMessageService.getMessageById(saveChatMessage.getId());
		// Ensure messages are sent to the correct user destination
		messagingTemplate.convertAndSendToUser(chatMessage.getReceiverId(), "/private", message);

		// unread message notification
		String destination = "/topic/unread-msg/" + chatMessage.getReceiverId();
		messagingTemplate.convertAndSend(destination, chatMessage.getReceiverId());

		// unread message for private message
		String destination1 = "/topic/private-unread-msg/" + chatMessage.getReceiverId();
		messagingTemplate.convertAndSend(destination1, chatMessage.getSenderId());

		// unread message notification
		String notidestination = "/topic/unread-notification/" + chatMessage.getReceiverId();
		messagingTemplate.convertAndSend(notidestination, unreadNotification);

		// Update edit message
		String editDestination = "/topic/" + message.getReceiverId() + "/update-edited-message";
		messagingTemplate.convertAndSend(editDestination, message);

	}

	// Fetch chat history between two users
	@GetMapping("/messages/{senderId}/{receiverId}")
	public ServiceResponse getMessages(@PathVariable String senderId, @PathVariable String receiverId) {

		return ServiceResponse.asSuccess(chatMessageService
				.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestamp(senderId, receiverId));

	}

	// Typing Status API SHowing another user current used is typing
	@MessageMapping("/typing-status")
	public void sendTypingStatus(@Payload TypingStatus typingStatus) {

		// Send typing status to receiver
		messagingTemplate.convertAndSendToUser(typingStatus.getReceiverId(), "/isTyping", typingStatus);
	}

	// user Active Status
	@GetMapping("/user-status/{userId}")
	public ServiceResponse getUserStatus(@PathVariable String userId) {

		return ServiceResponse.asSuccess(chatMessageService.getUserStatus(userId));
	}

	// Mark user as ONLINE
	@GetMapping("/{userId}/online")
	public ResponseEntity<String> setUserOnline(@PathVariable String userId) {

		chatMessageService.updateUserStatus(userId, 1);

		List<ChatMessage> getDeliveredMessages = new ArrayList<ChatMessage>();
		getDeliveredMessages = chatMessageService.getDeliveredMessages(userId);

		// Notify sender for each delivered message
		if (!getDeliveredMessages.isEmpty()) {

			getDeliveredMessages.forEach(deliveredMessage -> {
				String senderDestination = "/user/" + deliveredMessage.getSenderId() + "/message-delivery";
				messagingTemplate.convertAndSend(senderDestination, deliveredMessage);
			});
		}

		return ResponseEntity.ok("User is now online");
	}

	// Mark user as OFFLINE
	@GetMapping("/{userId}/offline")
	public ResponseEntity<String> setUserOffline(@PathVariable String userId) {
		chatMessageService.updateUserStatus(userId, 0);
		return ResponseEntity.ok("User is now offline");
	}

	// Delivered messages
	@GetMapping("/update-delivered-status/{userId}")
	public ServiceResponse getDeliveredMessages(@PathVariable String userId) {

		List<ChatMessage> getDeliveredMessages = new ArrayList<ChatMessage>();

		getDeliveredMessages = chatMessageService.getDeliveredMessages(userId);

		// Notify sender for each delivered message
		getDeliveredMessages.forEach(deliveredMessage -> {
			String senderDestination = "/user/" + deliveredMessage.getSenderId() + "/message-delivery";
			messagingTemplate.convertAndSend(senderDestination, deliveredMessage);
		});

		return ServiceResponse.asSuccess(getDeliveredMessages);
	}

	// Mark messages as SEEN
	@GetMapping("/mark-seen/{senderId}/{receiverId}")
	public void markMessagesAsSeen(@PathVariable String senderId, @PathVariable String receiverId) {

		chatMessageService.updateTheSeenStatus(senderId, receiverId);

		List<ChatMessage> chatMessages = chatMessageRepository
				.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestamp(senderId, receiverId);

		int remainingUnreadMsgCount = chatMessageService.getUnreadMsgOfUser(senderId);

		String destination = "/topic/read-msg/" + senderId;
		messagingTemplate.convertAndSend(destination, remainingUnreadMsgCount);

		// Notify sender for each delivered message
		chatMessages.forEach(deliveredMessage -> {
			String senderDestination = "/user/" + deliveredMessage.getSenderId() + "/message-delivery";
			messagingTemplate.convertAndSend(senderDestination, deliveredMessage);
		});
	}

	// Online Offline Status
	@PostMapping("/online-offline-status")
	public void userOnlineOfflineStatus(@RequestBody OnlineOfflineStatus onlineOfflineStatus) {

		List<String> onlineUsersStatus = chatMessageService.getOnlineUsersStatus(onlineOfflineStatus);

		String destination = "/topic/online-offline-user";
		messagingTemplate.convertAndSend(destination, onlineUsersStatus);
	}

	@GetMapping("/unread-msg")
	public ServiceResponse getUnreadMsgOfUser(@RequestParam("userId") String userId) {

		return ServiceResponse.asSuccess(chatMessageService.getUnreadMsgOfUser(userId));

	}

	@GetMapping("/delete-message/{messageId}")
	public ServiceResponse deleteMessageById(@PathVariable String messageId, @RequestParam("flag") int flag) {

		ErrorMessage errMessage = chatMessageService.deleteMessageById(messageId, flag);

		DTOChatMessage message = new DTOChatMessage();

		message = chatMessageService.getMessageById(messageId);

		String destination = "/topic/" + message.getReceiverId() + "/update-delete-message";

		// Sends the full message as JSON to the frontend
		messagingTemplate.convertAndSend(destination, message);

		return ServiceResponse.asSuccess(errMessage);
	}

}
