package com.supanta.JournalApp.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supanta.JournalApp.Entity.JournalEntry;
import com.supanta.JournalApp.Entity.User;
import com.supanta.JournalApp.Service.JournalEntryService;
import com.supanta.JournalApp.Service.UserEntryService;
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userService;
    
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() { 
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
        if(user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if(journalEntries != null && !journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) 
    {
        try {
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUsername(userName);
            if(user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            journalEntryService.saveJournalEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
             return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
        }
       
    }

    //Below code is response entity how we can implement it in get by id method
    @GetMapping("id/{myId}") 
    public ResponseEntity<JournalEntry> getJournalEntryBYId(@PathVariable Long myId)
    {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User byusername=userService.findByUsername(userName);
        List<JournalEntry> collect=byusername.getJournalEntries().stream().filter(entry->entry.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(myId);
            if(journalEntry.isPresent())
            {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryBYId(@PathVariable Long myId) 
    {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed=journalEntryService.deleteJournalEntryById(myId, userName);
        if(removed)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
    @PutMapping("id/{myId}") 
    public ResponseEntity<?> updateJournalEntry(@PathVariable Long myId, @RequestBody JournalEntry newEntry)
    {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User byusername=userService.findByUsername(userName);
        List<JournalEntry> collect=byusername.getJournalEntries().stream().filter(entry->entry.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(myId);
            if(journalEntry.isPresent())
            {
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle():old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent():old.getContent());
                journalEntryService.saveJournalEntry(old, userName);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        JournalEntry old= journalEntryService.getJournalEntryById(myId).orElse(null);
        if(old!=null)
        {
            
        }
        
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
