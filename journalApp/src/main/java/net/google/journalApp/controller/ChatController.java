package net.google.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import net.google.journalApp.entity.ChatMessage;
import net.google.journalApp.repository.ChatMessageRepository;

@Controller
public class ChatController {

	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	public ChatController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/private-message")
	public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
		System.out.println("📩 Message from: " + chatMessage.getSenderId());
		System.out.println("📩 Message to: " + chatMessage.getReceiverId());
		System.out.println("📩 Content: " + chatMessage.getContent());

		chatMessageRepository.save(chatMessage);

		// Ensure messages are sent to the correct user destination
		messagingTemplate.convertAndSendToUser(chatMessage.getReceiverId(), "/private", chatMessage);
	}

	// Fetch chat history between two users
	@GetMapping("/messages/{senderId}/{receiverId}")
	public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable String senderId,
			@PathVariable String receiverId) {
		List<ChatMessage> messages = chatMessageRepository
				.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestamp(senderId, receiverId);
		return ResponseEntity.ok(messages);
	}

}
