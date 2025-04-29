package net.google.journalApp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.DTONotifications;
import net.google.journalApp.entity.Notifications;
import net.google.journalApp.repository.DTONotificationsRepository;
import net.google.journalApp.repository.NotificationsRepository;

@Service
public class NotificationsService {

	@Autowired
	private NotificationsRepository notificationsRepository;

	@Autowired
	private DTONotificationsRepository dtoNotificationsRepository;

	public Notifications saveNotifications(Notifications notifications) {
		Date now = new Date();
		Notifications savedNotification;

		if (notifications.getType() == 1) {
			Notifications prevNoti = notificationsRepository
					.getNotificationBySenderAndReceiverId(notifications.getReceiverId(), notifications.getSenderId());

			if (prevNoti != null) {
				long minutes = (now.getTime() - prevNoti.getDate().getTime()) / (60 * 1000);

				if (minutes <= 5 && minutes >= 0) {
					prevNoti.setMessageCount(prevNoti.getMessageCount() + 1);
					prevNoti.setDate(now);
					savedNotification = notificationsRepository.save(prevNoti);
				} else {
					notifications.setMessageCount(1); // Reset message count if outside 5 minutes
					notifications.setDate(now);
					savedNotification = notificationsRepository.save(notifications);
				}
			} else {
				notifications.setDate(now);
				notifications.setMessageCount(1); // Set message count for new notification
				savedNotification = notificationsRepository.save(notifications);
			}
		} else {
			notifications.setDate(now);
			savedNotification = notificationsRepository.save(notifications);
		}

		return savedNotification;
	}

	public List<DTONotifications> getNotificationOfUsers(String userId) {
		// get user notifications

		return dtoNotificationsRepository.getNotificationOfUsers(userId);
	}
}
