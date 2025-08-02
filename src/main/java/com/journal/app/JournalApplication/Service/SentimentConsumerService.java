package com.journal.app.JournalApplication.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.journal.app.JournalApplication.model.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.EnumMap;

@Service
@Slf4j
public class SentimentConsumerService {
    @Autowired
    private MailService mailService;

    @KafkaListener(topics = "weekly_sentiments", groupId = "weekly-sentiment-group-test5")
    public void consumeRaw(String message) {
        try {
            System.out.println("🔥 Raw message: " + message);
            ObjectMapper mapper = new ObjectMapper();
            SentimentData data = mapper.readValue(message, SentimentData.class);
            sendMail(data);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Exception: " + e);
        }
    }


    public void sendMail(SentimentData sentimentData){
        try {
            System.out.println(">> Trying to send mail to: " + sentimentData.getEmail());
            mailService.sendMail(sentimentData.getEmail(), "Sentiment for last week",
                    sentimentData.getSentimentContent());
            System.out.println(">> Mail sending call completed.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(">> Mail sending failed: " + e.getMessage());
        }
    }
}
