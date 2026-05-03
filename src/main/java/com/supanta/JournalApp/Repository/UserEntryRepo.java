package com.supanta.JournalApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.supanta.JournalApp.Entity.JournalEntry;
import com.supanta.JournalApp.Entity.User;

public interface UserEntryRepo extends JpaRepository<User, Long> {

    User findByUserName(String userName);
    void deleteByUserName(String userName);
}
