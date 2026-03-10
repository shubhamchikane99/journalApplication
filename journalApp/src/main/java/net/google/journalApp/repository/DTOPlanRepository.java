package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.DTOPlan;

@Repository
public interface DTOPlanRepository extends JpaRepository<DTOPlan, String> {

	@Query(value = " SELECT\r\n"
			+ "    pl.id,\r\n"
			+ "    pl.name,\r\n"
			+ "    pl.description,\r\n"
			+ "    (pl.duration - DATEDIFF(NOW(), p.insert_date_time)) AS days_count\r\n"
			+ "FROM\r\n"
			+ "    payment p,\r\n"
			+ "    plan pl\r\n"
			+ "WHERE\r\n"
			+ "       p.plan_id = pl.id\r\n"
			+ "   AND p.user_id =:userId \r\n"
			+ "ORDER BY p.insert_date_time DESC  ", nativeQuery   =true)
	DTOPlan getUserActivePlanByUserId(@Param("userId") String userId);

}
