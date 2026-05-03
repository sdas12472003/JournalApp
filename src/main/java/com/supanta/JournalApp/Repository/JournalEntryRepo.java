package com.supanta.JournalApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.supanta.JournalApp.Entity.JournalEntry;

public interface JournalEntryRepo extends JpaRepository<JournalEntry, Long> {

    
}