package net.google.journalApp.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DTOForUserList {

	private List<DTOUsers> chat = new ArrayList<DTOUsers>();

	private List<DTOUsers> request = new ArrayList<DTOUsers>();

	private List<DTOUsers> allUsers = new ArrayList<DTOUsers>();

}
