package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.DTOChatMessage;

@Repository
public interface DTOChatMessageRepository extends JpaRepository<DTOChatMessage, String> {

	
	@Query(value = " SELECT\r\n"
			+ "    a.*\r\n"
			+ "FROM\r\n"
			+ "    (\r\n"
			+ "    SELECT\r\n"
			+ "        rm.content AS reply_message,\r\n"
			+ "        rm.sender_id AS reply_message_sender_id,\r\n"
			+ "        cm.*\r\n"
			+ "    FROM\r\n"
			+ "        chat_messages cm LEFT JOIN chat_messages rm ON rm.id = cm.reply_to_message_id\r\n"
			+ "    WHERE\r\n"
			+ "       ((cm.sender_id =:senderId AND cm.receiver_id =:receiverId) \r\n"
			+ "     OR( cm.sender_id =:receiverId AND cm.receiver_id =:senderId))\r\n"
			+ "      \r\n"
			+ "    ORDER BY  cm.insert_date_time DESC\r\n"
			+ "LIMIT 25\r\n"
			+ ") a ORDER BY a.insert_date_time ASC; ", nativeQuery =  true)
	List<DTOChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestamp(
			@Param("senderId") String senderId, @Param("receiverId") String receiverId);

	
	@Query(value = " SELECT\r\n"
			+ "    rm.content AS reply_message,\r\n"
			+ "    rm.sender_id AS reply_message_sender_id,\r\n"
			+ "    cm.*\r\n"
			+ "FROM\r\n"
			+ "    chat_messages cm LEFT JOIN chat_messages rm ON rm.id = cm.reply_to_message_id\r\n"
			+ "WHERE\r\n"
			+ "    cm.id =:messageId ", nativeQuery = true)
	DTOChatMessage getMessageById(@Param("messageId") String messageId);
	
	
}
