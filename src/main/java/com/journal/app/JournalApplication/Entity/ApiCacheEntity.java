package com.journal.app.JournalApplication.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ApiConfiguration")
@Data
@NoArgsConstructor
public class ApiCacheEntity {
    @Id
    private ObjectId id;
    @NonNull
    private String key;
    @NonNull
    private String value;
}
