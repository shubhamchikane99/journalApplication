package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.google.journalApp.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {

	@Modifying
	@Transactional
	@Query(value = " DELETE FROM users WHERE id =:id ", nativeQuery = true)
	int deleteByIdUsers(@Param("id") String id);

	@Query(value = "SELECT u.* FROM users u WHERE u.user_name =:userName", nativeQuery = true)
	Users findUserByUserName(@Param("userName") String userName);
}
