package com.supanta.JournalApp.Controller;

import java.security.Security;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.supanta.JournalApp.Repository.UserEntryRepo;
import com.supanta.JournalApp.Service.JournalEntryService;
import com.supanta.JournalApp.Service.UserEntryService;
@RestController
@RequestMapping("/user")
public class UserEntryController {
   @Autowired
   public UserEntryService userEntryService;
   @Autowired 
   public UserEntryRepo userEntryRepo;
   @Autowired
    private PasswordEncoder passwordEncoder;

   @GetMapping
   public List<User> getAllUsers()
   {
      return userEntryService.getAllUserEntries();
   }
   
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user)
    {

        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb=userEntryService.findByUsername(userName);
        if(userInDb!=null)
        {
            if(user.getUserName() != null) {
                userInDb.setUserName(user.getUserName());
            }

            if(user.getPassword() != null) {
            userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
        }
            userEntryService.saveUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser()
    {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepo.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
