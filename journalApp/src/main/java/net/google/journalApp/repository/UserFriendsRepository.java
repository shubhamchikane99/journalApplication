package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.UserFriends;

@Repository
public interface UserFriendsRepository
		extends JpaRepository<UserFriends, String>, JpaSpecificationExecutor<UserFriends> {

}
