package com.YashGPT.journalApp.Repository;
import com.YashGPT.journalApp.Entity.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface JournalRepository extends MongoRepository<JournalEntries, ObjectId> {

}
