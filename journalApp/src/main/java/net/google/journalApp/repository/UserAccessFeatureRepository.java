package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.UserAccessFeature;

@Repository
public interface UserAccessFeatureRepository extends JpaRepository<UserAccessFeature, String> {

	@Query(value = " SELECT f.* FROM user_access_feature f ORDER BY f.name ", nativeQuery = true)
	List<UserAccessFeature> getUserAccessFeatureAll();

}
