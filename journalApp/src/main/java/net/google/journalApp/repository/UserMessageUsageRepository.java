package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.UserMessageUsage;

@Repository
public interface UserMessageUsageRepository extends JpaRepository<UserMessageUsage, String> {

	@Query(value = " SELECT mu.* FROM user_message_usage mu WHERE mu.sender_id =:senderId AND mu.receiver_id =:receiverId ", nativeQuery = true)
	UserMessageUsage getUserMessageUsageByUserId(@Param("senderId") String senderId,
			@Param("receiverId") String receiverId);

}
