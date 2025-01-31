package net.google.journalApp.entity;

import lombok.Data;

@Data
public class ChatMessage {

	private String senderId;
	private String receiverId; // For private messages
	private String content;
	private String type; // "C
}
