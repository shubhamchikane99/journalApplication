package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;
import net.google.journalApp.entity.UserChatBackgrounds;

@Repository
public interface UserChatBackgroundsRepository extends JpaRepository<UserChatBackgrounds, String> {

	@Query(value = " SELECT * FROM user_chat_backgrounds bg WHERE bg.user_id =:userId AND bg.bg_img_user_id =:selectUserId AND bg.is_active = 1 ORDER BY bg.insert_date_time DESC LIMIT 1  ", nativeQuery = true)
	UserChatBackgrounds getUserChatBackgroundsByUsers(@Param("userId") String userId,
			@Param("selectUserId") String selectUserId);

}
