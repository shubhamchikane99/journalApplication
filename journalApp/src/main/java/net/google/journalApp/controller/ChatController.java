package net.google.journalApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.ChatMessage;

@Controller
public class ChatController {

	private final SimpMessagingTemplate messagingTemplate;

	public ChatController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/private-message")
	public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
	    System.out.println("📩 Message from: " + chatMessage.getSenderId());
	    System.out.println("📩 Message to: " + chatMessage.getReceiverId());
	    System.out.println("📩 Content: " + chatMessage.getContent());

	    // Ensure messages are sent to the correct user destination
	    messagingTemplate.convertAndSendToUser(chatMessage.getReceiverId(), "/private", chatMessage);
	}

}
