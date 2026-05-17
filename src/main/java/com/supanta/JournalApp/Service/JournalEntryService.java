package com.supanta.JournalApp.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.supanta.JournalApp.Entity.JournalEntry;
import com.supanta.JournalApp.Entity.User;
import com.supanta.JournalApp.Repository.JournalEntryRepo;

import jakarta.transaction.Transactional;
import jakarta.websocket.server.ServerEndpoint;

@Service
public class JournalEntryService {

    @Autowired // --> This is dependency Injection, We are Injecting Journal Entry Repo in this
               // service class
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserEntryService userService;

    

    @Transactional
    public void saveJournalEntry(JournalEntry myEntry, String userName) {
        try {
            User user = userService.findByUsername(userName);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            myEntry.setUser(user);
            JournalEntry saved = journalEntryRepo.save(myEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);

        } catch (Exception e) {
            
            throw new RuntimeException("Error saving journal entry", e);
        }

    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(Long myId) {
        return journalEntryRepo.findById(myId);
    }

    @Transactional
    public boolean deleteJournalEntryById(Long myId, String userNameString) {
        boolean removed = false;
        try {
            User user = userService.findByUsername(userNameString);

            if (user == null) {
                throw new RuntimeException("User not found");
            }

            Optional<JournalEntry> entry = journalEntryRepo.findById(myId);

            if(entry.isPresent()) {
                JournalEntry journalEntry = entry.get();

                if(journalEntry.getUser().getUserName().equals(userNameString)) {
                    journalEntryRepo.deleteById(myId);
                }
            }

        } catch (Exception e) {
            
            throw new RuntimeException("Error deleting journal entry: " + e.getMessage());
        }
        return removed;
    }

}
