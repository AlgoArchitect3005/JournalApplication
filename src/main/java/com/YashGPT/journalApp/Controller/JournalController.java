package com.YashGPT.journalApp.Controller;

import com.YashGPT.journalApp.Entity.JournalEntries;
import com.YashGPT.journalApp.Entity.User;
import com.YashGPT.journalApp.Service.JournalService;
import com.YashGPT.journalApp.Service.UserService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService journalServices;

    @Autowired
    private UserService userServices;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userServices.getUserByUsername(username);
        List<JournalEntries> all = user.getJournals();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);

        }
        return new ResponseEntity<>("No journal entries found for the user.", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntries> getJournalEntryById(
            @PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userServices.getUserByUsername(username);
        List <JournalEntries> collect = user.getJournals().stream().filter(x-> x.getId().equals(id)).collect(Collectors.toList());

        if (!collect.isEmpty()) {
            JournalEntries journalEntry = journalServices.findById(id);
            if (journalEntry != null) {
                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createJournalEntry(@RequestBody JournalEntries entry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalServices.createEntry(entry, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{username}/id/{id}")
    public ResponseEntity<?> updateJournalEntry(
            @PathVariable String username,
            @PathVariable ObjectId id,
            @RequestBody JournalEntries entry) {

        JournalEntries old = journalServices.findById(id);
        if (old != null) {
            old.setTitle(entry.getTitle() != null && !entry.getTitle().equals(" ") ? entry.getTitle() : old.getTitle());
            old.setContent(entry.getContent() != null && !entry.getContent().equals(" ") ? entry.getContent()
                    : old.getContent());
            journalServices.updateEntry(id, old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalEntry( @PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
       boolean removed = journalServices.deleteEntry(id, username);
         if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                      
}
