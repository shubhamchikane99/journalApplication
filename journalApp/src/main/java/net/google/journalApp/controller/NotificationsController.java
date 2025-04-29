package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.Notifications;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.NotificationsService;

@RestController
@RequestMapping("v1/notifications")
public class NotificationsController {

	@Autowired
	private NotificationsService notificationsService;

	@PostMapping
	public ServiceResponse saveNotifications(@RequestBody Notifications notifications) {

		return ServiceResponse.asSuccess(notificationsService.saveNotifications(notifications));
	}

	@GetMapping("/by-user")
	public ServiceResponse getNotificationOfUsers(@RequestParam("userId") String userId) {

		return ServiceResponse.asSuccess(notificationsService.getNotificationOfUsers(userId));
	}
}
