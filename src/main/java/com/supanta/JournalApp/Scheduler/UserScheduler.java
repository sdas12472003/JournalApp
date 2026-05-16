package com.supanta.JournalApp.Scheduler;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.supanta.JournalApp.Entity.JournalEntry;
import com.supanta.JournalApp.Entity.User;
import com.supanta.JournalApp.Repository.UserRepositoryImpl;
import com.supanta.JournalApp.Service.EmailService;
import com.supanta.JournalApp.Service.SentimentAnalysisService;

@Component
public class UserScheduler {
    @Autowired
    private EmailService emailService;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private UserRepositoryImpl userRepository;
    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserScheduler()
    {
        List<User> users = userRepository.getUserForSA();
        for(User user: users)
        {
            List<JournalEntry> entries = user.getJournalEntries();
            List<String> filteredEntries= entries.stream().filter(entry -> sentimentAnalysisService.getSentiment(entry.getContent()).equals("negative")).map(JournalEntry::getContent).toList();
            String entry=String.join("\n", filteredEntries);
            String sentiment=sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Your Journal Sentiment Analysis", "Your journal entries have a " + sentiment + " sentiment. Here are the entries:\n" + entry);
        }
    }
}
