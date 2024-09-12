package net.google.journalApp.entity;

import lombok.Data;

@Data
public class ErrorMessageForUser {

	private boolean error;

	private int statusCode;

	private String errorMessage;

	private Users users;
}
