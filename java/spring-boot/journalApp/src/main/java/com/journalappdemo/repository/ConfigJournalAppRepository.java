package com.journalappdemo.repository;

import com.journalappdemo.entity.ConfigJournalAppEntity;
import com.journalappdemo.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
