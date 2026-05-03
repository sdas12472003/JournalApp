package com.supanta.JournalApp.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.supanta.JournalApp.Entity.JournalEntry;
import com.supanta.JournalApp.Entity.User;
import com.supanta.JournalApp.Repository.JournalEntryRepo;
import com.supanta.JournalApp.Repository.UserEntryRepo;

import jakarta.websocket.server.ServerEndpoint;
@Service
public class UserEntryService {
    
    @Autowired// --> This is dependency Injection, We are Injecting Journal Entry Repo in this service class
    private UserEntryRepo userEntryRepo;
    

    private static final Logger logger = LoggerFactory.getLogger(UserEntryService.class);
    
    

    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean saveNewUser(User myEntry)
    {
        try {
            myEntry.setPassword(passwordEncoder.encode(myEntry.getPassword()));
            myEntry.setRoles(Arrays.asList("USER"));
            userEntryRepo.save(myEntry);
            return true;

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
    public void saveUser(User myEntry)
    {
        userEntryRepo.save(myEntry);
    }

    public List<User> getAllUserEntries() {
        return userEntryRepo.findAll();
    }

    public Optional<User> getUserEntryById(Long myId) {
        return userEntryRepo.findById(myId);
    }

    public void deleteUserEntryById(Long myId) {
        userEntryRepo.deleteById(myId);
    }
    public User findByUsername(String username) {
        return userEntryRepo.findByUserName(username);
    }
}
