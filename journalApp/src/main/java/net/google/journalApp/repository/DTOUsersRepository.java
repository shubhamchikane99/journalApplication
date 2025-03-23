package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.DTOUsers;

@Repository
public interface DTOUsersRepository extends JpaRepository<DTOUsers, String> {

	
	@Query(value = "SELECT\r\n"
			+ "    IFNULL(b.unread_msg_count,0) AS unread_msg_count,\r\n"
			+ "    a.*\r\n"
			+ "FROM\r\n"
			+ "    (\r\n"
			+ "    SELECT\r\n"
			+ "    u.*\r\n"
			+ "    FROM\r\n"
			+ "    users u\r\n"
			+ "    WHERE\r\n"
			+ "    u.user_name NOT IN(:userName)\r\n"
			+ ") a\r\n"
			+ "LEFT JOIN(\r\n"
			+ "    SELECT\r\n"
			+ "    cm.sender_id,\r\n"
			+ "    COUNT(cm.id) AS unread_msg_count\r\n"
			+ "    FROM\r\n"
			+ "    chat_messages cm,\r\n"
			+ "    users u\r\n"
			+ "    WHERE\r\n"
			+ "    u.user_name =:userName \r\n"
			+ "    AND cm.status != 'SEEN' \r\n"
			+ "    AND u.id = cm.receiver_id\r\n"
			+ "    GROUP BY cm.sender_id\r\n"
			+ ") b ON a.id = b.sender_id", nativeQuery = true)
	List<DTOUsers> getAllUserWithoutLogInPersonAndUnreadMsg(@Param("userName") String userName);

}
