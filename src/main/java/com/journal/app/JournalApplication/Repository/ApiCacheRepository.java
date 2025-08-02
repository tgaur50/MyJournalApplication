package com.journal.app.JournalApplication.Repository;

import com.journal.app.JournalApplication.Entity.ApiCacheEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiCacheRepository extends MongoRepository<ApiCacheEntity, ObjectId> {
}
