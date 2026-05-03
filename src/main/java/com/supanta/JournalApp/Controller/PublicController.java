package com.supanta.JournalApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supanta.JournalApp.Entity.User;
import com.supanta.JournalApp.Service.UserEntryService;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserEntryService userEntryService;
    @GetMapping("/health-check")
    public String healthCheck() {
        return "Journal App is running!";
    }
    @PostMapping("create-user")
    public void createUser(@RequestBody User myUser)
    {
        userEntryService.saveNewUser(myUser);
    }
}
