package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.DTOUsers;

@Repository
public interface DTOUsersRepository extends JpaRepository<DTOUsers, String> {

	
	@Query(value = " SELECT\r\n"
			+ "    IFNULL(b.unread_msg_count, 0) AS unread_msg_count,\r\n"
			+ "    0 AS send_request_flag,\r\n"
			+ "    a.id AS user_id,\r\n"
			+ "    '-' AS user_friends_id,\r\n"
			+ "    a.*\r\n"
			+ "FROM\r\n"
			+ "    (\r\n"
			+ "    SELECT\r\n"
			+ "    u.* \r\n"
			+ "    FROM\r\n"
			+ "    user_friends uf,\r\n"
			+ "    users u     \r\n"
			+ "    WHERE\r\n"
			+ "        uf.request_user_id = u.id\r\n"
			+ "    AND uf.flag = 1\r\n"
			+ "    AND uf.user_id =:userId \r\n"
			+ "UNION \r\n"
			+ "    SELECT \r\n"
			+ "    u.*\r\n"
			+ "    FROM \r\n"
			+ "    users u \r\n"
			+ "    WHERE u.is_ai = 1     \r\n"
			+ ") a\r\n"
			+ "LEFT JOIN(\r\n"
			+ "    SELECT\r\n"
			+ "    cm.sender_id,\r\n"
			+ "    COUNT(cm.id) AS unread_msg_count\r\n"
			+ "    FROM\r\n"
			+ "    chat_messages cm,\r\n"
			+ "    users u\r\n"
			+ "    WHERE\r\n"
			+ "    u.id =:userId \r\n"
			+ "    AND cm.status NOT IN(2) \r\n"
			+ "    AND u.id = cm.receiver_id\r\n"
			+ "    GROUP BY cm.sender_id\r\n"
			+ ") b ON a.id = b.sender_id ", nativeQuery = true)
	List<DTOUsers> getAcceptRequestUsersList(@Param("userId") String userId);

	
	@Query(value = "  SELECT uuid() AS id, a.id AS user_id,'-' AS user_friends_id,  \r\n"
			+ "    CASE \r\n"
			+ "    WHEN b.user_id IS NULL THEN 0\r\n"
			+ "    WHEN b.user_id IS NOT NULL THEN 1 \r\n"
			+ "    END AS send_request_flag,\r\n"
			+ "    0 AS unread_msg_count,\r\n"
			+ "    a.*\r\n"
			+ "FROM\r\n"
			+ "    (\r\n"
			+ "    SELECT\r\n"
			+ "    u.*\r\n"
			+ "    FROM\r\n"
			+ "    users u\r\n"
			+ "    WHERE\r\n"
			+ "    u.id NOT IN (SELECT us.id FROM users us WHERE us.id =:userId )     \r\n"
			+ "    AND u.id NOT IN(SELECT uf.request_user_id FROM user_friends uf WHERE\r\n"
			+ "            uf.flag IN(1, 2, 0) AND uf.user_id =:userId )\r\n"
			+ "    AND u.is_ai NOT IN (1)     \r\n"
			+ ") a\r\n"
			+ "LEFT JOIN\r\n"
			+ "(   \r\n"
			+ "    SELECT \r\n"
			+ "    uf.user_id\r\n"
			+ "    FROM\r\n"
			+ "    user_friends uf\r\n"
			+ "    WHERE \r\n"
			+ "    flag = 0\r\n"
			+ "    AND uf.request_user_id =:userId \r\n"
			+ "    GROUP BY uf.user_id\r\n"
			+ ")b ON a.id = b.user_id\r\n"
			+ "ORDER BY a.insert_date_time DESC ", nativeQuery = true)
	List<DTOUsers> allUserAndSendRequesFlag(@Param("userId") String userId);


	@Query(value = " SELECT\r\n"
			+ "   uuid() AS id, us.*,\r\n"
			+ "    0 AS unread_msg_count, 0 AS send_request_flag, us.id AS user_id,uf.id AS user_friends_id \r\n"
			+ "FROM\r\n"
			+ "    user_friends uf,\r\n"
			+ "    users us \r\n"
			+ "WHERE\r\n"
			+ "        uf.request_user_id = us.id\r\n"
			+ "    AND uf.flag = 0 \r\n"
			+ "    AND uf.user_id IN(SELECT u.id FROM users u WHERE u.user_name =:userName)\r\n"
			+ "    ORDER BY uf.insert_date_time DESC  ", nativeQuery = true)
	List<DTOUsers> getRequestUserListByUserName(@Param("userName") String userName);


	@Query(value = " SELECT\r\n"
			+ "   uuid() AS id, us.*,\r\n"
			+ "    0 AS unread_msg_count, 0 AS send_request_flag, us.id AS user_id, uf.id AS user_friends_id\r\n"
			+ "FROM\r\n"
			+ "    user_friends uf,\r\n"
			+ "    users us \r\n"
			+ "WHERE\r\n"
			+ "        uf.request_user_id = us.id\r\n"
			+ "    AND uf.flag = 0 \r\n"
			+ "    AND uf.user_id =:userId \r\n"
			+ "    ORDER BY uf.insert_date_time DESC  ", nativeQuery =  true)
	List<DTOUsers> getRequestUserListByUserId(@Param("userId") String userId);

}
