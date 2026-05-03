package com.supanta.JournalApp.Controller;

import java.util.List;
import com.supanta.JournalApp.Entity.User;
import org.apache.catalina.connector.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supanta.JournalApp.Service.UserEntryService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserEntryService userEntryService;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers()
    {
        java.util.List<com.supanta.JournalApp.Entity.User> all=userEntryService.getAllUserEntries();
        if(all!=null && !all.isEmpty())
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
