package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.google.journalApp.entity.ChatMessage;

@Repository
public interface ChatMessageRepository
		extends JpaRepository<ChatMessage, String>, JpaSpecificationExecutor<ChatMessage> {
	
	
	@Query(value = " SELECT \r\n"
			+ "a.* \r\n"
			+ "FROM \r\n"
			+ "(\r\n"
			+ "SELECT cm.* FROM chat_messages cm \r\n"
			+ "    WHERE cm.sender_id =:senderId \r\n"
			+ "    AND cm.receiver_id =:receiverId \r\n"
			+ "UNION\r\n"
			+ "SELECT cm.* FROM chat_messages cm \r\n"
			+ "    WHERE cm.sender_id =:receiverId \r\n"
			+ "    AND cm.receiver_id =:senderId \r\n"
			+ ") a ORDER BY a.insert_date_time ASC ", nativeQuery = true)
	 List<ChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestamp(
	           @Param("senderId") String senderId, @Param("receiverId") String receiverId); 

	
	@Query(value = " SELECT cm.* FROM chat_messages cm WHERE cm.sender_id =:senderId AND cm.receiver_id =:receiverId AND cm.status =:status ORDER BY cm.insert_date_time ASC  ", nativeQuery =  true)
	List<ChatMessage> findBySenderIdAndReceiverIdAndStatus(@Param("senderId")String senderId, @Param("receiverId") String receiverId, @Param("status") String status);


	@Modifying
	@Transactional
	@Query(value = " UPDATE chat_messages cm SET cm.status = 'DELIVERED' WHERE cm.receiver_id =:userId AND cm.status = 'SENT' ", nativeQuery =  true)
	int getDeliveredAllSentMsges(@Param("userId")String userId);


	@Query(value = " SELECT cm.* FROM chat_messages cm WHERE cm.receiver_id =:userId AND cm.status = 'DELIVERED' ORDER BY cm.insert_date_time ASC ", nativeQuery =  true)
	List<ChatMessage> getDeliveredMessages(@Param("userId")String userId);

}
