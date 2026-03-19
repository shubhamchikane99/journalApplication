package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.UserAccessRole;

@Repository
public interface UserAccessRoleRepository extends JpaRepository<UserAccessRole, String> {

	@Query(value = " SELECT a.* FROM user_access_role a WHERE a.user_id =:userId ORDER BY a.insert_date_time DESC LIMIT 1 ", nativeQuery = true)
	UserAccessRole getUserAccessRoleById(@Param("userId") String userId);

}
