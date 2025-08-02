package com.journal.app.JournalApplication.Service;

import com.journal.app.JournalApplication.Entity.Journal;
import com.journal.app.JournalApplication.Entity.User;
import com.journal.app.JournalApplication.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JournalService {
    @Autowired
    private JournalRepository journalRepo;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalService.class);

    public List<Journal> getAllJournals(String userName){
        User user = userService.findByUserName(userName);
        logger.info("Total Journals of the user: " + user.getUsername() + " is " + user.getJournalList().size());
        return user.getJournalList();
    }

    public Optional<Journal> getJournalById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        Optional<Journal> journal = null;
        List<Journal> journalList = user.getJournalList().stream()
                .filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!journalList.isEmpty()){
            journal = journalRepo.findById(id);
        }
        return journal;
    }

    public Journal createJournal(Journal journal, String userName){
        User user = userService.findByUserName(userName);
        journal.setJournalDate(LocalDate.now());
        Journal newJournal = journalRepo.save(journal);
        user.getJournalList().add(newJournal);
        userService.saveUser(user);
        return newJournal;
    }

    @Transactional
    public Journal updateJournal(ObjectId id, Journal journal, String userName){
        User user = userService.findByUserName(userName);
        Journal oldJournal = getJournalById(id, userName).orElse(null);
        int index = user.getJournalList().indexOf(oldJournal);
        if(oldJournal != null){
            oldJournal.setTitle(journal.getTitle()!=null
                    && !journal.getTitle().equals("") ?journal.getTitle():oldJournal.getTitle());
            oldJournal.setDescription((journal.getDescription() != null
                    && !journal.getDescription().equals(""))
                    ?journal.getDescription():oldJournal.getDescription());
            oldJournal.setJournalDate(LocalDate.now());
        }
        assert oldJournal != null;
        Journal newJournal = journalRepo.save(oldJournal);
        user.getJournalList().set(index, newJournal);
        userService.saveUser(user);
        return newJournal;
    }

    public boolean deleteJournal(ObjectId id, String userName){
        boolean isRemoved = false;
        User user = userService.findByUserName(userName);
        //Journal journal = journalRepo.findById(id).orElse(null);
        isRemoved = user.getJournalList().removeIf(x -> x.getId().equals(id));
        if (isRemoved){
            userService.saveUser(user);
            journalRepo.deleteById(id);
        }
        return isRemoved;
    }
}
