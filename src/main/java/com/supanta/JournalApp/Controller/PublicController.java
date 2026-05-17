package com.supanta.JournalApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.jdk14.JDK14Util;
import com.supanta.JournalApp.Dto.JwtResponse;
import com.supanta.JournalApp.Dto.LoginRequest;
import com.supanta.JournalApp.Entity.User;
import com.supanta.JournalApp.Service.UserDetailsServiceImpl;
import com.supanta.JournalApp.Service.UserEntryService;
import com.supanta.JournalApp.Utils.JwtUtils;
import com.supanta.JournalApp.Dto.JwtResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class PublicController {
    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/health-check")
    public String healthCheck() {
        return "Journal App is running!";
    }
    @PostMapping("signup")
    public void signup( @Valid @RequestBody User myUser)
    {
        userEntryService.saveNewUser(myUser);
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        try
        {
            authenticationManager.authenticate(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
            UserDetails userDetail = userDetailsService.loadUserByUsername(request.getUserName());
            String jwt=jwtUtil.generateToken(userDetail.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwt));
        }
        catch(Exception e)
        {
            log.error("Login failed for user: " + request.getUserName(), e);
            throw new RuntimeException("Invalid username or password");
        }
    }
}
