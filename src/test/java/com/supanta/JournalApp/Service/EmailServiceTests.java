package com.supanta.JournalApp.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;
    @Test
    public void testSendEmail()
    {
        emailService.sendEmail("test@example.com", "Test Subject", "Test Body");
    }
}
