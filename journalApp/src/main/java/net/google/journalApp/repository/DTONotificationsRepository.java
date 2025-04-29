package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.DTONotifications;

@Repository
public interface DTONotificationsRepository extends JpaRepository<DTONotifications, String> {

	
	@Query(value = " SELECT\r\n"
			+ "    CONCAT(ru.first_name) AS receiver_name,\r\n"
			+ "    CONCAT(su.first_name) AS sender_name,\r\n"
			+ "    n.* \r\n"
			+ "FROM\r\n"
			+ "    notifications n,\r\n"
			+ "    users su,\r\n"
			+ "    users ru\r\n"
			+ "WHERE\r\n"
			+ "       n.receiver_id = ru.id\r\n"
			+ "   AND n.sender_id = su.id \r\n"
			+ "   AND n.receiver_id =:userId \r\n"
			+ "   ORDER BY n.insert_date_time DESC  ", nativeQuery = true)
	List<DTONotifications> getNotificationOfUsers(@Param("userId") String userId);

}
