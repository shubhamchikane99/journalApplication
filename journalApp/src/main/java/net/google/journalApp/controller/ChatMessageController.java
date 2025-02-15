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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.ChatMessage;
import net.google.journalApp.entity.TypingStatus;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.repository.ChatMessageRepository;
import net.google.journalApp.service.ChatMessageService;

@RestController
@RequestMapping("v1/chat-message")
public class ChatMessageController {

	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private ChatMessageService chatMessageService;

	public ChatMessageController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/private-message")
	public void sendPrivateMessage(@Payload ChatMessage chatMessage) {

		// Save Chat's
		chatMessageService.saveChatMessage(chatMessage);

		// Ensure messages are sent to the correct user destination
		messagingTemplate.convertAndSendToUser(chatMessage.getReceiverId(), "/private", chatMessage);
	}

	// Fetch chat history between two users
	@GetMapping("/messages/{senderId}/{receiverId}")
	public ServiceResponse getMessages(@PathVariable String senderId, @PathVariable String receiverId) {

		return ServiceResponse.asSuccess(chatMessageRepository
				.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestamp(senderId, receiverId));

	}

	@MessageMapping("/typing-status")
	public void sendTypingStatus(@Payload TypingStatus typingStatus) {

		// Send typing status to receiver
		messagingTemplate.convertAndSendToUser(typingStatus.getReceiverId(), "/isTyping", typingStatus);
	}

	@GetMapping("/user-status/{userId}")
	public ServiceResponse getUserStatus(@PathVariable String userId) {
		System.err.println("IN  User status");

		return ServiceResponse.asSuccess(chatMessageService.getUserStatus(userId));
	}

	// ✅ Mark user as ONLINE
	@GetMapping("/{userId}/online")
	public ResponseEntity<String> setUserOnline(@PathVariable String userId) {
		System.err.println("IN Online User");
		chatMessageService.updateUserStatus(userId, 1);
		return ResponseEntity.ok("User is now online");
	}

	// 🔴 Mark user as OFFLINE
	@GetMapping("/{userId}/offline")
	public ResponseEntity<String> setUserOffline(@PathVariable String userId) {
		System.err.println("IN Offline User");
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
}
