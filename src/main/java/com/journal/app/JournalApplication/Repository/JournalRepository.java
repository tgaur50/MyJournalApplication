package com.journal.app.JournalApplication.Repository;

import com.journal.app.JournalApplication.Entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.SpringDataMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface JournalRepository extends MongoRepository<Journal, ObjectId> {
}
