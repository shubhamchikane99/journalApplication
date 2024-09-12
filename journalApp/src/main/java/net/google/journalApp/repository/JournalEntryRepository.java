package net.google.journalApp.repository;

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
	@Query(value =" DELETE FROM journal_entry WHERE id =:id ", nativeQuery = true)
	int deleteByIdJournalEntry(@Param("id") String id);

}
