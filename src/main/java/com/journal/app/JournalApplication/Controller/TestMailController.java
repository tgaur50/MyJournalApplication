package com.journal.app.JournalApplication.Controller;

import com.journal.app.JournalApplication.Service.SentimentConsumerService;
import com.journal.app.JournalApplication.model.SentimentData;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Test-APIs", description = "This is Rest API to send a test mail in Journal Application")
public class TestMailController {
    @Autowired
    private SentimentConsumerService sentimentConsumerService;

    @GetMapping("/test-mail")
    public void testMail() {
        SentimentData data = new SentimentData();
        data.setEmail("tushargaur9913103636@gmail.com");
        data.setSentimentContent("This is a debug test email.");
        sentimentConsumerService.sendMail(data);
    }

    @GetMapping("/debug-kafka-message")
    public void debugKafka() {
        SentimentData data = new SentimentData();
        data.setEmail("tushargaur9913103636@gmail.com");
        data.setSentimentContent("Sample sentiment");
        //sentimentConsumerService.consume(data); // this will hit your @KafkaListener logic
    }

}

