package net.google.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.DTORequestAccept;
import net.google.journalApp.entity.DTOUsers;
import net.google.journalApp.entity.Notifications;
import net.google.journalApp.entity.UserFriends;
import net.google.journalApp.exception.ResourceNotFoundException;
import net.google.journalApp.service.NotificationsService;
import net.google.journalApp.service.UserFriendsService;
import net.google.journalApp.service.UsersService;

@RestController
@RequestMapping("v1/user-friends")
public class UserFriendsController {

	private final SimpMessagingTemplate messagingTemplate;

	public UserFriendsController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Autowired
	private UserFriendsService userFriendsService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private NotificationsService notificationsService;

	@MessageMapping("/send-friend-request")
	public void saveUserFriends(@Payload UserFriends userFriends) {

		// save the send users request in db
		userFriendsService.saveUserFriends(userFriends);

		// send notification to users
		Notifications notifications = new Notifications();
		notifications.setSenderId(userFriends.getRequestUserId());
		notifications.setReceiverId(userFriends.getUserId());
		notifications.setType(0);

		notificationsService.saveNotifications(notifications);

		List<DTOUsers> userRequestList = usersService.getRequestUserList(userFriends.getUserId());
		List<DTOUsers> getUsersListWithSendRequestFlag = usersService
				.getUsersListWithSendRequestFlag(userFriends.getRequestUserId());

		List<DTOUsers> getUsersListWithSendRequestFlag1 = usersService
				.getUsersListWithSendRequestFlag(userFriends.getUserId());

		// update user Request list real time
		String destination1 = "/topic/user-request-list/" + userFriends.getUserId();
		messagingTemplate.convertAndSend(destination1, userRequestList);

		// update all used list update connect to pending status
		String destination2 = "/topic/user-list-with-request-flag/" + userFriends.getRequestUserId();
		messagingTemplate.convertAndSend(destination2, getUsersListWithSendRequestFlag);

		// update used list who send request user not showing in userList
		String destination3 = "/topic/user-list-with-request-flag/" + userFriends.getUserId();
		messagingTemplate.convertAndSend(destination3, getUsersListWithSendRequestFlag1);

	}

	@MessageMapping("/accept-friend-request")
	public void accepctRequest(@Payload DTORequestAccept requestAccept) throws ResourceNotFoundException {

		UserFriends userFriends = userFriendsService.findUserFriendsById(requestAccept.getRequestId());
		userFriendsService.accepctRequest(requestAccept, userFriends);

		Notifications notifications = new Notifications();

		notifications.setSenderId(userFriends.getUserId());
		notifications.setReceiverId(userFriends.getRequestUserId());
		notifications.setType(2);
		notificationsService.saveNotifications(notifications);

		List<DTOUsers> userRequestList = usersService.getRequestUserList(userFriends.getUserId());
		List<DTOUsers> accpectRequestUsers = usersService.getAcceptRequestUsersList(userFriends.getUserId());
		List<DTOUsers> sendRequestUsers = usersService.getAcceptRequestUsersList(userFriends.getRequestUserId());

		List<DTOUsers> getUsersListWithSendRequestFlag1 = usersService
				.getUsersListWithSendRequestFlag(userFriends.getUserId());

		List<DTOUsers> getUsersListWithSendRequestFlag = usersService
				.getUsersListWithSendRequestFlag(userFriends.getRequestUserId());

		// update user Request list real time
		String destination1 = "/topic/user-request-list-update/" + userFriends.getUserId();
		messagingTemplate.convertAndSend(destination1, userRequestList);

		// update accept request friend list for self
		String destination2 = "/topic/accept-request-users-list/" + userFriends.getUserId();
		messagingTemplate.convertAndSend(destination2, accpectRequestUsers);

		// update accept request friend list for who send request
		String destination3 = "/topic/accept-request-users-list/" + userFriends.getRequestUserId();
		messagingTemplate.convertAndSend(destination3, sendRequestUsers);

		// update all friend list who send request
		String destination4 = "/topic/user-list-with-request-flag/" + userFriends.getRequestUserId();
		messagingTemplate.convertAndSend(destination4, getUsersListWithSendRequestFlag);

		// update all friend list for self
		String destination5 = "/topic/user-list-with-request-flag/" + userFriends.getUserId();
		messagingTemplate.convertAndSend(destination5, getUsersListWithSendRequestFlag1);

	}

	@MessageMapping("/reject-friend-request")
	public void rejectRequest(@Payload DTORequestAccept requestAccept) throws ResourceNotFoundException {

		UserFriends userFriends = userFriendsService.findUserFriendsById(requestAccept.getRequestId());
		userFriendsService.rejectRequest(requestAccept, userFriends);

		Notifications notifications = new Notifications();
		notifications.setSenderId(userFriends.getUserId());
		notifications.setReceiverId(userFriends.getRequestUserId());
		notifications.setType(3);
		notificationsService.saveNotifications(notifications);

		List<DTOUsers> getUsersListWithSendRequestFlag1 = usersService
				.getUsersListWithSendRequestFlag(userFriends.getUserId());

		List<DTOUsers> getUsersListWithSendRequestFlag = usersService
				.getUsersListWithSendRequestFlag(userFriends.getRequestUserId());

		List<DTOUsers> userRequestList = usersService.getRequestUserList(userFriends.getUserId());

		String destination2 = "/topic/user-list-with-request-flag/" + userFriends.getRequestUserId();
		messagingTemplate.convertAndSend(destination2, getUsersListWithSendRequestFlag);

		String destination3 = "/topic/user-list-with-request-flag/" + userFriends.getUserId();
		messagingTemplate.convertAndSend(destination3, getUsersListWithSendRequestFlag1);

		String destination1 = "/topic/user-request-list-update/" + userFriends.getUserId();
		messagingTemplate.convertAndSend(destination1, userRequestList);

	}
}
