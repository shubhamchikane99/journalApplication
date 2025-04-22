package net.google.journalApp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.DTORequestAccept;
import net.google.journalApp.entity.UserFriends;
import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.repository.UserFriendsRepository;

@Service
public class UserFriendsService {

	@Autowired
	private UserFriendsRepository userFriendsRepository;

	public UserFriends saveUserFriends(UserFriends userFriends) {
		// save User Friends

		userFriends.setRequestDate(new Date());

		return userFriendsRepository.save(userFriends);
	}

	public void accepctRequest(DTORequestAccept requestAccept, UserFriends userFriends) {
		// get Accept the request

		List<UserFriends> saveUsersFriend = new ArrayList<UserFriends>();

		// Update the existing friend request (mark as accepted)
		userFriends.setFlag(requestAccept.getFlag());
		userFriends.setAcceptDate(new Date());
		saveUsersFriend.add(userFriends);

		// Create reverse friendship (mutual friend entry)
		UserFriends users = new UserFriends();
		users.setUserId(userFriends.getRequestUserId());
		users.setRequestUserId(userFriends.getUserId());
		users.setFlag(1);

		saveUsersFriend.add(users);

		userFriendsRepository.saveAll(saveUsersFriend);

	}

	public UserFriends findUserFriendsById(String id) throws ResourceNotFoundException {

		Optional<UserFriends> userFriendsOpt = userFriendsRepository.findById(id);

		UserFriends userFriends = userFriendsOpt
				.orElseThrow(() -> new ResourceNotFoundException("Friend request not found with ID: " + id));

		return userFriends;
	}

	public void rejectRequest(DTORequestAccept requestAccept, UserFriends userFriends) {
		// reject Request

		userFriends.setRejectDate(new Date());
		userFriends.setFlag(requestAccept.getFlag());

		userFriendsRepository.save(userFriends);
	}

}
