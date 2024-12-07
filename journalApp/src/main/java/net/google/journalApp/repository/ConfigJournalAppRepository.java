package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.google.journalApp.entity.ConfigJournalApp;

@Repository
public interface ConfigJournalAppRepository
		extends JpaRepository<ConfigJournalApp, String>, JpaSpecificationExecutor<ConfigJournalApp> {

	@Modifying
	@Transactional
	@Query(value = " DELETE FROM config_journal_app WHERE id =:id ", nativeQuery = true)
	int deleteConfigJournalAppById(@Param("id") String id);

	
	@Query(value = " SELECT cjp.* FROM config_journal_app cjp ORDER BY cjp.insert_date_time DESC  ", nativeQuery =  true)
	List<ConfigJournalApp> getAllConfigJournalApp();

}
