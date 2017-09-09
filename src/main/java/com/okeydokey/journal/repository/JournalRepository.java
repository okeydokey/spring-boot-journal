package com.okeydokey.journal.repository;

import com.okeydokey.journal.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long>{
    List<Journal> findByTitle(String title);
}
