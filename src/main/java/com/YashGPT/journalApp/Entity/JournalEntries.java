package com.YashGPT.journalApp.Entity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "journal_Entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntries {
@Id
private ObjectId id;
private String title;
private String content;
private LocalDate date;
}
