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

	
	
	@Query(value = " SELECT gt.* FROM generate_otp gt WHERE gt.email_id =:emailId AND gt.otp =:otp AND :currentFormatDateTime BETWEEN gt.send_date_time AND gt.expired_date_time ", nativeQuery = true)
	GenerateOtp getvalidateOtpByEmailId(@Param("emailId") String emailId, @Param("otp") String otp,
			@Param("currentFormatDateTime") String currentFormatDateTime);
}
