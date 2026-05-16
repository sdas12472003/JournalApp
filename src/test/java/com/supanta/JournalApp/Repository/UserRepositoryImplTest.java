package com.supanta.JournalApp.Repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.supanta.JournalApp.Entity.User;

@SpringBootTest
public class UserRepositoryImplTest {
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Test
    public void testSaveNewUser()
    {
        Assertions.assertNotNull(userRepositoryImpl.getUserForSA());
    }
}
