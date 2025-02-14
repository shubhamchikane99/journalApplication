package net.google.journalApp.entity;

import lombok.Data;

@Data
public class ErrorMessageForStatus {

	private int status;

	private boolean error;

}
