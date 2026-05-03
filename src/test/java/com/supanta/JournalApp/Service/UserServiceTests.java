package com.supanta.JournalApp.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.supanta.JournalApp.Entity.User;
import com.supanta.JournalApp.Repository.UserEntryRepo;

import net.bytebuddy.asm.MemberSubstitution.Argument;
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserEntryService userService;

    @Autowired
    private UserEntryRepo userEntryRepo;
    @Test
    public void testFindByUserName()
    {
        User user = userEntryRepo.findByUserName("supanta");
        assertTrue(!user.getJournalEntries().isEmpty());
    }
    //if we donot want to run the test then we do 
    // @Disabled
    // @Test
    // public void testFindByUserName()
    // {
    //     User user = userEntryRepo.findByUserName("supanta");
    //     assertTrue(!user.getJournalEntries().isEmpty());
    // }

    @ParameterizedTest
    @CsvSource({
        "1,1,2",
        "2,10,12",
        "3,3,9"
    })
    public void test(int a, int b, int expected)
    {
        assertEquals(expected, a+b);
    }
    //(or)
    @ParameterizedTest
    @CsvSource({
        "ram",
        "shyam",
        "hari"
    })
    public void testFindByUserName(String userName)
    {
        assertNotNull(userEntryRepo.findByUserName(userName));
    }
    //(or)
    // @ParameterizedTest
    // @ValueSource(strings = {
    //     "ram",
    //     "shyam",
    //     "hari"
    // })
    // public void testFindByUserName(String userName)
    // {
    //     assertNotNull(userEntryRepo.findByUserName(userName));
    // }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testFindByUserName(User userName)
    {
        assertTrue(userService.saveNewUser(userName));
    }
}
