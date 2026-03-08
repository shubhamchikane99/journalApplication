package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.google.journalApp.entity.PlanFeature;

@Repository
public interface PlanFeatureRepository extends JpaRepository<PlanFeature, String> {

	@Modifying
	@Transactional
	@Query(value = " DELETE FROM plan_feature WHERE plan_id  =:id ", nativeQuery = true)
	int deletePlanFeatureByPlanId(String id);

}
