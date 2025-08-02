package com.journal.app.JournalApplication.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SentimentConsumerServiceTest {
    @Autowired
    private SentimentConsumerService sentimentConsumerService;

    @Disabled
    @Test
    public void testSendMail(){
        //sentimentConsumerService.consume();
    }
}