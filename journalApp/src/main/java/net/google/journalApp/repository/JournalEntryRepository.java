package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.google.journalApp.entity.JournalEntry;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, String> {

	@Modifying
	@Transactional
	@Query(value = " DELETE FROM journal_entry WHERE id =:id ", nativeQuery = true)
	int deleteJournalEntryById(@Param("id") String id);

	@Query(value = " SELECT je.* FROM journal_entry je WHERE je.user_id =:userId ", nativeQuery = true)
	List<JournalEntry> journalEntryByUserId(@Param("userId") String userId);

	@Query(value = " SELECT je.* FROM journal_entry je WHERE je.id =:id ", nativeQuery = true)
	JournalEntry journalEntryById(@Param("id") String id);

	
	@Query(value = " SELECT je.* FROM journal_entry je ORDER BY je.insert_date_time DESC  ", nativeQuery = true)
	List<JournalEntry> getAllJournalEntry();

}
