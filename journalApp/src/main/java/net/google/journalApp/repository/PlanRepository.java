package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.google.journalApp.entity.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, String> {

	@Query(value = " SELECT p.* FROM plan p WHERE p.is_active = 1 ORDER BY p.price ", nativeQuery = true)
	List<Plan> getActivePlan();

	@Modifying
	@Transactional
	@Query(value = " DELETE FROM plan WHERE id =:id ", nativeQuery = true)
	int deletePlanById(@Param("id") String id);

}
