package net.google.journalApp.entity;

import lombok.Data;

@Data
public class EmailOtpErrorMessage {

	private boolean error;

	private int statusCode;

	private String errorMessage;

}
