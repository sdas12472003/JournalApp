package com.supanta.JournalApp.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import com.supanta.JournalApp.Entity.User;
import com.supanta.JournalApp.Repository.UserEntryRepo;
public class UserDetailsServiceImplTests {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserEntryRepo userEntryRepo;


    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void loadUserByUsername()
    {   
       when(userEntryRepo.findByUserName(ArgumentMatchers.anyString()))
    .thenReturn(User.builder()
        .userName("ram")
        .password("ram123")
        .roles(Arrays.asList("USER"))
        .build());
        UserDetails userDetails = userDetailsService.loadUserByUsername("ram");
        assertNotNull(userDetails);
    }
}
