package com.supanta.JournalApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.supanta.JournalApp.Entity.User;
import com.supanta.JournalApp.Repository.UserEntryRepo;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserEntryRepo userEntryRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userEntryRepo.findByUserName(username);
        if(byUsername != null) {
           UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(byUsername.getUserName())
                    .password(byUsername.getPassword())
                    .roles(byUsername.getRoles().toArray(new String[0]))
                    .build();
           return userDetails;
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
