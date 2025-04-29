package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.Notifications;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, String> {

	
	@Query(value = " SELECT n.* FROM notifications n WHERE n.sender_id =:senderId AND n.receiver_id =:receiverId AND n.is_read = 0 AND n.type = 1 ORDER BY n.insert_date_time DESC LIMIT 1 ", nativeQuery = true)
	Notifications getNotificationBySenderAndReceiverId(@Param("receiverId") String receiverId, @Param("senderId") String senderId);

}
