package net.google.journalApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.google.journalApp.entity.GenerateOtp;

@Repository
public interface GenerateOtpRepository extends JpaRepository<GenerateOtp, String> {

	@Modifying
	@Transactional
	@Query(value = " DELETE FROM generate_otp WHERE id =:id ", nativeQuery = true)
	int deleteGenerateOtpById(@Param("id") String id);
}
