package net.raju.journalApp.scheduler;

import net.raju.journalApp.cache.AppCache;
import net.raju.journalApp.entity.JournalEntry;
import net.raju.journalApp.entity.User;
import net.raju.journalApp.enums.Sentiment;
import net.raju.journalApp.model.SentimentData;
import net.raju.journalApp.repository.UserRepositoryImpl;
import net.raju.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String,SentimentData> kafkaTemplate;


   @Scheduled(cron = "0 0 8 * * SUN")
    public void fetchUsersAndSendSaMail(){
        List<User> users = userRepository.getUserForSA();
        
        for(User user:users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(30, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();

            for(Sentiment sentiment:sentiments){
                if(sentiment != null){
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }

            Sentiment mostFrequentSentiment = null;

            int maxCount = 0;

            for(Sentiment sentiment:sentimentCounts.keySet()){
                if(sentimentCounts.get(sentiment) > maxCount){
                    maxCount = sentimentCounts.get(sentiment);
                    mostFrequentSentiment = sentiment;
                }
            }

            if(mostFrequentSentiment != null){
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 30 days " + mostFrequentSentiment).build();
                kafkaTemplate.send("weekly-sentiments",sentimentData.getEmail(), sentimentData);
            }
        }

    }


    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }

}
