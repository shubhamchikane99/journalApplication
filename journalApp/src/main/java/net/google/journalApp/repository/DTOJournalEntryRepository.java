package net.google.journalApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.google.journalApp.entity.DTOJournalEntry;

@Repository
public interface DTOJournalEntryRepository extends JpaRepository<DTOJournalEntry, String> {

	@Query(value = "  SELECT je.*, u.user_name FROM journal_entry je, users u WHERE je.user_id = u.id AND je.user_id =:userId ", nativeQuery = true)
	List<DTOJournalEntry> journalEntryByUserId(@Param("userId") String userId);
}
