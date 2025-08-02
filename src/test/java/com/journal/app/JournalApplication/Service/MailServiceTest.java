package com.journal.app.JournalApplication.Service;

import com.journal.app.JournalApplication.Scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserScheduler userScheduler;

    /*@Test
    public void testSendMail(){
        mailService.sendMail("tushargaur9913103636@gmail.com",
                "Test Mail via Spring boot", "Hi, This is a test mail body!");
    }*/

    @Test
    public void testSendMail(){
        userScheduler.fetchSAUsersAndSendMail();
    }
}
