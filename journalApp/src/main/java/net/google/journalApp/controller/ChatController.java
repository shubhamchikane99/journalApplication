package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.ChatMessage;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.repository.ChatMessageRepository;

@RestController
@RequestMapping("v1/chat-message")
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
	public ServiceResponse  getMessages(@PathVariable String senderId,
			@PathVariable String receiverId) {
//		List<ChatMessage> messages = chatMessageRepository
//				.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestamp(senderId, receiverId);
	//	return ResponseEntity.ok(messages);
		
		return ServiceResponse.asSuccess(chatMessageRepository
				.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestamp(senderId, receiverId));
		
	}

}
