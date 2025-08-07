package com.journal.app.JournalApplication.Controller;

import com.journal.app.JournalApplication.Entity.Journal;
import com.journal.app.JournalApplication.Service.JournalService;
import com.journal.app.JournalApplication.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Journal")
@Tag(name="Journal-APIs", description = "This is Rest API for Journals of Journal Application")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllJournalsOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<Journal> userJournalList = journalService.getAllJournals(userName);
        if (userJournalList!=null && !userJournalList.isEmpty()){
            return new ResponseEntity<>(userJournalList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createJounralForUser(@RequestBody Journal journal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try{
            Journal newJournal = journalService.createJournal(journal, userName);
            return new ResponseEntity<>(newJournal, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Journal> journal = journalService.getJournalById(myId, userName);
        if (journal != null && journal.isPresent()){
            return new ResponseEntity<>(journal.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean isRemoved = false;
        isRemoved = journalService.deleteJournal(myId, userName);
        if (isRemoved){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId myId, @RequestBody Journal journal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Journal updatedJournal = journalService.updateJournal(myId, journal, userName);
        if (updatedJournal != null){
            return new ResponseEntity<>(updatedJournal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
