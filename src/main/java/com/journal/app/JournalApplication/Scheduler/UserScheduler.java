package com.journal.app.JournalApplication.Scheduler;

import com.journal.app.JournalApplication.Entity.Journal;
import com.journal.app.JournalApplication.Entity.User;
import com.journal.app.JournalApplication.Enums.Sentiment;
import com.journal.app.JournalApplication.Repository.UserRepositoryImpl;
import com.journal.app.JournalApplication.Service.MailService;
import com.journal.app.JournalApplication.model.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserScheduler {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    //Every sun at 9 AM
    //@Scheduled(cron = "0 0 09 * * SUN")
    //Every min
    //@Scheduled(cron = "0 */1 * * * *")
    public void fetchSAUsersAndSendMail(){
        List<User> saUsersList = userRepository.getAllUsersForSA();
        for (User saUser : saUsersList){
            Map<String, Long> journalSentiments = saUser.getJournalList().stream()
                    .filter(x -> x.getJournalDate().isAfter(LocalDate.now().minus(7,
                            ChronoUnit.DAYS))).map(Journal::getSentiment)
                    .filter(Objects::nonNull).collect(Collectors.groupingBy(Enum::name,
                            Collectors.counting()));

            String maxOccuredSentiment = journalSentiments.entrySet().stream()
                   .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
           if (maxOccuredSentiment != null){
               SentimentData sentimentData = SentimentData.builder().email(saUser.getEmail())
                       .sentimentContent("Sentiment for last 7 days is: " + maxOccuredSentiment).build();
               try{
                   kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(), sentimentData);
               }catch (Exception e){
                   log.error("Exception: " + e);
                   mailService.sendMail(sentimentData.getEmail(), "Sentiment for last week",
                           sentimentData.getSentimentContent());
               }
           }
        }
    }
}
