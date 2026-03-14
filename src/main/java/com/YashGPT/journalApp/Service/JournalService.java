package com.YashGPT.journalApp.Service;

import com.YashGPT.journalApp.Entity.JournalEntries;
import com.YashGPT.journalApp.Entity.User;
import com.YashGPT.journalApp.Repository.JournalRepository;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// import org.springframework.transaction.annotation.Transactional;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userServices;

    // get all journal entries
    public List<JournalEntries> getAllEntries() {
        return journalRepository.findAll();
    }

    // get journal entry by id
    public JournalEntries findById(ObjectId id) {
        return journalRepository.findById(id).orElse(null);
    }

    // create new journal entry
    // @Transactional annotation ensures that the entire method is executed within a transaction. If any exception occurs during the execution of the method, the transaction will be rolled back, ensuring data integrity and consistency in the database.
    // @Transactional
    public void createEntry(JournalEntries entry, String username) {
        try {
            User user = userServices.getUserByUsername(username);
            entry.setDate(LocalDate.now());
            JournalEntries saved = journalRepository.save(entry);
            user.getJournals().add(saved);
            userServices.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Failed to create journal entry: " + e.getMessage()); // Rethrow the exception to
                                                                                             // trigger transaction
                                                                                             // rollback
        }

    }

    // update journal entry
    public void updateEntry(ObjectId id, JournalEntries entry) {
        if (journalRepository.existsById(id)) {
            entry.setId(id);
            journalRepository.save(entry);
        }
    }

    // delete journal entry
    public void deleteEntry(ObjectId id, String username) {
        User user = userServices.getUserByUsername(username);
        user.getJournals().removeIf(journal -> journal.getId().equals(id));
        userServices.saveUser(user);
        journalRepository.deleteById(id);
    }
}
