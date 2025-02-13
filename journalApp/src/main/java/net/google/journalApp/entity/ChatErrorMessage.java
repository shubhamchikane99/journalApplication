package net.google.journalApp.entity;

import lombok.Data;

@Data
public class ChatErrorMessage {

	private boolean error;

	private int statusCode;

	private String errorMessage;
}
