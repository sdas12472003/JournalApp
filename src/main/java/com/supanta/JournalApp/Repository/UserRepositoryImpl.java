package com.supanta.JournalApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.supanta.JournalApp.Entity.User;

public interface UserRepositoryImpl extends JpaRepository<User, Long> {

    @Query("""
        SELECT DISTINCT u
        FROM User u
        JOIN u.roles r
        WHERE u.sentimentAnalysis = true
        AND u.email LIKE '%.com'
        AND (r = 'USER' OR r = 'ADMIN')
    """)
    List<User> getUserForSA();
}